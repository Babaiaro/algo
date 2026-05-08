import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * PhoneBattery
 * ------------
 * Reads battery percentage from a phone connected via USB-C on Linux.
 * Supports Android (via ADB) and iPhone (via libimobiledevice's idevicediagnostics).
 *
 * Compile:  javac PhoneBattery.java
 * Run:      java PhoneBattery
 *           java PhoneBattery --watch
 *           java PhoneBattery --json
 */
public class PhoneBattery {

    // ---------- Data holder ----------

    static class BatteryInfo {
        String platform;
        String device;
        int level = -1;
        String status = "unknown";
        String source = "Not charging";
        Double temperatureC;  // nullable
        Double voltageV;      // nullable

        String toJson() {
            StringBuilder sb = new StringBuilder("{");
            sb.append("\"platform\":\"").append(platform).append("\",");
            sb.append("\"device\":\"").append(escape(device)).append("\",");
            sb.append("\"level\":").append(level).append(",");
            sb.append("\"status\":\"").append(status).append("\",");
            sb.append("\"source\":\"").append(source).append("\",");
            sb.append("\"temperature_c\":").append(temperatureC == null ? "null" : temperatureC).append(",");
            sb.append("\"voltage_v\":").append(voltageV == null ? "null" : voltageV);
            sb.append("}");
            return sb.toString();
        }

        private String escape(String s) {
            return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
        }
    }

    // ---------- Process helper ----------

    /** Runs a command, returns stdout as String, or null on failure/timeout. */
    static String run(String... cmd) {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd).redirectErrorStream(true);
            Process p = pb.start();
            StringBuilder out = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    out.append(line).append("\n");
                }
            }
            if (!p.waitFor(5, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                return null;
            }
            return p.exitValue() == 0 ? out.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    static boolean commandExists(String cmd) {
        String out = run("which", cmd);
        return out != null && !out.trim().isEmpty();
    }

    // ---------- Android (ADB) ----------

    static BatteryInfo getAndroidBattery() {
        if (!commandExists("adb")) return null;

        String devicesOut = run("adb", "devices");
        if (devicesOut == null) return null;

        String deviceId = null;
        for (String line : devicesOut.split("\n")) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("List of devices")) continue;
            if (line.endsWith("device")) {
                deviceId = line.split("\\s+")[0];
                break;
            }
        }
        if (deviceId == null) return null;

        String dump = run("adb", "shell", "dumpsys", "battery");
        if (dump == null) return null;

        Map<String, String> kv = new HashMap<>();
        for (String line : dump.split("\n")) {
            int colon = line.indexOf(':');
            if (colon > 0) {
                kv.put(line.substring(0, colon).trim(), line.substring(colon + 1).trim());
            }
        }

        BatteryInfo info = new BatteryInfo();
        info.platform = "Android";
        info.device = deviceId;

        try {
            info.level = Integer.parseInt(kv.getOrDefault("level", "-1"));
        } catch (NumberFormatException ignored) {}

        info.status = kv.getOrDefault("status", "unknown");

        if ("true".equals(kv.get("AC powered"))) info.source = "AC";
        else if ("true".equals(kv.get("USB powered"))) info.source = "USB";
        else if ("true".equals(kv.get("Wireless powered"))) info.source = "Wireless";

        // Temperature is in tenths of a degree Celsius
        try {
            int t = Integer.parseInt(kv.getOrDefault("temperature", ""));
            info.temperatureC = t / 10.0;
        } catch (NumberFormatException ignored) {}

        // Voltage is in millivolts
        try {
            int v = Integer.parseInt(kv.getOrDefault("voltage", ""));
            info.voltageV = v / 1000.0;
        } catch (NumberFormatException ignored) {}

        return info;
    }

    // ---------- iPhone (libimobiledevice) ----------

    static BatteryInfo getIosBattery() {
        if (!commandExists("idevice_id") || !commandExists("idevicediagnostics")) return null;

        String idOut = run("idevice_id", "-l");
        if (idOut == null || idOut.trim().isEmpty()) return null;

        String udid = idOut.trim().split("\n")[0].trim();

        // Get device name (best-effort)
        String name = "iPhone";
        String nameOut = run("idevicename");
        if (nameOut != null && !nameOut.trim().isEmpty()) {
            name = nameOut.trim();
        }

        // Battery info lives in the IOREGISTRY under AppleSmartBattery
        String dump = run("idevicediagnostics", "ioregentry", "AppleSmartBattery");
        if (dump == null) return null;

        BatteryInfo info = new BatteryInfo();
        info.platform = "iOS";
        info.device = name + " (" + udid.substring(0, Math.min(8, udid.length())) + "...)";

        // The plist output is line-based; we parse the keys we care about
        info.level = parsePlistInt(dump, "CurrentCapacity");
        boolean charging = parsePlistBool(dump, "IsCharging");
        boolean fullyCharged = parsePlistBool(dump, "FullyCharged");
        boolean externalConnected = parsePlistBool(dump, "ExternalConnected");

        if (fullyCharged) info.status = "Full";
        else if (charging) info.status = "Charging";
        else info.status = "Discharging";

        info.source = externalConnected ? "USB" : "Not charging";

        return info;
    }

    /** Pulls an integer value out of a plist-style key/value dump. */
    static int parsePlistInt(String dump, String key) {
        for (String line : dump.split("\n")) {
            if (line.contains("<key>" + key + "</key>")) {
                // Value is on the next non-empty line typically — handle inline format too
                int idx = dump.indexOf(line) + line.length();
                String rest = dump.substring(idx);
                int open = rest.indexOf("<integer>");
                int close = rest.indexOf("</integer>");
                if (open >= 0 && close > open && open < 200) {
                    try {
                        return Integer.parseInt(rest.substring(open + 9, close).trim());
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return -1;
    }

    static boolean parsePlistBool(String dump, String key) {
        for (String line : dump.split("\n")) {
            if (line.contains("<key>" + key + "</key>")) {
                int idx = dump.indexOf(line) + line.length();
                String rest = dump.substring(idx, Math.min(rest_safeEnd(dump, idx), dump.length()));
                if (rest.contains("<true/>")) return true;
                if (rest.contains("<false/>")) return false;
            }
        }
        return false;
    }

    private static int rest_safeEnd(String s, int idx) {
        return Math.min(idx + 200, s.length());
    }

    // ---------- Display ----------

    static BatteryInfo detect() {
        BatteryInfo a = getAndroidBattery();
        if (a != null) return a;
        return getIosBattery();
    }

    static String renderBar(int level, int width) {
        if (level < 0) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < width; i++) sb.append("?");
            return sb.append("]").toString();
        }
        int filled = (int) Math.round(level / 100.0 * width);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < filled; i++) sb.append("█");
        for (int i = filled; i < width; i++) sb.append("░");
        return sb.append("]").toString();
    }

    static String formatLine(BatteryInfo info) {
        StringBuilder sb = new StringBuilder();
        sb.append(info.platform).append(" (").append(info.device).append("): ")
          .append(renderBar(info.level, 20)).append(" ")
          .append(info.level).append("%  ")
          .append(info.status).append(" via ").append(info.source);

        if (info.temperatureC != null || info.voltageV != null) {
            sb.append("  (");
            boolean first = true;
            if (info.temperatureC != null) {
                sb.append(info.temperatureC).append("°C");
                first = false;
            }
            if (info.voltageV != null) {
                if (!first) sb.append(", ");
                sb.append(info.voltageV).append("V");
            }
            sb.append(")");
        }
        return sb.toString();
    }

    // ---------- Main ----------

    public static void main(String[] args) throws InterruptedException {
        boolean watch = false;
        boolean json = false;
        double interval = 2.0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--watch": watch = true; break;
                case "--json":  json = true;  break;
                case "--interval":
                    if (i + 1 < args.length) {
                        try { interval = Double.parseDouble(args[++i]); }
                        catch (NumberFormatException ignored) {}
                    }
                    break;
                case "--help":
                case "-h":
                    System.out.println("Usage: java PhoneBattery [--watch] [--json] [--interval SECONDS]");
                    return;
            }
        }

        if (watch) {
            // Graceful Ctrl+C
            Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println()));
            while (true) {
                BatteryInfo info = detect();
                System.out.print("\r\033[K");
                if (info != null) {
                    System.out.print(formatLine(info));
                } else {
                    System.out.print("No phone detected. Check USB debugging (Android) or 'Trust' (iPhone).");
                }
                System.out.flush();
                Thread.sleep((long) (interval * 1000));
            }
        }

        BatteryInfo info = detect();
        if (info == null) {
            String msg = "No phone detected. Make sure: (Android) USB debugging is on and accepted; (iPhone) you tapped 'Trust this computer'.";
            if (json) {
                System.out.println("{\"error\":\"" + msg + "\"}");
            } else {
                System.err.println(msg);
            }
            System.exit(1);
        }

        if (json) {
            System.out.println(info.toJson());
        } else {
            System.out.println(formatLine(info));
        }
    }
}
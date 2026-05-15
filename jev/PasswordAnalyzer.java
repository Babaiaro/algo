import java.util.*;

/**
 * Password Strength Analyzer
 *
 * Estimates how strong a password is by calculating its entropy (in bits)
 * and converting that into an estimated crack time at realistic attack speeds.
 *
 * Compile:  javac PasswordAnalyzer.java
 * Run:      java PasswordAnalyzer
 *           java PasswordAnalyzer "mypassword123"
 *
 * What it checks:
 *   - Character variety (lowercase, uppercase, digits, symbols)
 *   - Length
 *   - Common password blacklist
 *   - Dictionary words inside the password
 *   - Predictable patterns (sequences, repeats, dates, keyboard runs)
 *   - Leetspeak substitutions (p@ssw0rd → password)
 */
public class PasswordAnalyzer {

    // Attacker assumptions:
    //   - Online throttled: ~10 guesses/sec (rate-limited login form)
    //   - Online unthrottled: ~10,000 guesses/sec
    //   - Offline slow hash (bcrypt): ~10,000 guesses/sec
    //   - Offline fast hash (MD5/SHA1 on a single modern GPU): ~10 billion/sec
    static final double GUESSES_PER_SEC_OFFLINE_FAST = 1e10;

    // A small starter list. In production you'd load rockyou.txt or HIBP top-N.
    static final Set<String> COMMON_PASSWORDS = new HashSet<>(Arrays.asList(
        "password", "123456", "12345678", "qwerty", "abc123", "monkey", "letmein",
        "dragon", "111111", "baseball", "iloveyou", "trustno1", "sunshine",
        "master", "welcome", "shadow", "ashley", "football", "jesus", "michael",
        "ninja", "mustang", "password1", "admin", "login", "starwars", "hello",
        "freedom", "whatever", "qazwsx", "superman", "batman", "passw0rd"
    ));

    // Common English words that often appear in passwords.
    static final Set<String> DICTIONARY = new HashSet<>(Arrays.asList(
        "love", "hate", "the", "and", "for", "you", "are", "but", "not", "all",
        "summer", "winter", "spring", "autumn", "blue", "red", "green", "black",
        "white", "dog", "cat", "house", "home", "work", "play", "fire", "water",
        "king", "queen", "prince", "money", "happy", "sad", "good", "bad",
        "java", "code", "test", "user", "name", "pass", "word", "secret",
        "hello", "world", "ninja", "tiger", "lion", "dragon", "phoenix"
    ));

    static final String[] KEYBOARD_RUNS = {
        "qwertyuiop", "asdfghjkl", "zxcvbnm",
        "1234567890", "0987654321",
        "abcdefghijklmnopqrstuvwxyz"
    };

    // Common leet substitutions (used to "un-leet" before dictionary check)
    static final Map<Character, Character> LEET = Map.of(
        '0','o', '1','i', '3','e', '4','a', '5','s', '7','t', '@','a', '$','s'
    );

    // ===== Result holder =====
    static class Result {
        double entropyBits;
        String verdict;
        String crackTime;
        List<String> warnings = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();
    }

    public static void main(String[] args) {
        String password;
        if (args.length > 0) {
            password = args[0];
        } else {
            Scanner s = new Scanner(System.in);
            System.out.print("Enter a password to analyze: ");
            password = s.nextLine();
        }

        Result r = analyze(password);
        printReport(password, r);
    }

    // ===== Main entry point =====
    public static Result analyze(String password) {
        Result r = new Result();

        if (password == null || password.isEmpty()) {
            r.entropyBits = 0;
            r.verdict = "Empty";
            r.crackTime = "instant";
            r.warnings.add("Password is empty.");
            return r;
        }

        // Start with raw character-pool entropy
        double entropy = rawEntropy(password);

        // Apply penalties for predictable patterns
        double penalty = patternPenalty(password, r);
        entropy -= penalty;
        if (entropy < 0) entropy = 0;

        r.entropyBits = entropy;
        r.crackTime = crackTimeFromEntropy(entropy);
        r.verdict = verdictFromEntropy(entropy);
        addSuggestions(password, r);
        return r;
    }

    // ===== Step 1: raw entropy from character set size =====
    static double rawEntropy(String pwd) {
        int poolSize = 0;
        boolean lower = false, upper = false, digit = false, symbol = false;
        for (char c : pwd.toCharArray()) {
            if      (Character.isLowerCase(c)) lower = true;
            else if (Character.isUpperCase(c)) upper = true;
            else if (Character.isDigit(c))     digit = true;
            else                               symbol = true;
        }
        if (lower)  poolSize += 26;
        if (upper)  poolSize += 26;
        if (digit)  poolSize += 10;
        if (symbol) poolSize += 32;   // approx printable symbol count

        // entropy (bits) = length * log2(poolSize)
        return pwd.length() * (Math.log(poolSize) / Math.log(2));
    }

    // ===== Step 2: penalize predictable patterns =====
    static double patternPenalty(String pwd, Result r) {
        double penalty = 0;
        String lower = pwd.toLowerCase();

        // (a) In common-password blacklist? massive penalty
        if (COMMON_PASSWORDS.contains(lower)) {
            r.warnings.add("This is one of the most common passwords ever.");
            penalty += 30;
        }

        // (b) Contains a dictionary word?
        String unleet = unleet(lower);
        for (String word : DICTIONARY) {
            if (word.length() >= 3 && (lower.contains(word) || unleet.contains(word))) {
                r.warnings.add("Contains a dictionary word: \"" + word + "\".");
                penalty += word.length() * 2;
                break;  // one warning is enough
            }
        }

        // (c) Long runs of repeated characters (aaaa, 1111)
        if (pwd.matches(".*(.)\\1{2,}.*")) {
            r.warnings.add("Contains repeated characters (like 'aaaa' or '111').");
            penalty += 6;
        }

        // (d) Sequential / keyboard runs (1234, qwerty, abcd)
        for (String run : KEYBOARD_RUNS) {
            for (int i = 0; i <= run.length() - 4; i++) {
                String slice = run.substring(i, i + 4);
                if (lower.contains(slice) || lower.contains(new StringBuilder(slice).reverse().toString())) {
                    r.warnings.add("Contains a predictable sequence (like '1234' or 'qwer').");
                    penalty += 8;
                    return penalty;  // stop after first match
                }
            }
        }

        // (e) Looks like a year (19xx or 20xx)
        if (pwd.matches(".*(19|20)\\d{2}.*")) {
            r.warnings.add("Contains a year — easy to guess.");
            penalty += 4;
        }

        // (f) All lowercase letters → small pool
        if (pwd.matches("^[a-z]+$")) {
            r.warnings.add("Only lowercase letters.");
            penalty += 4;
        }

        // (g) All digits → very small pool
        if (pwd.matches("^[0-9]+$")) {
            r.warnings.add("Only digits — very weak.");
            penalty += 10;
        }

        return penalty;
    }

    /** Replace leet characters with their letter equivalents for dictionary checks. */
    static String unleet(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(LEET.getOrDefault(c, c));
        }
        return sb.toString();
    }

    // ===== Step 3: turn entropy bits into a human time estimate =====
    static String crackTimeFromEntropy(double bits) {
        double totalGuesses = Math.pow(2, bits) / 2.0;  // average case
        double seconds = totalGuesses / GUESSES_PER_SEC_OFFLINE_FAST;
        return humanTime(seconds);
    }

    static String humanTime(double seconds) {
        if (seconds < 1)               return "instant";
        if (seconds < 60)              return String.format("%.0f seconds", seconds);
        if (seconds < 3600)            return String.format("%.0f minutes", seconds / 60);
        if (seconds < 86400)           return String.format("%.0f hours",   seconds / 3600);
        if (seconds < 86400 * 365)     return String.format("%.0f days",    seconds / 86400);
        double years = seconds / (86400 * 365);
        if (years < 1000)              return String.format("%.0f years", years);
        if (years < 1e6)               return String.format("%.0f thousand years", years / 1e3);
        if (years < 1e9)               return String.format("%.0f million years",  years / 1e6);
        if (years < 1e12)              return String.format("%.0f billion years",  years / 1e9);
        return "longer than the age of the universe";
    }

    // ===== Step 4: short verdict label =====
    static String verdictFromEntropy(double bits) {
        if (bits < 28)  return "Very Weak";
        if (bits < 36)  return "Weak";
        if (bits < 60)  return "Reasonable";
        if (bits < 80)  return "Strong";
        return "Very Strong";
    }

    // ===== Step 5: tailored suggestions =====
    static void addSuggestions(String pwd, Result r) {
        if (pwd.length() < 12) {
            r.suggestions.add("Use at least 12 characters (length matters more than complexity).");
        }
        if (!pwd.matches(".*[A-Z].*")) r.suggestions.add("Mix in some uppercase letters.");
        if (!pwd.matches(".*[a-z].*")) r.suggestions.add("Mix in some lowercase letters.");
        if (!pwd.matches(".*[0-9].*")) r.suggestions.add("Add a few digits.");
        if (!pwd.matches(".*[^A-Za-z0-9].*")) r.suggestions.add("Add a symbol or two (!, #, %, etc.).");
        if (r.warnings.stream().anyMatch(w -> w.contains("dictionary"))) {
            r.suggestions.add("Avoid real words — try a passphrase of 4+ random words instead.");
        }
    }

    // ===== Pretty report =====
    static void printReport(String pwd, Result r) {
        String masked = "*".repeat(pwd.length());
        System.out.println();
        System.out.println("═══════════════════════════════════════════");
        System.out.println("        PASSWORD STRENGTH REPORT");
        System.out.println("═══════════════════════════════════════════");
        System.out.println(" Password:   " + masked + "  (" + pwd.length() + " chars)");
        System.out.printf ( " Entropy:    %.1f bits%n", r.entropyBits);
        System.out.println(" Verdict:    " + r.verdict);
        System.out.println(" Crack time: " + r.crackTime + "  (offline, fast hash)");
        System.out.println(" Strength:   " + meter(r.entropyBits));

        if (!r.warnings.isEmpty()) {
            System.out.println("\n ⚠  Issues found:");
            for (String w : r.warnings) System.out.println("    • " + w);
        }
        if (!r.suggestions.isEmpty()) {
            System.out.println("\n 💡 Suggestions:");
            for (String s : r.suggestions) System.out.println("    • " + s);
        }
        System.out.println();
    }

    /** Simple ASCII bar that fills based on entropy. */
    static String meter(double bits) {
        int filled = (int) Math.min(20, bits / 4);   // 80 bits = full bar
        return "[" + "█".repeat(filled) + "░".repeat(20 - filled) + "]";
    }
}

package java_olds;
import java.util.ArrayList;
import java.util.List;

// ─── ENGINE ──────────────────────────────────────────────────────────────────
class Engine {
    private String type;       // PETROL, DIESEL, ELECTRIC, HYBRID
    private int horsepower;
    private double torque;     // Nm
    private boolean running;

    public Engine(String type, int horsepower, double torque) {
        this.type = type;
        this.horsepower = horsepower;
        this.torque = torque;
        this.running = false;
    }

    public void start() { running = true;  System.out.println("Engine started [" + type + "]"); }
    public void stop()  { running = false; System.out.println("Engine stopped."); }

    public String  getType()       { return type; }
    public int     getHorsepower() { return horsepower; }
    public double  getTorque()     { return torque; }
    public boolean isRunning()     { return running; }

    @Override
    public String toString() {
        return String.format("Engine{type=%s, hp=%d, torque=%.1fNm, running=%b}",
                type, horsepower, torque, running);
    }
}

// ─── SPEED ───────────────────────────────────────────────────────────────────
class Speed {
    private double max;           // km/h
    private double current;       // km/h
    private double acceleration;  // km/h per second

    public Speed(double max, double acceleration) {
        this.max = max;
        this.current = 0;
        this.acceleration = acceleration;
    }

    public void accelerate(double seconds) {
        current = Math.min(current + acceleration * seconds, max);
        System.out.printf("Accelerating... current speed: %.1f km/h%n", current);
    }

    public void brake(double reduction) {
        current = Math.max(0, current - reduction);
        System.out.printf("Braking... current speed: %.1f km/h%n", current);
    }

    public void fullStop() { current = 0; System.out.println("Full stop."); }

    public double getMax()          { return max; }
    public double getCurrent()      { return current; }
    public double getAcceleration() { return acceleration; }

    @Override
    public String toString() {
        return String.format("Speed{max=%.1f, current=%.1f, accel=%.1f km/h/s}",
                max, current, acceleration);
    }
}

// ─── FUEL TANK ───────────────────────────────────────────────────────────────
class FuelTank {
    private String fuelType;
    private double capacity;   // liters
    private double level;      // liters

    public FuelTank(String fuelType, double capacity) {
        this.fuelType = fuelType;
        this.capacity = capacity;
        this.level = capacity;
    }

    public void refuel(double amount) {
        level = Math.min(level + amount, capacity);
        System.out.printf("Refueled. Tank: %.1f / %.1f L%n", level, capacity);
    }

    public boolean consume(double amount) {
        if (level < amount) {
            System.out.println("Not enough fuel!");
            return false;
        }
        level -= amount;
        return true;
    }

    public double getPercentage() { return (level / capacity) * 100; }
    public double getLevel()      { return level; }
    public double getCapacity()   { return capacity; }
    public String getFuelType()   { return fuelType; }
    public boolean isEmpty()      { return level <= 0; }

    @Override
    public String toString() {
        return String.format("FuelTank{type=%s, %.1f/%.1fL (%.0f%%)}",
                fuelType, level, capacity, getPercentage());
    }
}

// ─── GPS ─────────────────────────────────────────────────────────────────────
class GPS {
    private double latitude;
    private double longitude;
    private String destination;

    public GPS(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setDestination(String destination) {
        this.destination = destination;
        System.out.println("GPS destination set: " + destination);
    }

    public double distanceTo(double lat, double lon) {
        // Haversine approximation (km)
        final double R = 6371;
        double dLat = Math.toRadians(lat - latitude);
        double dLon = Math.toRadians(lon - longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public void updatePosition(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getLatitude()    { return latitude; }
    public double getLongitude()   { return longitude; }
    public String getDestination() { return destination; }

    @Override
    public String toString() {
        return String.format("GPS{lat=%.4f, lon=%.4f, dest=%s}",
                latitude, longitude, destination);
    }
}

// ─── DRIVER ──────────────────────────────────────────────────────────────────
class Driver {
    private String name;
    private String licenseNumber;
    private int    experienceYears;
    private int    age;

    public Driver(String name, String licenseNumber, int age, int experienceYears) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.age = age;
        this.experienceYears = experienceYears;
    }

    public boolean isEligible()     { return age >= 18 && licenseNumber != null; }
    public String  getName()        { return name; }
    public String  getLicense()     { return licenseNumber; }
    public int     getAge()         { return age; }
    public int     getExperience()  { return experienceYears; }

    @Override
    public String toString() {
        return String.format("Driver{name=%s, license=%s, age=%d, exp=%d yrs}",
                name, licenseNumber, age, experienceYears);
    }
}

// ─── VEHICLE (aggregates everything) ─────────────────────────────────────────
class Vehicle {
    private String vin;
    private String brand;
    private String model;
    private int    year;

    // Aggregated — exist independently of Vehicle
    private Engine   engine;
    private Speed    speed;
    private FuelTank fuelTank;
    private GPS      gps;
    private Driver   driver;

    public Vehicle(String vin, String brand, String model, int year,
                   Engine engine, Speed speed, FuelTank fuelTank, GPS gps) {
        this.vin      = vin;
        this.brand    = brand;
        this.model    = model;
        this.year     = year;
        this.engine   = engine;
        this.speed    = speed;
        this.fuelTank = fuelTank;
        this.gps      = gps;
    }

    public boolean assignDriver(Driver driver) {
        if (!driver.isEligible()) {
            System.out.println("Driver " + driver.getName() + " is not eligible.");
            return false;
        }
        this.driver = driver;
        System.out.println(driver.getName() + " assigned to " + brand + " " + model);
        return true;
    }

    public void ignite() {
        if (driver == null)           { System.out.println("No driver assigned!"); return; }
        if (fuelTank.isEmpty())       { System.out.println("Tank is empty!"); return; }
        engine.start();
    }

    public void drive(double seconds) {
        if (!engine.isRunning()) { System.out.println("Start the engine first!"); return; }
        if (!fuelTank.consume(seconds * 0.03)) return;
        speed.accelerate(seconds);
    }

    public void stop() {
        speed.fullStop();
        engine.stop();
    }

    public void navigateTo(String destination, double lat, double lon) {
        gps.setDestination(destination);
        double dist = gps.distanceTo(lat, lon);
        System.out.printf("Distance to %s: %.2f km%n", destination, dist);
    }

    public String status() {
        return String.format(
            "\n╔══════════════════════════════════════╗\n" +
            "║  %s %s (%d) — %s\n"                        +
            "╠══════════════════════════════════════╣\n" +
            "║  %s\n"                                      +
            "║  %s\n"                                      +
            "║  %s\n"                                      +
            "║  %s\n"                                      +
            "║  Driver: %s\n"                              +
            "╚══════════════════════════════════════╝",
            brand, model, year, vin,
            engine, speed, fuelTank, gps,
            driver != null ? driver.getName() : "Unassigned"
        );
    }

    // Getters
    public String   getVin()      { return vin; }
    public String   getBrand()    { return brand; }
    public String   getModel()    { return model; }
    public int      getYear()     { return year; }
    public Engine   getEngine()   { return engine; }
    public Speed    getSpeed()    { return speed; }
    public FuelTank getFuelTank() { return fuelTank; }
    public GPS      getGps()      { return gps; }
    public Driver   getDriver()   { return driver; }

    @Override
    public String toString() {
        return brand + " " + model + " (" + year + ") [" + vin + "]";
    }
}

// ─── FLEET (aggregates multiple vehicles) ────────────────────────────────────
class Fleet {
    private String        name;
    private List<Vehicle> vehicles = new ArrayList<>();

    public Fleet(String name) { this.name = name; }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
        System.out.println(v + " added to fleet [" + name + "]");
    }

    public void removeVehicle(String vin) {
        vehicles.removeIf(v -> v.getVin().equals(vin));
        System.out.println("Vehicle " + vin + " removed from fleet.");
    }

    public Vehicle findByVin(String vin) {
        return vehicles.stream()
                .filter(v -> v.getVin().equals(vin))
                .findFirst()
                .orElse(null);
    }

    public int     size()     { return vehicles.size(); }
    public String  getName()  { return name; }

    public void listAll() {
        System.out.println("\n── Fleet: " + name + " (" + vehicles.size() + " vehicles) ──");
        vehicles.forEach(v -> System.out.println("  • " + v));
    }
}

// ─── MAIN ────────────────────────────────────────────────────────────────────
public class aggregation {
    public static void main(String[] args) {

        // Build components independently (aggregation — not composition)
        Engine   bmwEngine  = new Engine("PETROL",  335, 450.0);
        Speed    bmwSpeed   = new Speed(250, 12.5);
        FuelTank bmwTank    = new FuelTank("PETROL", 60);
        GPS      bmwGps     = new GPS(51.5074, -0.1278);

        Engine   teslaEngine = new Engine("ELECTRIC", 670, 900.0);
        Speed    teslaSpeed  = new Speed(322, 18.0);
        FuelTank teslaTank   = new FuelTank("ELECTRIC", 100);
        GPS      teslaGps    = new GPS(40.7128, -74.0060);

        // Create vehicles
        Vehicle bmw   = new Vehicle("WBA3A5C5XFF598543", "BMW",   "M4",       2023, bmwEngine,   bmwSpeed,   bmwTank,   bmwGps);
        Vehicle tesla = new Vehicle("5YJ3E1EA8JF006195", "Tesla", "Model S",  2024, teslaEngine, teslaSpeed, teslaTank, teslaGps);

        // Create drivers (exist independently)
        Driver alex  = new Driver("Alex Carter",  "DL-482910", 30, 8);
        Driver sarah = new Driver("Sarah Mills",  "DL-773621", 25, 4);

        // Assign drivers
        bmw.assignDriver(alex);
        tesla.assignDriver(sarah);

        // Build fleet
        Fleet fleet = new Fleet("Urban Motors");
        fleet.addVehicle(bmw);
        fleet.addVehicle(tesla);
        fleet.listAll();

        // Drive the BMW
        System.out.println("\n─── BMW M4 Test Drive ───");
        bmw.ignite();
        bmw.drive(5);
        bmw.drive(8);
        bmw.navigateTo("Heathrow Airport", 51.4700, -0.4543);
        System.out.println(bmw.status());
        bmw.stop();

        // Drive the Tesla
        System.out.println("\n─── Tesla Model S Test Drive ───");
        tesla.ignite();
        tesla.drive(3);
        tesla.drive(10);
        tesla.navigateTo("Times Square", 40.7580, -73.9855);
        System.out.println(tesla.status());
        tesla.stop();

        // Components still exist independently — that's aggregation
        System.out.println("\n─── Standalone components still alive ───");
        System.out.println(bmwEngine);
        System.out.println(teslaGps);
        System.out.println(alex);
    }
}
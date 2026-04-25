// FILE: QuantityMeasurementApp.java
// Quantity Measurement App

// ============================================================
// UC1: Feet Measurement Equality
// Concepts: Class, equals(), Numeric comparison,
//           Edge case handling, Equality check
// ============================================================

// Feet class — represents a measurement in feet
class Feet {
    private final double value;

    public Feet(double value) {
        this.value = value;
    }

    // Override equals() to compare two Feet values
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Feet other = (Feet) obj;
        return Double.compare(this.value, other.value) == 0;
    }

    public double getValue() { return value; }
}

class QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC1: Feet Measurement Equality");
        System.out.println("====================================");

        // Test case 1: Equal values
        Feet feet1 = new Feet(5.0);
        Feet feet2 = new Feet(5.0);
        System.out.println("\n[TC1] 5.0 feet == 5.0 feet : " + feet1.equals(feet2));

        // Test case 2: Unequal values
        Feet feet3 = new Feet(3.0);
        Feet feet4 = new Feet(7.0);
        System.out.println("[TC2] 3.0 feet == 7.0 feet : " + feet3.equals(feet4));

        // Test case 3: Zero feet
        Feet zero1 = new Feet(0.0);
        Feet zero2 = new Feet(0.0);
        System.out.println("[TC3] 0.0 feet == 0.0 feet : " + zero1.equals(zero2));

        // Test case 4: Negative values
        Feet neg1 = new Feet(-2.0);
        Feet neg2 = new Feet(-2.0);
        System.out.println("[TC4] -2.0 feet == -2.0 feet: " + neg1.equals(neg2));

        // Test case 5: Null comparison
        System.out.println("[TC5] feet1 == null        : " + feet1.equals(null));
    }
}
// ============================================================
// UC2: Feet and Inches Measurement Equality
// Concepts: Multiple classes, equals(), Separate unit handling,
//           Edge cases for both Feet and Inches
// ============================================================

// Inches class — represents a measurement in inches
class Inches {
    private final double value;

    public Inches(double value) {
        this.value = value;
    }

    // Override equals() to compare two Inches values
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Inches other = (Inches) obj;
        return Double.compare(this.value, other.value) == 0;
    }

    public double getValue() { return value; }
}

class UC2QuantityMeasurementApp {

    // Static method to check feet equality
    static boolean checkFeetEquality(double val1, double val2) {
        Feet f1 = new Feet(val1);
        Feet f2 = new Feet(val2);
        return f1.equals(f2);
    }

    // Static method to check inches equality
    static boolean checkInchesEquality(double val1, double val2) {
        Inches i1 = new Inches(val1);
        Inches i2 = new Inches(val2);
        return i1.equals(i2);
    }

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC2: Feet & Inches Equality");
        System.out.println("====================================");

        // Feet equality tests
        System.out.println("\n--- Feet Equality ---");
        System.out.println("[TC1] 5.0 ft == 5.0 ft : " + checkFeetEquality(5.0, 5.0));
        System.out.println("[TC2] 3.0 ft == 7.0 ft : " + checkFeetEquality(3.0, 7.0));
        System.out.println("[TC3] 0.0 ft == 0.0 ft : " + checkFeetEquality(0.0, 0.0));

        // Inches equality tests
        System.out.println("\n--- Inches Equality ---");
        System.out.println("[TC4] 12.0 in == 12.0 in: " + checkInchesEquality(12.0, 12.0));
        System.out.println("[TC5] 6.0 in == 10.0 in : " + checkInchesEquality(6.0, 10.0));
        System.out.println("[TC6] 0.0 in == 0.0 in  : " + checkInchesEquality(0.0, 0.0));
        System.out.println("[TC7] -5.0 in == -5.0 in: " + checkInchesEquality(-5.0, -5.0));
    }
}
// ============================================================
// UC3: Generic Quantity Class (DRY Principle)
// Concepts: DRY Principle, Enum with conversion factors,
//           Generic class, equals() with conversion,
//           Refactoring Feet and Inches into one class
// ============================================================

// LengthUnit enum — defines units and their conversion factors to feet
enum LengthUnit {
    FEET(1.0),              // Base unit
    INCHES(1.0 / 12.0),    // 1 inch = 1/12 foot
    YARDS(3.0),             // 1 yard = 3 feet
    CENTIMETERS(0.393701 / 12.0); // 1 cm = 0.393701 inches = 0.393701/12 feet

    final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }
}

// Generic QuantityLength class — replaces both Feet and Inches
class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        this.value = value;
        this.unit  = unit;
    }

    // Convert this measurement to feet (base unit)
    private double toBaseUnit() {
        return value * unit.conversionFactor;
    }

    // Compare two QuantityLength objects by converting to base unit
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLength other = (QuantityLength) obj;
        // Compare base values with small epsilon for floating point
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 1e-9;
    }

    public double getValue() { return value; }
    public LengthUnit getUnit() { return unit; }
    public double getBaseValue() { return toBaseUnit(); }
}

class UC3QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC3: Generic Quantity (DRY)");
        System.out.println("====================================");

        // Feet equality using generic class
        System.out.println("\n--- Feet Equality (Generic) ---");
        QuantityLength f1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength f2 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength f3 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("[TC1] 5.0 ft == 5.0 ft : " + f1.equals(f2));
        System.out.println("[TC2] 5.0 ft == 3.0 ft : " + f1.equals(f3));

        // Inches equality using generic class
        System.out.println("\n--- Inches Equality (Generic) ---");
        QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength i2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength i3 = new QuantityLength(6.0,  LengthUnit.INCHES);
        System.out.println("[TC3] 12.0 in == 12.0 in: " + i1.equals(i2));
        System.out.println("[TC4] 12.0 in == 6.0 in : " + i1.equals(i3));

        // Cross-unit equality: 12 inches == 1 foot
        System.out.println("\n--- Cross-Unit Equality ---");
        QuantityLength onefoot   = new QuantityLength(1.0,  LengthUnit.FEET);
        QuantityLength twelvein  = new QuantityLength(12.0, LengthUnit.INCHES);
        System.out.println("[TC5] 1.0 ft == 12.0 in : " + onefoot.equals(twelvein));
    }
}
// ============================================================
// UC4: Extended Unit Support (Yards & Centimeters)
// Concepts: Enum extension, Conversion factors,
//           Yards (1 yard = 3 feet), CM (1cm = 0.393701 in)
// ============================================================
class UC4QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC4: Extended Units (Yard & CM)");
        System.out.println("====================================");

        // Feet equality
        System.out.println("\n--- Feet ---");
        QuantityLength f1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength f2 = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println("[TC1] 1.0 ft == 1.0 ft  : " + f1.equals(f2));

        // Inches equality
        System.out.println("\n--- Inches ---");
        QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);
        System.out.println("[TC2] 12.0 in == 1.0 ft : " + i1.equals(f1));

        // Yards equality
        System.out.println("\n--- Yards ---");
        QuantityLength y1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength y2 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength y3 = new QuantityLength(36.0, LengthUnit.INCHES);
        System.out.println("[TC3] 1.0 yd == 3.0 ft  : " + y1.equals(y2));
        System.out.println("[TC4] 1.0 yd == 36.0 in : " + y1.equals(y3));

        // Centimeters equality
        System.out.println("\n--- Centimeters ---");
        QuantityLength cm1 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength in1 = new QuantityLength(1.0,  LengthUnit.INCHES);
        System.out.println("[TC5] 2.54 cm == 1.0 in : " + cm1.equals(in1));

        // Unequal cross-unit
        QuantityLength y4 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength f3 = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println("[TC6] 1.0 yd == 1.0 ft  : " + y4.equals(f3));
    }
}
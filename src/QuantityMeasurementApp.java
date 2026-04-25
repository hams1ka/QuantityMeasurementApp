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
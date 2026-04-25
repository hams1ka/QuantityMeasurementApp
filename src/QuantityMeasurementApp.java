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
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
// UC8: LengthUnit Refactored as Standalone (Single Responsibility)
// Concepts: Standalone enum, Single Responsibility Principle,
//           convertToBaseUnit(), convertFromBaseUnit(),
//           Scalable pattern for new measurement categories
// ============================================================

// LengthUnit — standalone enum with full conversion responsibility
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0);

    private final double conversionFactor; // Factor relative to FEET (base)

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    // Convert a value in THIS unit to base unit (feet)
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    // Convert a value from base unit (feet) to THIS unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }

    public double getConversionFactor() { return conversionFactor; }
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
    // Delegate conversion to the unit itself (UC8 refactoring)
    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
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

    // Convert this measurement to a target unit
    public QuantityLength convertTo(LengthUnit targetUnit) {
        // Step 1: Convert to base unit (feet)
        double baseValue = toBaseUnit();
        // Step 2: Convert from base unit to target unit
        double converted = baseValue / targetUnit.conversionFactor;
        return new QuantityLength(converted, targetUnit);
    }

    // Static conversion method
    public static double convert(double value, LengthUnit from, LengthUnit to) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite: " + value);
        }
        // Convert to base (feet), then to target
        double baseValue = value * from.conversionFactor;
        return baseValue / to.conversionFactor;
    }

    // Add two QuantityLength values — result in unit of first operand
    public static QuantityLength add(QuantityLength l1, QuantityLength l2) {
        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null.");
        if (!Double.isFinite(l1.value) || !Double.isFinite(l2.value))
            throw new IllegalArgumentException("Length values must be finite.");
        // Convert both to base unit (feet) and sum
        double sumInBase = l1.toBaseUnit() + l2.toBaseUnit();
        // Convert result back to unit of first operand
        double result = sumInBase / l1.unit.conversionFactor;
        return new QuantityLength(result, l1.unit);
    }

     // Add two lengths and return result in explicitly specified target unit
    public static QuantityLength add(QuantityLength l1, QuantityLength l2,
                                     LengthUnit targetUnit) {
        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null.");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null.");
        if (!Double.isFinite(l1.value) || !Double.isFinite(l2.value))
            throw new IllegalArgumentException("Length values must be finite.");
        // Convert both to base unit (feet) and sum
        double sumInBase = l1.toBaseUnit() + l2.toBaseUnit();
        // Convert to explicitly specified target unit
        double result = sumInBase / targetUnit.conversionFactor;
        return new QuantityLength(result, targetUnit);
    }

    @Override
    public String toString() {
        return String.format("%.4f %s", value, unit);
    }
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
// ============================================================
// UC5: Unit-to-Unit Conversion (Same Measurement Type)
// Concepts: Conversion method, Base unit normalization,
//           Static convert(), Precision handling
// ============================================================
class UC5QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC5: Unit-to-Unit Conversion");
        System.out.println("====================================");

        // Feet → Inches
        System.out.println("\n--- Feet to Inches ---");
        double result1 = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        System.out.println("[TC1] 1.0 ft -> inches : " + result1);

        // Yards → Feet
        System.out.println("\n--- Yards to Feet ---");
        double result2 = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.FEET);
        System.out.println("[TC2] 1.0 yd -> feet   : " + result2);

        // Yards → Inches
        System.out.println("\n--- Yards to Inches ---");
        double result3 = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCHES);
        System.out.println("[TC3] 1.0 yd -> inches : " + result3);

        // Centimeters → Inches
        System.out.println("\n--- Centimeters to Inches ---");
        double result4 = QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        System.out.println("[TC4] 2.54 cm -> inches: " + String.format("%.4f", result4));

        // Instance method conversion
        System.out.println("\n--- Instance Conversion ---");
        QuantityLength length = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength converted = length.convertTo(LengthUnit.INCHES);
        System.out.println("[TC5] 3.0 ft -> " + converted);

        // Invalid input test
        System.out.println("\n--- Invalid Input ---");
        try {
            QuantityLength.convert(Double.POSITIVE_INFINITY,
                                   LengthUnit.FEET, LengthUnit.INCHES);
        } catch (IllegalArgumentException e) {
            System.out.println("[TC6] Infinity -> [ERROR] " + e.getMessage());
        }
    }
}
// ============================================================
// UC6: Addition of Two Length Units (Same Category)
// Concepts: Addition of mixed units, Base unit conversion,
//           Result in first operand's unit
// ============================================================
class UC6QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC6: Length Addition");
        System.out.println("====================================");

        // 1 foot + 12 inches = 2 feet
        System.out.println("\n--- Feet + Inches ---");
        QuantityLength l1 = new QuantityLength(1.0,  LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength sum1 = QuantityLength.add(l1, l2);
        System.out.println("[TC1] 1.0 ft + 12.0 in = " + sum1);

        // 1 yard + 1 foot = result in yards
        System.out.println("\n--- Yards + Feet ---");
        QuantityLength l3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength l4 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength sum2 = QuantityLength.add(l3, l4);
        System.out.println("[TC2] 1.0 yd + 1.0 ft  = " + sum2);

        // 2.54 cm + 1 inch = result in cm
        System.out.println("\n--- CM + Inches ---");
        QuantityLength l5 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength l6 = new QuantityLength(1.0,  LengthUnit.INCHES);
        QuantityLength sum3 = QuantityLength.add(l5, l6);
        System.out.println("[TC3] 2.54 cm + 1.0 in = " + sum3);

        // Null input test
        System.out.println("\n--- Null Input ---");
        try {
            QuantityLength.add(null, l1);
        } catch (IllegalArgumentException e) {
            System.out.println("[TC4] null + l1 -> [ERROR] " + e.getMessage());
        }
    }
}
// ============================================================
// UC7: Addition with Target Unit Specification
// Concepts: Overloaded add(), Explicit target unit,
//           Flexible result unit, Same-category addition
// ============================================================
class UC7QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC7: Addition with Target Unit");
        System.out.println("====================================");

        QuantityLength l1 = new QuantityLength(1.0,  LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // 1 foot + 12 inches → result in YARDS
        System.out.println("\n--- Result in Yards ---");
        QuantityLength sum1 = QuantityLength.add(l1, l2, LengthUnit.YARDS);
        System.out.println("[TC1] 1.0 ft + 12.0 in (in yards)      = " + sum1);

        // 1 foot + 12 inches → result in CENTIMETERS
        System.out.println("\n--- Result in Centimeters ---");
        QuantityLength sum2 = QuantityLength.add(l1, l2, LengthUnit.CENTIMETERS);
        System.out.println("[TC2] 1.0 ft + 12.0 in (in cm)         = " + sum2);

        // 1 yard + 3 feet → result in INCHES
        System.out.println("\n--- Yards + Feet → Inches ---");
        QuantityLength l3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength l4 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength sum3 = QuantityLength.add(l3, l4, LengthUnit.INCHES);
        System.out.println("[TC3] 1.0 yd + 3.0 ft (in inches)      = " + sum3);

        // Null target unit
        System.out.println("\n--- Null Target Unit ---");
        try {
            QuantityLength.add(l1, l2, null);
        } catch (IllegalArgumentException e) {
            System.out.println("[TC4] null target -> [ERROR] " + e.getMessage());
        }
    }
}
// UC8 main — verifies backward compatibility after refactoring
class UC8QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Quantity Measurement App");
        System.out.println("   UC8: Refactored LengthUnit");
        System.out.println("====================================");

        System.out.println("\n--- Backward Compatibility Checks ---");

        // UC1: Feet equality
        QuantityLength f1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength f2 = new QuantityLength(5.0, LengthUnit.FEET);
        System.out.println("[UC1] 5.0 ft == 5.0 ft         : " + f1.equals(f2));

        // UC3: Cross-unit equality
        QuantityLength ft = new QuantityLength(1.0,  LengthUnit.FEET);
        QuantityLength in = new QuantityLength(12.0, LengthUnit.INCHES);
        System.out.println("[UC3] 1.0 ft == 12.0 in        : " + ft.equals(in));

        // UC4: Yard equality
        QuantityLength yd  = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength ft3 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("[UC4] 1.0 yd == 3.0 ft         : " + yd.equals(ft3));

        // UC5: Conversion
        double converted = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        System.out.println("[UC5] 1.0 ft -> in             : " + converted);

        // UC6: Addition
        QuantityLength sum = QuantityLength.add(ft, in);
        System.out.println("[UC6] 1.0 ft + 12.0 in         : " + sum);

        // UC7: Addition with target unit
        QuantityLength sum2 = QuantityLength.add(ft, in, LengthUnit.YARDS);
        System.out.println("[UC7] 1.0 ft + 12.0 in (yards) : " + sum2);

        // Unit conversion methods (new in UC8)
        System.out.println("\n--- New UC8 Unit Methods ---");
        System.out.println("[UC8] 12.0 in convertToBase    : " +
                           LengthUnit.INCHES.convertToBaseUnit(12.0) + " ft");
        System.out.println("[UC8] 1.0 ft convertFromBase   : " +
                           LengthUnit.INCHES.convertFromBaseUnit(1.0) + " in");
    }
}
package Mingo.WeirdItems.Helpers.Buff;

public enum StrengthType {
    WEAK(2.5D),
    MEH(3.5),
    STRONG(7.5D),
    MEGA_STRONG(11.0D),
    GOKU(25.0D);

    double strength;

    StrengthType(double strength) {
        this.strength = strength;
    }

    public double getStrength() { return this.strength; }
}

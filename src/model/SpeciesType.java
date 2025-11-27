package model;

public enum SpeciesType {
    FIRE, WATER, GRASS;

    // Helper untuk konversi ID angka (legacy) ke Enum
    public static SpeciesType fromId(int id) {
        if (id == 0) return FIRE;
        if (id == 1) return WATER;
        if (id == 2) return GRASS;
        return FIRE; // Default
    }
}
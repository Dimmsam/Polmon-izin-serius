package model;

public enum SpeciesType {
    KUCING, SLIME;

    public static SpeciesType fromId(int id) {
        if (id == 0) return KUCING;
        if (id == 1) return SLIME;
        return KUCING;
    }
}
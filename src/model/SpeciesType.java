package model;

public enum SpeciesType {
    FIRE, WATER; // Cukup 2 jenis sesuai gambar

    public static SpeciesType fromId(int id) {
        if (id == 0) return FIRE;
        return WATER; // Default ke WATER jika ID 1 atau lainnya
    }
}
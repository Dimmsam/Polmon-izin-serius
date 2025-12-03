package model;

public class MonsterDatabase {
    public static String getName(SpeciesType type, EvolutionStage stage) {
        if (stage == EvolutionStage.EGG) return "Mystery Egg";

        switch (type) {
            case KUCING:
                if (stage == EvolutionStage.BABY) return "Kitty";
                if (stage == EvolutionStage.CHILD) return "Catmon";
                return "Meowster";
            case SLIME:
                if (stage == EvolutionStage.BABY) return "Slimelet";
                if (stage == EvolutionStage.CHILD) return "Slimeon";
                return "Slimeking";
            default:
                return "Unknown";
        }
    }
}
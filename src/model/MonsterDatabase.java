package model;

public class MonsterDatabase {
    public static String getName(SpeciesType type, EvolutionStage stage) {
        if (stage == EvolutionStage.EGG) return "Mystery Egg";

        switch (type) {
            case FIRE:
                if (stage == EvolutionStage.BABY) return "Charmby";
                if (stage == EvolutionStage.CHILD) return "Charmeleon";
                return "Charizard";
            case WATER:
                if (stage == EvolutionStage.BABY) return "Squirtby";
                if (stage == EvolutionStage.CHILD) return "Wartortle";
                return "Blastoise";
            case GRASS:
                if (stage == EvolutionStage.BABY) return "Bulbaby";
                if (stage == EvolutionStage.CHILD) return "Ivysaur";
                return "Venusaur";
            default: return "Unknown";
        }
    }
}
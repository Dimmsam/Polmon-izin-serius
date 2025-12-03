package model;

public class EvolutionManager {

    private static final int HATCH_TIME = 180;   // 3 menit (180 detik) jadi Baby
    private static final int CHILD_TIME = 600;   // 10 menit (600 detik) jadi Child
    private static final int ADULT_TIME = 1500;  // 25 menit (1500 detik) jadi Adult

    public static boolean canEvolve(Monster monster) {
        int age = monster.getAgeSeconds();
        EvolutionStage stage = monster.getStage();

        if (stage == EvolutionStage.EGG && age >= HATCH_TIME) return true;
        if (stage == EvolutionStage.BABY && age >= CHILD_TIME) return true;
        if (stage == EvolutionStage.CHILD && age >= ADULT_TIME) return true;

        return false;
    }

    public static void evolve(Monster monster) {
        EvolutionStage current = monster.getStage();
        EvolutionStage next = current;

        switch (current) {
            case EGG:
                next = EvolutionStage.BABY;
                break;
            case BABY:
                next = EvolutionStage.CHILD;
                break;
            case CHILD:
                next = EvolutionStage.ADULT;
                break;
            default:
                return;
        }

        monster.setStage(next);
        monster.setName(MonsterDatabase.getName(monster.getSpecies(), next));
        monster.setHp(monster.getMaxHP());
        monster.modifyHappiness(100);
    }
}
package model;

public class EvolutionManager {

    // Waktu dalam DETIK (biar cepat saat demo/testing)
    private static final int HATCH_TIME = 10;   // 10 detik jadi Baby
    private static final int CHILD_TIME = 30;   // 30 detik jadi Child
    private static final int ADULT_TIME = 60;   // 60 detik jadi Adult

    public static boolean canEvolve(Monster monster) {
        int age = monster.getAgeSeconds();
        EvolutionStage stage = monster.getStage();

        // Logika sederhana: Cek umur & stage saat ini
        if (stage == EvolutionStage.EGG && age >= HATCH_TIME) return true;
        if (stage == EvolutionStage.BABY && age >= CHILD_TIME) return true;
        if (stage == EvolutionStage.CHILD && age >= ADULT_TIME) return true;

        return false;
    }

    public static void evolve(Monster monster) {
        EvolutionStage current = monster.getStage();
        EvolutionStage next = current;

        // Tentukan stage selanjutnya
        switch (current) {
            case EGG: next = EvolutionStage.BABY; break;
            case BABY: next = EvolutionStage.CHILD; break;
            case CHILD: next = EvolutionStage.ADULT; break;
            default: return; // Adult tidak evolusi lagi
        }

        monster.setStage(next);
        System.out.println("✨ EVOLUSI BERHASIL! Sekarang menjadi " + next + " ✨");
    }
}
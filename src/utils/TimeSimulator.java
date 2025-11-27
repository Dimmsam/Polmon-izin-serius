package utils;

import model.Monster;

public class TimeSimulator {

    public static void simulateTime(Monster monster) {
        if (monster == null) return;

        double barHours = monster.getMaxHP() / 8.0;
        int hoursLastFed = monster.getHoursSinceLastFed();
        double elapsed = 0;

        while (elapsed < hoursLastFed) {
            monster.starve();
            elapsed += barHours;

            if (monster.getHP() == 0) {
                break;
            }
        }
    }
}
package view;

import model.Monster;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StatsDialog {
    
    public static void show(JFrame parent, Monster monster) {
        if (monster == null) {
            JOptionPane.showMessageDialog(parent, "No monster data");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== MONSTER STATS ===\n\n");
        sb.append("Name: ").append(monster.getName()).append("\n");
        sb.append("Species: ").append(monster.getSpecies()).append("\n");
        sb.append("Stage: ").append(monster.getStage()).append("\n");
        sb.append("State: ").append(monster.getStateName()).append("\n\n");
        
        sb.append("HP: ").append(monster.getHP()).append("/").append(monster.getMaxHP()).append("\n");
        sb.append("Energy: ").append(monster.getEnergy()).append("/").append(monster.getMaxEnergy()).append("\n");
        sb.append("Happiness: ").append(monster.getHappiness()).append("/100\n");
        sb.append("Hunger: ").append(monster.getHunger()).append("/100\n\n");
        
        sb.append("Age: ").append(monster.getAgeSeconds()).append(" seconds\n");
        sb.append("Days Old: ").append(monster.getDaysOld()).append(" days\n");
        sb.append("Birthday: ").append(monster.getBirthday());
        
        JOptionPane.showMessageDialog(parent, sb.toString(), 
            "Monster Stats", JOptionPane.INFORMATION_MESSAGE);
    }
}

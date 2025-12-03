package view;

import model.Monster;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Renderer {
    private AssetLoader assetLoader;
    
    public Renderer(AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
    }
    
    public void renderStats(Graphics2D g, Monster monster, int canvasWidth, int canvasHeight) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        renderHPBar(g, monster);
        renderEnergyBar(g, monster);
        renderHappinessBar(g, monster);
        renderInfo(g, monster);
    }
    
    private void renderHPBar(Graphics2D g, Monster monster) {
        int barWidth = 150;
        int barHeight = 20;
        int x = 10;
        int y = 10;
        
        g.setColor(new Color(200, 200, 200));
        g.fillRect(x, y, barWidth, barHeight);
        
        float hpRatio = (float) monster.getHP() / monster.getMaxHP();
        int fillWidth = (int) (barWidth * hpRatio);
        
        Color hpColor = getHPColor(hpRatio);
        g.setColor(hpColor);
        g.fillRect(x, y, fillWidth, barHeight);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String text = "HP: " + monster.getHP() + "/" + monster.getMaxHP();
        g.drawString(text, x + 5, y + 15);
    }
    
    private void renderEnergyBar(Graphics2D g, Monster monster) {
        int barWidth = 150;
        int barHeight = 20;
        int x = 10;
        int y = 35;
        
        g.setColor(new Color(200, 200, 200));
        g.fillRect(x, y, barWidth, barHeight);
        
        float energyRatio = (float) monster.getEnergy() / monster.getMaxEnergy();
        int fillWidth = (int) (barWidth * energyRatio);
        
        Color energyColor = new Color(255, 200, 0);
        g.setColor(energyColor);
        g.fillRect(x, y, fillWidth, barHeight);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String text = "Energy: " + monster.getEnergy() + "/" + monster.getMaxEnergy();
        g.drawString(text, x + 5, y + 15);
    }
    
    private void renderHappinessBar(Graphics2D g, Monster monster) {
        int barWidth = 150;
        int barHeight = 20;
        int x = 10;
        int y = 60;
        
        g.setColor(new Color(200, 200, 200));
        g.fillRect(x, y, barWidth, barHeight);
        
        float happyRatio = (float) monster.getHappiness() / 100;
        int fillWidth = (int) (barWidth * happyRatio);
        
        Color happyColor = getHappinessColor(happyRatio);
        g.setColor(happyColor);
        g.fillRect(x, y, fillWidth, barHeight);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String text = "Happy: " + monster.getHappiness() + "/100";
        g.drawString(text, x + 5, y + 15);
    }
    
    private void renderInfo(Graphics2D g, Monster monster) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        
        int x = 10;
        int y = 90;
        
        g.drawString("Name: " + monster.getName(), x, y);
        
        String stateDisplay = monster.getStateName().replace("State", "");
        g.drawString("State: " + stateDisplay, x, y + 20);
        
        g.drawString("Stage: " + monster.getStage(), x, y + 40);
        
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Age: " + formatAge(monster.getAgeSeconds()), x, y + 60);
    }
    
    private Color getHPColor(float ratio) {
        if (ratio > 0.6f) return new Color(0, 200, 0);
        if (ratio > 0.3f) return new Color(255, 200, 0);
        return new Color(255, 50, 50);
    }
    
    private Color getHappinessColor(float ratio) {
        if (ratio > 0.6f) return new Color(0, 200, 0);
        if (ratio > 0.3f) return new Color(255, 200, 0);
        return new Color(200, 100, 0);
    }
    
    private String formatAge(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        
        if (minutes > 0) {
            return minutes + "m " + remainingSeconds + "s";
        }
        return seconds + "s";
    }
}

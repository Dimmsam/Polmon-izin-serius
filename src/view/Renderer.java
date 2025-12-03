package view;

import model.Monster;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Renderer {
    private AssetLoader assetLoader;
    
    public Renderer(AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
    }
    
    public void renderStats(Graphics2D g, Monster monster, int canvasWidth, int canvasHeight) {
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
        
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);
        
        float hpRatio = (float) monster.getHP() / monster.getMaxHP();
        int fillWidth = (int) (barWidth * hpRatio);
        
        g.setColor(Color.RED);
        g.fillRect(x, y, fillWidth, barHeight);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        String text = "HP: " + monster.getHP() + "/" + monster.getMaxHP();
        g.drawString(text, x + 5, y + 15);
    }
    
    private void renderEnergyBar(Graphics2D g, Monster monster) {
        int barWidth = 150;
        int barHeight = 20;
        int x = 10;
        int y = 35;
        
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);
        
        float energyRatio = (float) monster.getEnergy() / monster.getMaxEnergy();
        int fillWidth = (int) (barWidth * energyRatio);
        
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, fillWidth, barHeight);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        String text = "Energy: " + monster.getEnergy() + "/" + monster.getMaxEnergy();
        g.drawString(text, x + 5, y + 15);
    }
    
    private void renderHappinessBar(Graphics2D g, Monster monster) {
        int barWidth = 150;
        int barHeight = 20;
        int x = 10;
        int y = 60;
        
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);
        
        float happyRatio = (float) monster.getHappiness() / 100;
        int fillWidth = (int) (barWidth * happyRatio);
        
        g.setColor(Color.GREEN);
        g.fillRect(x, y, fillWidth, barHeight);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        String text = "Happy: " + monster.getHappiness() + "/100";
        g.drawString(text, x + 5, y + 15);
    }
    
    private void renderInfo(Graphics2D g, Monster monster) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        
        int x = 10;
        int y = 90;
        
        g.drawString("Name: " + monster.getName(), x, y);
        g.drawString("State: " + monster.getStateName(), x, y + 20);
        g.drawString("Stage: " + monster.getStage(), x, y + 40);
    }
}

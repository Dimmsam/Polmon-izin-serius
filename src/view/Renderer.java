package view;

import model.Monster;
import view.components.StatBar;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Renderer {
    private AssetLoader assetLoader;
    private StatBar hpBar;
    private StatBar energyBar;
    private StatBar happinessBar;

    public Renderer(AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
        this.hpBar = new StatBar("HP", 0, 0, Color.RED);
        this.energyBar = new StatBar("Energy", 0, 0, new Color(255, 200, 0));
        this.happinessBar = new StatBar("Happy", 0, 0, Color.GREEN);
    }

    public void renderStats(Graphics2D g, Monster monster, int canvasWidth, int canvasHeight) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        updateBars(monster);

        hpBar.render(g, 10, 10, 150, 20);
        energyBar.render(g, 10, 35, 150, 20);
        happinessBar.render(g, 10, 60, 150, 20);

        renderInfo(g, monster);
        renderHungerWarning(g, monster);
    }

    private void updateBars(Monster monster) {
        hpBar.setCurrent(monster.getHP());
        hpBar.setMax(monster.getMaxHP());
        hpBar.setFillColor(getHPColor((float) monster.getHP() / monster.getMaxHP()));

        energyBar.setCurrent(monster.getEnergy());
        energyBar.setMax(monster.getMaxEnergy());

        happinessBar.setCurrent(monster.getHappiness());
        happinessBar.setMax(100);
        happinessBar.setFillColor(getHappinessColor((float) monster.getHappiness() / 100));
    }

    private void renderInfo(Graphics2D g, Monster monster) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        int x = 10;
        int y = 95;

        g.drawString("Name: " + monster.getName(), x, y);

        String stateDisplay = monster.getStateName().replace("State", "");
        Color stateColor = getStateColor(stateDisplay);
        g.setColor(stateColor);
        g.drawString("State: " + stateDisplay, x, y + 20);

        g.setColor(Color.BLACK);
        g.drawString("Stage: " + monster.getStage(), x, y + 40);

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Age: " + formatAge(monster.getAgeSeconds()), x, y + 60);
    }

    private void renderHungerWarning(Graphics2D g, Monster monster) {
        if (monster.getHunger() > 70) {
            g.setColor(new Color(255, 100, 100, 200));
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("HUNGRY!", 10, 180);
        }
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

    private Color getStateColor(String state) {
        switch (state.toLowerCase()) {
            case "normal":
                return new Color(0, 150, 0);
            case "hungry":
                return new Color(200, 0, 0);
            case "sleep":
                return new Color(0, 0, 200);
            case "bored":
                return new Color(200, 150, 0);
            case "dead":
                return Color.GRAY;
            default:
                return Color.BLACK;
        }
    }

    private String formatAge(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        if (minutes > 0) {
            return minutes + "m " + remainingSeconds + "s";
        }
        return seconds + "s";
    }
}
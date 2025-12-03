package view;

import model.SpeciesType;
import model.EvolutionStage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class SpriteGenerator {
    
    private static final int SPRITE_WIDTH = 48;
    private static final int SPRITE_HEIGHT = 64;
    
    public static BufferedImage generateSprite(SpeciesType species, EvolutionStage stage, String state) {
        BufferedImage img = new BufferedImage(SPRITE_WIDTH, SPRITE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color baseColor = getSpeciesColor(species);
        Color stateColor = getStateColor(state);
        int size = getStageSize(stage);
        
        int x = (SPRITE_WIDTH - size) / 2;
        int y = (SPRITE_HEIGHT - size) / 2;
        
        g.setColor(baseColor);
        g.fillOval(x, y, size, size);
        
        g.setColor(stateColor);
        g.fillOval(x + size/4, y + size/4, size/2, size/2);
        
        g.setColor(Color.BLACK);
        g.drawOval(x, y, size, size);
        
        drawLabel(g, species, stage, state);
        
        g.dispose();
        return img;
    }
    
    public static BufferedImage generateEgg() {
        BufferedImage img = new BufferedImage(SPRITE_WIDTH, SPRITE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = 36;
        int height = 50;
        int x = (SPRITE_WIDTH - width) / 2;
        int y = (SPRITE_HEIGHT - height) / 2;
        
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
        
        g.setColor(new Color(200, 200, 200));
        for (int i = 0; i < 5; i++) {
            int spotSize = 5 + (i * 2);
            int spotX = x + (i * 7);
            int spotY = y + 10 + (i * 8);
            g.fillOval(spotX, spotY, spotSize, spotSize);
        }
        
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
        
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("EGG", x + 8, y + height + 12);
        
        g.dispose();
        return img;
    }
    
    private static Color getSpeciesColor(SpeciesType species) {
        switch (species) {
            case KUCING:
                return new Color(255, 200, 100);
            case SLIME:
                return new Color(100, 200, 255);
            default:
                return Color.GRAY;
        }
    }
    
    private static Color getStateColor(String state) {
        switch (state.toLowerCase()) {
            case "normal":
                return new Color(100, 255, 100);
            case "hungry":
                return new Color(255, 100, 100);
            case "sleep":
                return new Color(150, 150, 255);
            case "bored":
                return new Color(255, 255, 100);
            case "dead":
                return new Color(100, 100, 100);
            default:
                return Color.WHITE;
        }
    }
    
    private static int getStageSize(EvolutionStage stage) {
        switch (stage) {
            case EGG:
                return 36;
            case BABY:
                return 28;
            case CHILD:
                return 38;
            case ADULT:
                return 45;
            default:
                return 36;
        }
    }
    
    private static void drawLabel(Graphics2D g, SpeciesType species, EvolutionStage stage, String state) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 8));
        
        String label = species.name().substring(0, 1) + 
                       stage.name().substring(0, 1) + 
                       state.substring(0, 1).toUpperCase();
        
        g.drawString(label, SPRITE_WIDTH/2 - 10, SPRITE_HEIGHT - 5);
    }
}

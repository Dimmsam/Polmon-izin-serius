package view;

import core.GameEngine;
import model.Monster;
import model.EvolutionStage;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameCanvas extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    
    private GameEngine engine;
    private AssetLoader assetLoader;
    private Renderer renderer;
    
    public GameCanvas(GameEngine engine) {
        this.engine = engine;
        this.assetLoader = AssetLoader.getInstance();
        this.renderer = new Renderer(assetLoader);
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        Monster monster = engine.getMonster();
        if (monster != null) {
            BufferedImage sprite;
            
            if (monster.getStage() == EvolutionStage.EGG) {
                sprite = assetLoader.getEggSprite();
            } else {
                String stateName = getSimpleStateName(monster.getStateName());
                sprite = assetLoader.getMonsterSprite(
                    monster.getSpecies(), 
                    monster.getStage(), 
                    stateName
                );
            }
            
            if (sprite != null) {
                int x = (getWidth() - sprite.getWidth()) / 2;
                int y = (getHeight() - sprite.getHeight()) / 2;
                g2d.drawImage(sprite, x, y, null);
            }
            
            renderer.renderStats(g2d, monster, getWidth(), getHeight());
        }
    }
    
    private String getSimpleStateName(String fullName) {
        return fullName.replace("State", "").toLowerCase();
    }
}

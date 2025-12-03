package view;

import core.GameEngine;
import model.Monster;
import model.EvolutionStage;
import model.state.SleepState;
import model.state.DeadState;
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
    private double animationTime;
    
    public GameCanvas(GameEngine engine) {
        this.engine = engine;
        this.assetLoader = AssetLoader.getInstance();
        this.renderer = new Renderer(assetLoader);
        this.animationTime = 0;
        
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
                int baseX = (getWidth() - sprite.getWidth()) / 2;
                int baseY = (getHeight() - sprite.getHeight()) / 2;
                
                int offsetY = getAnimationOffset(monster);
                
                g2d.drawImage(sprite, baseX, baseY + offsetY, null);
            }
            
            renderer.renderStats(g2d, monster, getWidth(), getHeight());
        }
        
        animationTime += 0.05;
    }
    
    private int getAnimationOffset(Monster monster) {
        if (monster.getCurrentState() instanceof DeadState) {
            return 0;
        }
        
        if (monster.getCurrentState() instanceof SleepState) {
            return (int) (Math.sin(animationTime * 0.5) * 3);
        }
        
        return (int) (Math.sin(animationTime) * 5);
    }
    
    private String getSimpleStateName(String fullName) {
        return fullName.replace("State", "").toLowerCase();
    }
}

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
import java.awt.Font;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;

public class GameCanvas extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private GameEngine engine;
    private AssetLoader assetLoader;
    private Renderer renderer;
    private double animationTime;
    private EvolutionStage lastStage;
    private int evolutionEffectTimer;

    public GameCanvas(GameEngine engine) {
        this.engine = engine;
        this.assetLoader = AssetLoader.getInstance();
        this.renderer = new Renderer(assetLoader);
        this.animationTime = 0;
        this.lastStage = null;
        this.evolutionEffectTimer = 0;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Monster monster = engine.getMonster();
        if (monster != null) {
            checkEvolution(monster);

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

                if (evolutionEffectTimer > 0) {
                    renderEvolutionEffect(g2d, baseX, baseY, sprite);
                } else {
                    g2d.drawImage(sprite, baseX, baseY + offsetY, null);
                }

                if (monster.getCurrentState() instanceof DeadState) {
                    renderDeathOverlay(g2d, baseX, baseY, sprite);
                }
            }

            renderer.renderStats(g2d, monster, getWidth(), getHeight());
        }

        animationTime += 0.05;
        if (evolutionEffectTimer > 0) {
            evolutionEffectTimer--;
        }
    }

    private void checkEvolution(Monster monster) {
        if (lastStage != null && lastStage != monster.getStage()) {
            evolutionEffectTimer = 30;
        }
        lastStage = monster.getStage();
    }

    private void renderEvolutionEffect(Graphics2D g, int x, int y, BufferedImage sprite) {
        float alpha = (float) Math.abs(Math.sin(evolutionEffectTimer * 0.3));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(sprite, x, y, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g.setColor(new Color(255, 255, 0, 150));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("EVOLUTION!", getWidth()/2 - 60, getHeight()/2);
    }

    private void renderDeathOverlay(Graphics2D g, int x, int y, BufferedImage sprite) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.BLACK);
        g.fillRect(x, y, sprite.getWidth(), sprite.getHeight());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("RIP", x + sprite.getWidth()/2 - 15, y + sprite.getHeight()/2);
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
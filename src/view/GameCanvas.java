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
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class GameCanvas extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final double SPRITE_SCALE = 2.0;

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

        // Enable smooth scaling
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

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
                int scaledWidth = (int) (sprite.getWidth() * SPRITE_SCALE);
                int scaledHeight = (int) (sprite.getHeight() * SPRITE_SCALE);

                int baseX = (getWidth() - scaledWidth) / 2;
                int baseY = (getHeight() - scaledHeight) / 2;

                int offsetY = getAnimationOffset(monster);

                if (evolutionEffectTimer > 0) {
                    renderEvolutionEffect(g2d, baseX, baseY + offsetY, sprite, scaledWidth, scaledHeight);
                } else {
                    g2d.drawImage(sprite, baseX, baseY + offsetY, scaledWidth, scaledHeight, null);
                }

                if (monster.getCurrentState() instanceof DeadState) {
                    renderDeathOverlay(g2d, baseX, baseY + offsetY, scaledWidth, scaledHeight);
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

    private void renderEvolutionEffect(Graphics2D g, int x, int y, BufferedImage sprite, int width, int height) {
        float alpha = (float) Math.abs(Math.sin(evolutionEffectTimer * 0.3));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(sprite, x, y, width, height, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g.setColor(new Color(255, 255, 0, 150));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("EVOLUTION!", getWidth()/2 - 60, getHeight()/2);
    }

    private void renderDeathOverlay(Graphics2D g, int x, int y, int width, int height) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("RIP", x + width/2 - 15, y + height/2);
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
        String stateName = fullName.replace("State", "").toLowerCase();

        if (stateName.equals("normal")) {
            return "happy";
        }

        return stateName;
    }
}
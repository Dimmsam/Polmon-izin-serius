package core;

import model.Monster;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameEngine {
    private static GameEngine instance;
    private static final Object lock = new Object();

    private boolean isRunning;
    private Timer visualTimer;
    private Timer hourlyTimer;
    private Monster monster;
    private JPanel canvas;

    private static final int VISUAL_DELAY = 500;
    private static final int HOURLY_DELAY = 3600000;

    private GameEngine() {
        this.isRunning = false;
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new GameEngine();
                }
            }
        }
        return instance;
    }

    public void initialize(Monster monster, JPanel canvas) {
        this.monster = monster;
        this.canvas = canvas;
    }

    public void start() {
        if (isRunning) return;

        isRunning = true;

        visualTimer = new Timer(VISUAL_DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (canvas != null) {
                    canvas.repaint();
                }
            }
        });

        hourlyTimer = new Timer(HOURLY_DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (monster != null) {
                    monster.starve();
                }
            }
        });

        visualTimer.start();
        hourlyTimer.start();
    }

    public void stop() {
        if (!isRunning) return;

        if (visualTimer != null) {
            visualTimer.stop();
        }

        if (hourlyTimer != null) {
            hourlyTimer.stop();
        }

        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Monster getMonster() {
        return monster;
    }
}
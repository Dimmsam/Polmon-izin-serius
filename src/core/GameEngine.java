package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Monster;
import utils.MonsterWriter;
import utils.event.EventListener;
import utils.event.GameEvent;
import model.EvolutionStage;
import model.state.PolmonState;

public class GameEngine {
    private static GameEngine instance;
    private static final Object lock = new Object();

    private boolean isRunning;
    private Timer visualTimer;
    private Timer hourlyTimer;
    private Timer logicTimer;
    private Monster monster;
    private JPanel canvas;

    private static final int VISUAL_DELAY = 500;
    private static final int HOURLY_DELAY = 3600000;
    private static final String SAVE_FILE = "polmon.xml";

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

        monster.getEvolutionEvents().subscribe(new EventListener<GameEvent<EvolutionStage>>() {
            @Override
            public void onEvent(GameEvent<EvolutionStage> event) {
                System.out.println("[EVENT] Evolution occurred: " + event.getPayload());
            }
        });

        monster.getStateChangeEvents().subscribe(new EventListener<GameEvent<PolmonState>>() {
            @Override
            public void onEvent(GameEvent<PolmonState> event) {
                System.out.println("[EVENT] State changed: " + event.getPayload().getClass().getSimpleName());
            }
        });

        monster.getHealthChangeEvents().subscribe(new EventListener<GameEvent<Integer>>() {
            @Override
            public void onEvent(GameEvent<Integer> event) {
                System.out.println("[EVENT] HP changed to: " + event.getPayload());
            }
        });
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

        logicTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (monster != null) {
                    monster.updateLogic();
                }
            }
        });

        visualTimer.start();
        hourlyTimer.start();
        logicTimer.start();
    }


    public void stop() {
        if (!isRunning) return;

        if (visualTimer != null) {
            visualTimer.stop();
        }

        if (hourlyTimer != null) {
            hourlyTimer.stop();
        }

        if (logicTimer != null) {
            logicTimer.stop();
        }

        isRunning = false;
    }

    public void save() {
        if (monster != null) {
            MonsterWriter.write(monster, new File("./"), SAVE_FILE);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Monster getMonster() {
        return monster;
    }
}
package view;

import core.GameEngine;
import model.Monster;
import model.state.DeadState;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActionHandler {
    private GameEngine engine;
    private GameWindow window;

    public ActionHandler(GameEngine engine, GameWindow window) {
        this.engine = engine;
        this.window = window;
    }

    public ActionListener getFeedAction() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Monster monster = engine.getMonster();
                if (monster != null) {
                    if (monster.getCurrentState() instanceof DeadState) {
                        showDeadMessage();
                        return;
                    }
                    monster.feed();
                }
            }
        };
    }

    public ActionListener getPlayAction() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Monster monster = engine.getMonster();
                if (monster != null) {
                    if (monster.getCurrentState() instanceof DeadState) {
                        showDeadMessage();
                        return;
                    }
                    monster.play();
                }
            }
        };
    }

    public ActionListener getSleepAction() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Monster monster = engine.getMonster();
                if (monster != null) {
                    if (monster.getCurrentState() instanceof DeadState) {
                        showDeadMessage();
                        return;
                    }
                    monster.sleep();
                }
            }
        };
    }

    public ActionListener getWakeUpAction() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Monster monster = engine.getMonster();
                if (monster != null) {
                    if (monster.getCurrentState() instanceof DeadState) {
                        showDeadMessage();
                        return;
                    }
                    monster.wakeUp();
                }
            }
        };
    }

    public ActionListener getStatsAction() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Monster monster = engine.getMonster();
                if (monster != null) {
                    StatsDialog.show(window, monster);
                }
            }
        };
    }

    public ActionListener getSaveAction() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.save();
                JOptionPane.showMessageDialog(window, "Game saved successfully!",
                        "Save", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    public ActionListener getNewGameAction() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(window,
                        "Start a new game? Current progress will be lost!",
                        "New Game", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    window.dispose();
                    core.Main.main(new String[]{});
                }
            }
        };
    }

    private void showDeadMessage() {
        JOptionPane.showMessageDialog(window,
                "Your monster is dead. Start a new game!",
                "RIP", JOptionPane.ERROR_MESSAGE);
    }
}
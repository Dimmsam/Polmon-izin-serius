package view;

import core.GameEngine;
import model.Monster;
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
                JOptionPane.showMessageDialog(window, "Game saved!");
            }
        };
    }
}

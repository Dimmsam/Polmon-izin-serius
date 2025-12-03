package view;

import core.GameEngine;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class GameWindow extends JFrame {
    private GameEngine engine;
    private GameCanvas canvas;
    private ActionHandler actionHandler;
    
    public GameWindow(GameEngine engine) {
        this.engine = engine;
        this.actionHandler = new ActionHandler(engine, this);
        
        setupWindow();
        setupMenu();
        setupCanvas();
        setupButtons();
    }
    
    private void setupWindow() {
        setTitle("Polmon - Polban Monster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(actionHandler.getSaveAction());
        fileMenu.add(saveItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem statsItem = new JMenuItem("Stats");
        statsItem.addActionListener(actionHandler.getStatsAction());
        helpMenu.add(statsItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void setupCanvas() {
        canvas = new GameCanvas(engine);
        add(canvas, BorderLayout.CENTER);
        
        engine.initialize(engine.getMonster(), canvas);
    }
    
    private void setupButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton feedButton = new JButton("Feed");
        feedButton.addActionListener(actionHandler.getFeedAction());
        
        JButton playButton = new JButton("Play");
        playButton.addActionListener(actionHandler.getPlayAction());
        
        JButton sleepButton = new JButton("Sleep");
        sleepButton.addActionListener(actionHandler.getSleepAction());
        
        JButton wakeButton = new JButton("Wake Up");
        wakeButton.addActionListener(actionHandler.getWakeUpAction());
        
        buttonPanel.add(feedButton);
        buttonPanel.add(playButton);
        buttonPanel.add(sleepButton);
        buttonPanel.add(wakeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void display() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

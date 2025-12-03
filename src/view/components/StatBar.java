package view.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class StatBar {
    private String label;
    private int current;
    private int max;
    private Color fillColor;
    private Color backgroundColor;

    public StatBar(String label, int current, int max, Color fillColor) {
        this.label = label;
        this.current = current;
        this.max = max;
        this.fillColor = fillColor;
        this.backgroundColor = new Color(200, 200, 200);
    }

    public void render(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(backgroundColor);
        g.fillRect(x, y, width, height);

        float ratio = (float) current / max;
        int fillWidth = (int) (width * Math.max(0, Math.min(1, ratio)));

        g.setColor(fillColor);
        g.fillRect(x, y, fillWidth, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setFont(new Font("Arial", Font.BOLD, 12));
        String text = label + ": " + current + "/" + max;
        g.drawString(text, x + 5, y + 15);
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }
}
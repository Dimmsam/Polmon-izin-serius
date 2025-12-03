package view.adapter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PlaceHolderAdapter implements ImageAdapter {
    
    private static final int DEFAULT_WIDTH = 48;
    private static final int DEFAULT_HEIGHT = 64;
    
    @Override
    public BufferedImage getImage(String resourcePath) {
        return getImage(resourcePath, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    @Override
    public BufferedImage getImage(String resourcePath, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        
        g.dispose();
        return img;
    }
}

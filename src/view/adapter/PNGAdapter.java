package view.adapter;

import utils.ResourceHelper;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

public class PNGAdapter implements ImageAdapter {
    
    @Override
    public BufferedImage getImage(String resourcePath) {
        return getImage(resourcePath, -1, -1);
    }
    
    @Override
    public BufferedImage getImage(String resourcePath, int width, int height) {
        try {
            URL url = ResourceHelper.getResourceURL(resourcePath);
            if (url == null) {
                return null;
            }
            
            BufferedImage img = ImageIO.read(url);
            
            if (width > 0 && height > 0) {
                return resize(img, width, height);
            }
            
            return img;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private BufferedImage resize(BufferedImage original, int width, int height) {
        Image scaled = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(scaled, 0, 0, null);
        g.dispose();
        return result;
    }
}

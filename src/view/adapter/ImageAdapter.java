package view.adapter;

import java.awt.image.BufferedImage;

public interface ImageAdapter {
    BufferedImage getImage(String resourcePath);
    BufferedImage getImage(String resourcePath, int width, int height);
}
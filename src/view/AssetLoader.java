package view;

import view.adapter.ImageAdapter;
import view.adapter.PNGAdapter;
import view.adapter.PlaceholderAdapter;
import model.SpeciesType;
import model.EvolutionStage;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class AssetLoader {
    private static AssetLoader instance;
    private ImageAdapter pngAdapter;
    private ImageAdapter placeholderAdapter;
    private Map<String, BufferedImage> cache;
    
    private AssetLoader() {
        this.pngAdapter = new PNGAdapter();
        this.placeholderAdapter = new PlaceholderAdapter();
        this.cache = new HashMap<>();
    }
    
    public static AssetLoader getInstance() {
        if (instance == null) {
            instance = new AssetLoader();
        }
        return instance;
    }
    
    public BufferedImage getMonsterSprite(SpeciesType species, EvolutionStage stage, String state) {
        String key = species.name() + "" + stage.name() + "" + state;
        
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        
        String path = "/images/" + species.name().toLowerCase() + "_" + 
                      stage.name().toLowerCase() + "_" + state.toLowerCase() + ".png";
        
        BufferedImage img = pngAdapter.getImage(path, 64, 64);
        
        if (img == null) {
            img = placeholderAdapter.getImage(path, 64, 64);
        }
        
        cache.put(key, img);
        return img;
    }
    
    public BufferedImage getEggSprite() {
        if (cache.containsKey("egg")) {
            return cache.get("egg");
        }
        
        BufferedImage img = pngAdapter.getImage("/images/egg.png", 64, 64);
        
        if (img == null) {
            img = placeholderAdapter.getImage("/images/egg.png", 64, 64);
        }
        
        cache.put("egg", img);
        return img;
    }
}

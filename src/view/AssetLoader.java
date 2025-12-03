package view;

import view.adapter.ImageAdapter;
import view.adapter.PNGAdapter;
import view.adapter.PlaceHolderAdapter;
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
        this.placeholderAdapter = new PlaceHolderAdapter();
        this.cache = new HashMap<>();
    }

    public static AssetLoader getInstance() {
        if (instance == null) {
            instance = new AssetLoader();
        }
        return instance;
    }

    public BufferedImage getMonsterSprite(SpeciesType species, EvolutionStage stage, String state) {
        String key = species.name() + "_" + stage.name() + "_" + state;

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        String path = "/images/" + species.name().toLowerCase() + "_" +
                stage.name().toLowerCase() + "_" + state.toLowerCase() + ".png";

        BufferedImage img = pngAdapter.getImage(path);

        if (img == null) {
            img = SpriteGenerator.generateSprite(species, stage, state);
        }

        cache.put(key, img);
        return img;
    }

    public BufferedImage getEggSprite() {
        if (cache.containsKey("egg")) {
            return cache.get("egg");
        }

        BufferedImage img = pngAdapter.getImage("/images/egg.png");

        if (img == null) {
            img = SpriteGenerator.generateEgg();
        }

        cache.put("egg", img);
        return img;
    }
}

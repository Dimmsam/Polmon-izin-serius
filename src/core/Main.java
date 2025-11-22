package core;

import utils.ResourceHelper;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== POLMON - Resource Helper Test ===\n");

        // Test 1: getResourcePath (untuk development)
        String path1 = ResourceHelper.getResourcePath("images/egg.png");
        System.out.println("✓ Resource path (dev): " + path1);

        // Test 2: getResourceURL (untuk production)
        URL url = ResourceHelper.getResourceURL("/images/egg.png");
        if (url != null) {
            System.out.println("✓ Resource URL found: " + url);
        } else {
            System.out.println("⚠ Resource URL not found (normal, belum ada asset)");
        }

        // Test 3: Check existence
        boolean exists = ResourceHelper.resourceExists("/images/egg.png");
        System.out.println("✓ Resource exists: " + exists);

        System.out.println("\n=== Resource Helper Ready! ===");
    }
}
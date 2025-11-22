package utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * RESOURCE PATH HELPER
 * Utility class untuk load resource dengan path yang benar
 * agar compatible dengan JAR packaging
 *
 * @author Person A (Dimas Rizal Ramadhani)
 */
public class ResourceHelper {

    /**
     * Method untuk mendapatkan path resource yang benar
     * Digunakan untuk loading file dari folder resources/
     *
     * PENGGUNAAN:
     * String path = ResourceHelper.getResourcePath("images/egg.png");
     * BufferedImage img = ImageIO.read(new File(path));
     *
     * @param resourcePath - path relatif dari folder resources/ (tanpa leading slash)
     * @return Full path ke resource
     */
    public static String getResourcePath(String resourcePath) {
        // Untuk development (running dari IDE)
        // Path akan mengarah ke: src/resources/...
        return "src/resources/" + resourcePath;
    }

    /**
     * Method untuk load resource sebagai URL
     * Lebih robust, work untuk development dan JAR packaging
     *
     * PENGGUNAAN:
     * URL url = ResourceHelper.getResourceURL("/images/egg.png");
     * BufferedImage img = ImageIO.read(url);
     *
     * @param resourcePath - path relatif dari resources/ (WITH leading slash)
     * @return URL ke resource, atau null jika tidak ditemukan
     */
    public static URL getResourceURL(String resourcePath) {
        // getResource() mencari dari classpath
        // Saat running dari IDE: src/resources/
        // Saat running dari JAR: resources/ di dalam JAR
        return ResourceHelper.class.getResource(resourcePath);
    }

    /**
     * Method untuk load resource sebagai InputStream
     * Berguna untuk membaca file text/binary
     *
     * PENGGUNAAN:
     * InputStream is = ResourceHelper.getResourceStream("/data/monsters.txt");
     * BufferedReader reader = new BufferedReader(new InputStreamReader(is));
     *
     * @param resourcePath - path relatif dari resources/ (WITH leading slash)
     * @return InputStream ke resource, atau null jika tidak ditemukan
     */
    public static InputStream getResourceStream(String resourcePath) {
        return ResourceHelper.class.getResourceAsStream(resourcePath);
    }

    /**
     * Method untuk check apakah resource exists
     *
     * @param resourcePath - path relatif dari resources/ (WITH leading slash)
     * @return true jika resource ditemukan, false jika tidak
     */
    public static boolean resourceExists(String resourcePath) {
        return getResourceURL(resourcePath) != null;
    }
}
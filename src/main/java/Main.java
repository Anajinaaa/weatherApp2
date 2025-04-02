import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    private static final long CACHE_DURATION = 5 * 60 * 1000;
    private static final String CACHE_FILE = "./cache/cache-%s-%s.json";

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println(System.currentTimeMillis());

        String apiKey = "f36a33e216c196d0dd51e1bf4a8197d3";
        String latitude = "60";
        String longitude = "-50";

        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s",
                latitude, longitude, apiKey);

        Fetcher simpleFetcher = new SimpleWebFetcher();
        Fetcher rateLimitedFetcher = new RateLimitedFetcher(simpleFetcher);
        Fetcher cachedFetcher = new CachedWebFetcher(rateLimitedFetcher);
        String result = cachedFetcher.getUrl(url);
        System.out.println("Result: " + result);

        result = cachedFetcher.getUrl(url);
        System.out.println("Result: " + result);



    }

    private static String getCache(String latitude, String longitude) {
        cleanup();
        try {
            String cacheFileName = getCacheFileName(latitude, longitude);
            File cacheFile = new File(cacheFileName);
            if (cacheFile.exists()) {
                long lastModified = cacheFile.lastModified();
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastModified < CACHE_DURATION) {
                    return Files.readString(Paths.get(cacheFileName));
                }
            }
        } catch (Exception e) {
            System.out.println("Could not get cache: " + e.getMessage());
        }
        return null;
    }

    private static void saveToCache(String body, String latitude, String longitude) {
        System.out.println("Saving to cache...");
        try {
            String cacheFileName = getCacheFileName(latitude, longitude);
            FileWriter writer = new FileWriter(cacheFileName);
            writer.write(body);
            writer.flush();
            System.out.println("Saved to cache!");
        } catch (IOException e) {
            System.out.println("Error writing to cache: " + e.getMessage());
        }
    }

    private static String getCacheFileName(String latitude, String longitude) {
        String cacheFileName = String.format(CACHE_FILE, latitude, longitude);
        return cacheFileName;
    }

    private static void cleanup() {
        int randomNumber = new Random().nextInt(100);
        if (randomNumber < 5) {
            System.out.println("Cleaning house...");
            File cacheDirectory = new File("./cache/");
            long cutoffTime = 2 * 24 * 60 * 60 * 1000;
            File[] files = cacheDirectory.listFiles(
                    file -> file.isFile() &&
                            file.getName().startsWith("cache") &&
                            file.getName().endsWith(".json") &&
                            System.currentTimeMillis() - file.lastModified() >= cutoffTime
            );
            for (File file : files) {
                System.out.println("Deleting " + file.getName());
                file.delete();
            }
        }
    }


}
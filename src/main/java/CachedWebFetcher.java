import java.util.HashMap;

public class CachedWebFetcher implements Fetcher {

    private static final long CACHE_DURATION = 5 * 60 * 1000;
    private Fetcher fetcher;
    private HashMap<String, CacheObject<String>> cache;

    public CachedWebFetcher(Fetcher delegate) {
        this.fetcher = delegate;
        this.cache = new HashMap<>();
    }

    public String getUrl(String url) {
        CacheObject<String> cachedObject = cache.get(url);

        if (cachedObject != null && cachedObject.isValid(CACHE_DURATION)) {
            System.out.println("Cache hit!");
            return cachedObject.getValue();
        }

        System.out.println("Cache miss!");
        String response = fetcher.getUrl(url);
        if (response != null) {
            CacheObject<String> obj = new CacheObject<>(response);
            cache.put(url, obj);
            return response;
        }

        return null;
    }
}
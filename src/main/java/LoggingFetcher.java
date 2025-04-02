import java.util.HashMap;
import java.util.Map;

class LoggingFetcher {
    private final Fetcher fetcher;
    private final Map<String, Integer> requestCount;

    public LoggingFetcher(Fetcher fetcher) {
        this.fetcher = fetcher;
        this.requestCount = new HashMap<>();
    }

    public String getUrl(String url) {
        requestCount.put(url, requestCount.getOrDefault(url, 0) + 1);
        System.out.println("Fetching " + url + " (Request #" + requestCount.get(url) + ")");
        return fetcher.getUrl(url);
    }

    public int getRequestCount(String url) {
        return requestCount.getOrDefault(url, 0);
    }
}

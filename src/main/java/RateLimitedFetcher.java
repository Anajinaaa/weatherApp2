public class RateLimitedFetcher implements Fetcher {

    private static long throttle_duration = 1000;
    private long lastCalled = 0;
    private Fetcher fetcher;

    public RateLimitedFetcher(Fetcher delegate) {
        this.fetcher = delegate;
    }

    public String getUrl(String url) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCalled > throttle_duration) {
            return fetcher.getUrl(url);
        }
        return null;
    }
}
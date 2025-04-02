public class CacheObject<T> {
    private T value;
    private long timestamp;

    public CacheObject(T value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public CacheObject(T value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public T getValue() { return this.value; }
    public long getTimestamp() { return this.timestamp; }

    public boolean isValid(long cache_valid_duration) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - this.timestamp;
        return elapsedTime <= cache_valid_duration;
    }

    public boolean isExpired(long cache_valid_duration) {
        return !isValid(cache_valid_duration);
    }

}
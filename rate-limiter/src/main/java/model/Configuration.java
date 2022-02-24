package model;

import java.util.concurrent.TimeUnit;

public class Configuration {
    private final Integer maxAllowedRequests;
    private final Integer duration;
    private final TimeUnit timeUnit;

    public Configuration(Integer maxAllowedRequests, Integer duration, TimeUnit timeUnit) {
        this.maxAllowedRequests = maxAllowedRequests;
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    public Integer getMaxAllowedRequests() {
        return maxAllowedRequests;
    }

    public Integer getDuration() {
        return duration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}

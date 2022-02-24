package model;

public class FixedWindow {
    private long windowStart;
    private long availableRequests;
    private long availableCredits;

    public FixedWindow(long windowStart, long availableRequests) {
        this.windowStart = windowStart;
        this.availableRequests = availableRequests;
        this.availableCredits = 0;
    }

    public long getWindowStart() {
        return windowStart;
    }

    public long getAvailableRequests() {
        return availableRequests;
    }

    public void setWindowStart(long windowStart) {
        this.windowStart = windowStart;
    }

    public long getAvailableCredits() {
        return availableCredits;
    }

    public void resetWindow(long availableRequests, long windowStart) {
        this.availableCredits += this.availableRequests;
        this.availableRequests = availableRequests;
        this.windowStart = windowStart;
    }

    public void setAvailableRequests(long availableRequests) {
        this.availableRequests = availableRequests;
    }

    public void consumeCredit() {
        this.availableCredits -= 1;
    }
}

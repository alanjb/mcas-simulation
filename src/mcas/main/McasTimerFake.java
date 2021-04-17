package mcas.main;

public class McasTimerFake extends McasTimer {
    private final long interval;

    public McasTimerFake(long timeIntervalMs) {
        super(timeIntervalMs);

        //local copy of timer interval in ms
        this.interval = timeIntervalMs;
    }

    public long getTimeIntervalMs(){
        return this.interval;
    }
}

package mcas.main;

public class McasTimer
{
    // Fake activation interval in ms
    public static final long FAKE_INTERVAL = 100; // ms

    // Declare member variables
    private long lastSetTimeMs;
    private long timeIntervalMs;

    // Constructor
    public McasTimer(long timeIntervalMs)
    {
        // Initialize the lastSetTimeMs to an arbitrarily long
        // time ago. This causes the timer to start out in the
        // expired state (that is, an initial call to
        // isExpired() will return TRUE.
        lastSetTimeMs = 0;
        this.timeIntervalMs = FAKE_INTERVAL; // Modified. Original code: timeIntervalMs;
    }

    public void set()
    {
        // Set the timer to the current system time.
        lastSetTimeMs = System.currentTimeMillis();
    }

    public boolean isExpired()
    {
        // If the current system time is >= the last set time
        // plus the interval then return TRUE, otherwise
        // return FALSE. Note that an initial call to
        // isExpired() without first calling set() will
        // return TRUE.
        return System.currentTimeMillis() >= (lastSetTimeMs + FAKE_INTERVAL);
    }
}
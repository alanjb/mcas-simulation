package mcas.main;

import java.util.TimerTask;
import java.util.Timer;

public class TestTimer {
    Timer timer;
    TimerTask task;

    public TestTimer(Mcas m) {
        timer = new java.util.Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                m.trim(false, false, 15);
            }
        };
    }

    public void start(){
        timer.schedule(task, 5);
    }
}
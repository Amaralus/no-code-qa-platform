package apps.amaralus.qa.platform.runtime.execution;

import java.time.LocalTime;

public class Timer {

    private long startTime;
    private long elapsedTime;

    public synchronized void start() {
        startTime = System.nanoTime();
        elapsedTime = 0;
    }

    public synchronized void stop() {
        elapsedTime = startTime == 0 ? startTime : System.nanoTime() - startTime;
        startTime = 0;
    }

    public synchronized long getElapsedNanoTime() {
        return elapsedTime;
    }

    public synchronized LocalTime getElapsedAsLocalTime() {
        return LocalTime.ofNanoOfDay(getElapsedNanoTime());
    }
}

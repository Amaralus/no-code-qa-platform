package apps.amaralus.qa.platform.runtime.report;

import java.time.LocalTime;

public class Timer {

    private long startTime;
    private long elapsedTime;

    public synchronized void start() {
        startTime = System.nanoTime();
        elapsedTime = 0;
    }

    public synchronized void stop() {
        elapsedTime = System.nanoTime() - startTime;
        startTime = 0;
    }

    public synchronized long getElapsedNanoTime() {
        return elapsedTime == 0 ? System.nanoTime() - startTime : elapsedTime;
    }

    public synchronized LocalTime getElapsedAsLocalTime() {
        return LocalTime.ofNanoOfDay(getElapsedNanoTime());
    }
}

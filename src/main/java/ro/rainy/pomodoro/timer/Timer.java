package ro.rainy.pomodoro.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 10/11/2020__02:18
 */
public abstract class Timer {
    public static final int DURATION_INFINITY = -1;
    private final long interval;
    private final long duration;
    private long elapsedTime;
    private final ScheduledExecutorService execService = Executors.newSingleThreadScheduledExecutor();
    private Future<?> future = null;
    private volatile boolean isRunning = false;

    public Timer() {
        this(1000L, -1);
    }

    public Timer(long interval, long duration) {
        this.interval = interval;
        this.duration = duration;
        this.elapsedTime = 0;
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            future = execService.scheduleWithFixedDelay(() -> {
                onTick();
                elapsedTime += Timer.this.interval;
                if (duration > 0) {
                    if (elapsedTime >= duration) {
                        future.cancel(false);
                    }
                }
            }, 0, this.interval, TimeUnit.MILLISECONDS);
        }
    }

    public void pause() {
        if (isRunning) {
            future.cancel(false);
            isRunning = false;
        }
    }

    public void cancel() {
        pause();
        this.elapsedTime = 0;
    }

    protected abstract void onTick();
}
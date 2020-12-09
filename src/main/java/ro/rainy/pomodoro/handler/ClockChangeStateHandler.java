package ro.rainy.pomodoro.handler;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 10/11/2020__20:21
 */
@FunctionalInterface
public interface ClockChangeStateHandler {
    void nextState(String timeElapsed);
}

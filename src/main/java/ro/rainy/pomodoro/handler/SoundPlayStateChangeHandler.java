package ro.rainy.pomodoro.handler;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 07/01/2021__11:31
 */
@FunctionalInterface
public interface SoundPlayStateChangeHandler {
    void changeState(boolean state);
}

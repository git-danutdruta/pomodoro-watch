package ro.rainy.pomodoro;

import ro.rainy.pomodoro.handler.ApplicationStartHandler;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 08/12/2020__23:05
 */
public interface ApplicationStarter {
    void whenApplicationStart(ApplicationStartHandler startHandler);

    void start();
}

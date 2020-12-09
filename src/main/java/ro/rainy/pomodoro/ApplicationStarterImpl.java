package ro.rainy.pomodoro;

import ro.rainy.pomodoro.event.EventDispatcher;
import ro.rainy.pomodoro.handler.ApplicationStartHandler;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 08/12/2020__23:05
 */
public class ApplicationStarterImpl implements ApplicationStarter {
    private final EventDispatcher<ApplicationStartHandler> startHandlerEventDispatcher;

    public ApplicationStarterImpl() {
        startHandlerEventDispatcher = new EventDispatcher<>("applicationStarted");
    }

    @Override
    public void whenApplicationStart(ApplicationStartHandler startHandler) {
        startHandlerEventDispatcher.addListener(startHandler);
    }

    @Override
    public void start() {
        startHandlerEventDispatcher.dispatch();
    }
}

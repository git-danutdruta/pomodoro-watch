package ro.rainy.pomodoro.event;

public class EventDispatcherException extends RuntimeException {
    public EventDispatcherException(Throwable throwable) {
        super(throwable);
    }
}
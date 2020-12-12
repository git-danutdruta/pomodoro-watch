package ro.rainy.pomodoro.event;

import org.apache.commons.beanutils.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EventDispatcher<T> {
    private List<T> listeners;
    private String methodName;

    public EventDispatcher(String methodName) {
        this.listeners = new ArrayList<>();
        this.methodName = methodName;
    }

    public void addListener(T listener) {
        listeners.add(listener);
    }

    public void removeListener(T Listener) {
        listeners.remove(listeners);
    }

    public void dispatch(Object... args) {
        for (T listener : listeners) {
            try {
                MethodUtils.invokeMethod(listener, methodName, args);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new EventDispatcherException(e);
            }
        }
    }
}
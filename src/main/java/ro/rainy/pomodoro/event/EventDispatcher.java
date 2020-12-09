package ro.rainy.pomodoro.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
                Method method = listener.getClass().getMethod(methodName, argsToClasses(args));
                method.setAccessible(true);
                method.invoke(listener, args);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new EventDispatcherException(e);
            }
        }
    }

    private Class<?>[] argsToClasses(Object[] args) {
        Class<?>[] classes = new Class[args.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = args[i].getClass();
        }
        return classes;
    }
}
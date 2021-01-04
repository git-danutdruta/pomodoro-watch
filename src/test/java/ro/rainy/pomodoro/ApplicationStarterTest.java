package ro.rainy.pomodoro;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import ro.rainy.pomodoro.handler.ApplicationStartHandler;

public class ApplicationStarterTest extends ContextHolder {
    private ApplicationStarter target;
    private ApplicationStartHandler applicationStartedHandler1;
    private ApplicationStartHandler applicationStartedHandler2;

    @Before
    public void setUp() {
        target = new ApplicationStarterImpl();

        applicationStartedHandler1 = context.mock(ApplicationStartHandler.class, "applicationStartedHandler1");
        applicationStartedHandler2 = context.mock(ApplicationStartHandler.class, "applicationStartedHandler2");
    }
}
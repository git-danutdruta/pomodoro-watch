package ro.rainy.pomodoro.dispatcher;

import org.jmock.Expectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.rainy.pomodoro.ContextHolder;
import ro.rainy.pomodoro.event.EventDispatcher;
import ro.rainy.pomodoro.event.EventDispatcherException;

import java.lang.reflect.InvocationTargetException;

/**
 * @proiect: pomodoro-watch
 * @autor: daniel
 * @data: 27/12/2020__23:21
 */
public class EventDispatcherTest extends ContextHolder {

    private TestListener testListener;

    @Before
    public void setUp() {
        testListener = context.mock(TestListener.class, "testListener");
    }

    @Test
    public void dispatchMethodTriggerListenerBehaviorTest() {
        EventDispatcher<TestListener> updateListenerEventDispatcher = new EventDispatcher<>("test");
        updateListenerEventDispatcher.addListener(testListener);

        context.checking(new Expectations() {{
            oneOf(testListener).test();
        }});

        updateListenerEventDispatcher.dispatch();
    }

    @Test
    public void removeListenerTest() {
        EventDispatcher<TestListener> updateListenerEventDispatcher = new EventDispatcher<>("test");
        updateListenerEventDispatcher.addListener(testListener);
        context.checking(new Expectations() {{
            oneOf(testListener).test();
        }});
        updateListenerEventDispatcher.dispatch();

        updateListenerEventDispatcher.removeListener(testListener);

        updateListenerEventDispatcher.dispatch();
    }

    @Test
    public void checkIfInvocationTargetExceptionIsThrownTest() {
        EventDispatcher<TestListener> anotherUpdateListenerEventDispatcher = new EventDispatcher<>("test");
        anotherUpdateListenerEventDispatcher.addListener(testListener);

        context.checking(new Expectations() {{
            oneOf(testListener).test();
            will(throwException(new InvocationTargetException(null)));
        }});

        try {
            anotherUpdateListenerEventDispatcher.dispatch();
            Assert.fail("Should have thrown an exception");
        } catch (EventDispatcherException ex) {
            Assert.assertSame("wrong exception class", InvocationTargetException.class, ex.getCause().getClass());
        }

    }

    interface TestListener {
        void test();
    }
}

package ro.rainy.pomodoro;

import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @proiect: pomodoro-watch
 * @autor: daniel
 * @data: 27/12/2020__23:32
 */
@RunWith(JUnit4.class)
public class ContextHolder {
    protected JUnit4Mockery context = new JUnit4Mockery();
}
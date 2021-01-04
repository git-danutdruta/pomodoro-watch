package ro.rainy.pomodoro.controller;

import org.junit.Before;
import org.junit.Test;
import ro.rainy.pomodoro.ContextHolder;
import ro.rainy.pomodoro.model.PomodoroModel;
import ro.rainy.pomodoro.view.PomodoroView;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 03/01/2021__18:37
 */
public class PomodoroControllerTest extends ContextHolder {
    private PomodoroView view;
    private PomodoroModel model;

    @Before
    public void setUp() {
        view = context.mock(PomodoroView.class);
        model = context.mock(PomodoroModel.class);



//        new PomodoroController(view, model);
    }

    @Test
    public void test() {
    }
}
package ro.rainy.pomodoro.controller;

import ro.rainy.pomodoro.model.PomodoroModel;
import ro.rainy.pomodoro.view.PomodoroView;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 10/11/2020__01:38
 */
public class PomodoroController {
    private PomodoroView view;
    private PomodoroModel model;

    public PomodoroController(PomodoroView view, PomodoroModel model) {
        this.view = view;
        this.model = model;
        setup();
    }

    private void setup() {
        model.whenVisibilityChange(() -> view.setVisible(model.isFrameVisible()));
        model.whenCyclesIncrease(() -> view.setNumberOfCycle(model.getCycles()));
        model.whenTimeTypeSwitch(() -> view.setColorOfTimeLabel(model.isRelax()));
        model.registerClockChangeStateHandler(timeElapsed -> view.setNewCounterValue(timeElapsed));

        view.whenPlayButtonClick(() -> model.whenClockStart());
        view.whenPauseButtonClick(() -> model.whenClockPause());
        view.whenResetButtonClick(() -> model.whenClockReset());
        view.setIconImage(model.getLogo());

    }
}

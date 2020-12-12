package ro.rainy.pomodoro.controller;

import ro.rainy.pomodoro.model.PomodoroModel;
import ro.rainy.pomodoro.view.PomodoroView;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 10/11/2020__01:38
 */
public class PomodoroController {
    private final PomodoroView view;
    private final PomodoroModel model;

    public PomodoroController(PomodoroView view, PomodoroModel model) {
        this.view = view;
        this.model = model;
        setup();
    }

    private void setup() {
        model.whenExceptionThrown(view::showException);

        model.whenVisibilityChange(() -> view.setVisible(model.isFrameVisible()));
        model.whenCyclesIncrease(() -> view.setNumberOfCycle(model.getCycles()));
        model.whenTimeTypeSwitch(() -> view.setColorOfTimeLabel(model.isRelax()));
        model.registerClockChangeStateHandler(view::setNewCounterValue);
        model.whenSettingsDialogVisibilityChange(() -> view.setSettingsDialogVisible(model.isSettingsDialogVisible()));

        model.whenSettingWorkTimeChange(() -> view.setWorkTime(model.getWorkTime()));
        model.whenSettingPauseTimeChange(() -> view.setPauseTime(model.getPauseTime()));
        model.whenSettingBigPauseTimeChange(() -> view.setBigPauseTime(model.getBigPauseTime()));
        model.whenSettingCyclesTimeChange(() -> view.setCyclesOfTime(model.getCyclesOfTime()));

        view.whenSettingButtonClick(() -> model.setSettingsDialogVisible(true));
        view.whenSettingsDialogClose(model::whenSettingDialogClose);
        view.whenPlayButtonClick(model::whenClockStart);
        view.whenPauseButtonClick(model::whenClockPause);
        view.whenResetButtonClick(model::whenClockReset);
        view.whenSaveSettingsButtonClick(model::whenConfigUpdate);

        view.whenWorkSliderChange(() -> view.setWorkTime(model.getWorkSliderRangeModel().getValue()));
        view.whenPauseSliderChange(() -> view.setPauseTime(model.getPauseSliderRangeModel().getValue()));
        view.whenBigPauseSliderChange(() -> view.setBigPauseTime(model.getBigPauseSliderRangeModel().getValue()));
        view.whenCyclesSliderChange(() -> view.setCyclesOfTime(model.getCyclesSliderRangeModel().getValue()));

        view.setWorkSliderRangeModel(model.getWorkSliderRangeModel());
        view.setPauseSliderRangeModel(model.getPauseSliderRangeModel());
        view.setBigPauseSliderRangeModel(model.getBigPauseSliderRangeModel());
        view.setCyclesSliderRangeModel(model.getCyclesSliderRangeModel());

        view.setIconImage(model.getLogo());

    }
}

package ro.rainy.pomodoro.model;

import ro.rainy.pomodoro.handler.*;

import java.awt.image.BufferedImage;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/11/2020__23:45
 */
public interface PomodoroModel {
    void whenExceptionThrown(ExceptionThrownHandler exceptionThrownHandler);

    void loadConfigs();

    void whenVisibilityChange(VisibilityChangeHandler visibilityChangeHandler);

    void setVisible(boolean visible);

    boolean isFrameVisible();

    void whenSettingsDialogVisibilityChange(VisibilityChangeHandler visibilityChangeHandler);

    void setSettingsDialogVisible(boolean visible);

    boolean isSettingsDialogVisible();

    void whenSoundPlayChange(SoundPlayStateChangeHandler soundPlayStateChangeHandler);

    void setAlternateSoundPlay();

    boolean isSoundPlaying();

    void registerClockChangeStateHandler(ClockChangeStateHandler handler);

    void whenTimeTypeSwitch(TimeTypeSwitchHandler timeTypeSwitchHandler);

    void switchTimeType();

    boolean isRelax();

    void whenCyclesIncrease(CycleIncreaseHandler handler);

    int getCycles();

    void whenSettingWorkTimeChange(SettingTimeChangeHandler timeChangeHandler);

    void whenSettingPauseTimeChange(SettingTimeChangeHandler timeChangeHandler);

    void whenSettingBigPauseTimeChange(SettingTimeChangeHandler timeChangeHandler);

    void whenSettingCyclesTimeChange(SettingTimeChangeHandler timeChangeHandler);

    void whenSoundSelectionChange(UpdateSelectionHandler soundSelectionUpdateHandler);

    int getWorkTime();

    int getPauseTime();

    int getBigPauseTime();

    int getCyclesOfTime();

    void whenClockStart();

    void whenClockPause();

    void whenClockReset();

    void whenConfigUpdate();

    void whenSettingDialogClose();

    void updateSoundPathFileSelection();

    BufferedImage getLogo();

    void populateSettingsDialog();

    SliderRangeModel getWorkSliderRangeModel();

    SliderRangeModel getPauseSliderRangeModel();

    SliderRangeModel getBigPauseSliderRangeModel();

    SliderRangeModel getCyclesSliderRangeModel();

    PomodoroFileChooserModel getFileChooserModel();
}
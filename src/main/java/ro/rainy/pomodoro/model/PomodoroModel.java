package ro.rainy.pomodoro.model;

import ro.rainy.pomodoro.handler.ClockChangeStateHandler;
import ro.rainy.pomodoro.handler.CycleIncreaseHandler;
import ro.rainy.pomodoro.handler.TimeTypeSwitchHandler;
import ro.rainy.pomodoro.handler.VisibilityChangeHandler;

import java.awt.image.BufferedImage;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/11/2020__23:45
 */
public interface PomodoroModel {
    void loadConfigs();

    void whenVisibilityChange(VisibilityChangeHandler visibilityChangeHandler);

    void setVisible(boolean visible);

    boolean isFrameVisible();

    void registerClockChangeStateHandler(ClockChangeStateHandler handler);

    void whenTimeTypeSwitch(TimeTypeSwitchHandler timeTypeSwitchHandler);

    void switchTimeType();

    boolean isRelax();

    void whenCyclesIncrease(CycleIncreaseHandler handler);

    int getCycles();

    void whenClockStart();

    void whenClockPause();

    void whenClockReset();

    BufferedImage getLogo();
}
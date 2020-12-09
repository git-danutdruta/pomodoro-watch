package ro.rainy.pomodoro.view;

import ro.rainy.pomodoro.handler.ButtonClickHandler;

import java.awt.*;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/11/2020__23:44
 */
public interface PomodoroView {
    void setVisible(boolean visible);

    void setIconImage(Image image);

    void setNewCounterValue(String value);

    void whenPlayButtonClick(ButtonClickHandler handler);

    void whenPauseButtonClick(ButtonClickHandler handler);

    void whenResetButtonClick(ButtonClickHandler handler);

    void setColorOfTimeLabel(boolean isRelax);

    void setNumberOfCycle(int numberOfCycle);
}
package ro.rainy.pomodoro.view;

import ro.rainy.pomodoro.handler.ButtonClickHandler;
import ro.rainy.pomodoro.handler.CloseDialogHandler;
import ro.rainy.pomodoro.handler.FileSelectionChangeHandler;
import ro.rainy.pomodoro.handler.SliderChangeHandler;
import ro.rainy.pomodoro.model.PomodoroFileChooserModel;
import ro.rainy.pomodoro.model.SliderRangeModel;

import java.awt.*;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/11/2020__23:44
 */
public interface PomodoroView {
    void showException(Throwable throwable);

    void setVisible(boolean visible);

    void setSettingsDialogVisible(boolean visible);

    void setWorkSliderRangeModel(SliderRangeModel sliderModel);

    void setPauseSliderRangeModel(SliderRangeModel sliderModel);

    void setBigPauseSliderRangeModel(SliderRangeModel sliderModel);

    void setCyclesSliderRangeModel(SliderRangeModel sliderModel);

    void setSoundFileChooserModel(PomodoroFileChooserModel fileChooserModel);

    void setSoundFileChooserVisible();

    void setIconImage(Image image);

    void setSoundImageOnOff(boolean mark);

    void setNewCounterValue(String value);

    void whenSettingButtonClick(ButtonClickHandler handler);

    void whenPlayButtonClick(ButtonClickHandler handler);

    void whenPauseButtonClick(ButtonClickHandler handler);

    void whenResetButtonClick(ButtonClickHandler handler);

    void whenSoundButtonClick(ButtonClickHandler handler);

    void whenSoundChooserButtonClick(ButtonClickHandler handler);

    void whenWorkSliderChange(SliderChangeHandler handler);

    void whenPauseSliderChange(SliderChangeHandler handler);

    void whenBigPauseSliderChange(SliderChangeHandler handler);

    void whenCyclesSliderChange(SliderChangeHandler handler);

    void whenSoundFileChooserSelectionChange(FileSelectionChangeHandler selectionChangeHandler);

    void whenSaveSettingsButtonClick(ButtonClickHandler handler);

    void whenSettingsDialogClose(CloseDialogHandler handler);

    void setColorOfTimeLabel(boolean isRelax);

    void setNumberOfCycle(int numberOfCycle);

    void setWorkTime(int workTime);

    void setPauseTime(int pauseTime);

    void setBigPauseTime(int bigPauseTime);

    void setCyclesOfTime(int cycles);


}
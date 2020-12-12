package ro.rainy.pomodoro;

import ro.rainy.pomodoro.model.PomodoroModel;

/**
 * This class should be ending of setUp & fill of all the structures in the app.
 *
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 08/12/2020__23:06
 */
public class PomodoroCoordonator {


    public PomodoroCoordonator(ApplicationStarter applicationStarter, PomodoroModel pomodoroModel) {

        applicationStarter.whenApplicationStart(() -> {
            pomodoroModel.loadConfigs();
            pomodoroModel.populateSettingsDialog();
            pomodoroModel.switchTimeType();
            pomodoroModel.setVisible(true);
        });
    }
}

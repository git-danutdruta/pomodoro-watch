package ro.rainy.pomodoro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.rainy.pomodoro.model.PomodoroModel;

/**
 * This class should be ending of setUp & fill of all the structures in the app.
 *
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 08/12/2020__23:06
 */
public class PomodoroCoordonator {
    private static final Logger LOG = LoggerFactory.getLogger(PomodoroCoordonator.class);

    public PomodoroCoordonator(ApplicationStarter applicationStarter, PomodoroModel pomodoroModel) {

        applicationStarter.whenApplicationStart(() -> {
            LOG.info("Starting POMODORO app");
            pomodoroModel.loadConfigs();
            pomodoroModel.populateSettingsDialog();
            pomodoroModel.switchTimeType();
            pomodoroModel.setVisible(true);
        });
    }
}

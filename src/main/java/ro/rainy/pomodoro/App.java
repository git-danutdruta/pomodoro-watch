package ro.rainy.pomodoro;

import ro.rainy.pomodoro.model.PomodoroModel;
import ro.rainy.pomodoro.model.impl.PomodoroModelImpl;
import ro.rainy.pomodoro.controller.PomodoroController;
import ro.rainy.pomodoro.view.PomodoroView;
import ro.rainy.pomodoro.view.impl.PomodoroViewImpl;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationStarter app = new ApplicationStarterImpl();

            PomodoroView view = new PomodoroViewImpl();
            PomodoroModel model = new PomodoroModelImpl();
            PomodoroController presenter = new PomodoroController(view, model);
            PomodoroCoordonator pomodoroCoordonator = new PomodoroCoordonator(app, model);

            app.start();
        });
    }
}

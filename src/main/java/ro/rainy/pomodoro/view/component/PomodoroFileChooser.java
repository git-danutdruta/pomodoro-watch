package ro.rainy.pomodoro.view.component;

import ro.rainy.pomodoro.model.PomodoroFileChooserModel;
import ro.rainy.pomodoro.model.impl.PomodoroFileChooserModelImpl;

import javax.swing.*;
import java.io.File;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 17/12/2020__21:36
 */
public class PomodoroFileChooser extends JFileChooser {
    private PomodoroFileChooserModel fileChooserModel;

    public PomodoroFileChooser() {
        super();
        fileChooserModel = new PomodoroFileChooserModelImpl();
    }

    @Override
    public void setSelectedFile(File file) {
        super.setSelectedFile(file);
        fileChooserModel.setSelectedFile(file);
    }

    public void setFileChooserModel(PomodoroFileChooserModel fileChooserModel) {
        this.fileChooserModel = fileChooserModel;
    }
}
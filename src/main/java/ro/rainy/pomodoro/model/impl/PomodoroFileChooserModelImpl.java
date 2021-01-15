package ro.rainy.pomodoro.model.impl;

import ro.rainy.pomodoro.model.PomodoroFileChooserModel;

import java.io.File;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 17/12/2020__21:37
 */
public class PomodoroFileChooserModelImpl implements PomodoroFileChooserModel {
    private File file;

    @Override
    public File getSelectedFile() {
        return file;
    }

    @Override
    public void setSelectedFile(File file) {
        this.file = file;
    }
}
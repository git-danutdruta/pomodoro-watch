package ro.rainy.pomodoro.model;

import java.io.File;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 17/12/2020__21:37
 */
public interface PomodoroFileChooserModel {
    void setSelectedFile(File file);

    File getSelectedFile();
}
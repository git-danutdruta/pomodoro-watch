package ro.rainy.pomodoro.view.impl;

import ro.rainy.pomodoro.handler.ButtonClickHandler;
import ro.rainy.pomodoro.util.Constants;
import ro.rainy.pomodoro.util.GuiUtil;
import ro.rainy.pomodoro.view.AbstractFrame;
import ro.rainy.pomodoro.view.PomodoroView;

import javax.swing.*;
import java.awt.*;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/11/2020__23:45
 */
public class PomodoroViewImpl extends AbstractFrame implements PomodoroView {
    private JPanel contentPanel;
    private JButton settingBtn;
    private JButton playBtn;
    private JButton pauseBtn;
    private JButton resetBtn;
    private JLabel counterLbl;
    private JLabel cyclesLbl;

    private void initContent() {
        contentPanel = new JPanel();
        settingBtn = new JButton(new ImageIcon("static/settings-button.png"));
        playBtn = new JButton(new ImageIcon("static/play-button.png"));
        pauseBtn = new JButton(new ImageIcon("static/pause-button.png"));
        resetBtn = new JButton(new ImageIcon("static/stop-button.png"));
        counterLbl = new JLabel("00:00");
        cyclesLbl = new JLabel("0");
    }

    @Override
    public void init() {
        initContent();
    }


    private void setUpContent() {
        Dimension d20 = new Dimension(20, 20);
        settingBtn.setPreferredSize(d20);
        playBtn.setPreferredSize(d20);
        pauseBtn.setPreferredSize(d20);
        resetBtn.setPreferredSize(d20);

        counterLbl.setOpaque(true);
    }

    private void _setUp() {
        this.setTitle("POMODORO");
        this.setAlwaysOnTop(true);
        this.setContentPane(contentPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        GuiUtil.setPositionBottomRight(this);
    }

    @Override
    public void setup() {
        setUpContent();
        _setUp();
    }

    @Override
    public void build() {
//        contentPanel.add(settingBtn);
        contentPanel.add(counterLbl);
        contentPanel.add(playBtn);
        contentPanel.add(pauseBtn);
        contentPanel.add(resetBtn);
        contentPanel.add(cyclesLbl);

        pack();
    }

    @Override
    public void setNewCounterValue(String value) {
        counterLbl.setText(value);
    }

    @Override
    public void setColorOfTimeLabel(boolean isRelax) {
        counterLbl.setBackground(isRelax ? Constants.OFF_COLOR : Constants.ON_COLOR);
    }

    @Override
    public void setNumberOfCycle(int numberOfCycle) {
        cyclesLbl.setText(String.valueOf(numberOfCycle));
    }

    @Override
    public void whenPlayButtonClick(ButtonClickHandler handler) {
        playBtn.addActionListener(clickListener -> handler.click());
    }

    @Override
    public void whenPauseButtonClick(ButtonClickHandler handler) {
        pauseBtn.addActionListener(clickListener -> handler.click());
    }

    @Override
    public void whenResetButtonClick(ButtonClickHandler handler) {
        resetBtn.addActionListener(clickListener -> handler.click());
    }
}
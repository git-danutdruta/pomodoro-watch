package ro.rainy.pomodoro.view.impl;

import net.miginfocom.swing.MigLayout;
import ro.rainy.pomodoro.handler.ButtonClickHandler;
import ro.rainy.pomodoro.handler.CloseDialogHandler;
import ro.rainy.pomodoro.handler.FileSelectionChangeHandler;
import ro.rainy.pomodoro.handler.SliderChangeHandler;
import ro.rainy.pomodoro.model.PomodoroFileChooserModel;
import ro.rainy.pomodoro.model.SliderRangeModel;
import ro.rainy.pomodoro.util.Constants;
import ro.rainy.pomodoro.util.GuiUtil;
import ro.rainy.pomodoro.view.AbstractFrame;
import ro.rainy.pomodoro.view.PomodoroView;
import ro.rainy.pomodoro.view.component.PomodoroFileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
    private JButton soundBtn;
    private JLabel counterLbl;
    private JLabel cyclesLbl;

    private JDialog settingDialog;
    private JPanel settingPanel;
    private JLabel settingWorkTimeLbl;
    private JLabel settingPauseTimeLbl;
    private JLabel settingBigPauseTimeLbl;
    private JLabel settingCyclesOfTimeLbl;
    private JLabel settingSoundPathLbl;
    private JSlider settingWorkTimeSlider;
    private JSlider settingPauseTimeSlider;
    private JSlider settingBigPauseTimeSlider;
    private JSlider settingCyclesOfTimeSlider;
    private JTextField settingWorkTimeTxt;
    private JTextField settingPauseTimeTxt;
    private JTextField settingBigPauseTimeTxt;
    private JTextField settingCyclesOfTimeTxt;
    private JTextField settingSoundFilePathTxt;
    private PomodoroFileChooser settingsSoundFileChooser;
    private JButton openSoundFileBtn;
    private JButton saveSettingBtn;

    private void initSettingDialog() {
        settingDialog = new JDialog();
        settingPanel = new JPanel(new MigLayout("wrap 3", "[][grow, fill][]"));
        settingWorkTimeLbl = new JLabel("Work");
        settingPauseTimeLbl = new JLabel("Pause");
        settingBigPauseTimeLbl = new JLabel("Big pause");
        settingCyclesOfTimeLbl = new JLabel("Cycles");
        settingSoundPathLbl = new JLabel("Work background sound");
        settingWorkTimeSlider = new JSlider();
        settingPauseTimeSlider = new JSlider();
        settingBigPauseTimeSlider = new JSlider();
        settingCyclesOfTimeSlider = new JSlider();
        settingWorkTimeTxt = new JTextField(2);
        settingPauseTimeTxt = new JTextField(2);
        settingBigPauseTimeTxt = new JTextField(2);
        settingCyclesOfTimeTxt = new JTextField(2);
        settingSoundFilePathTxt = new JTextField(10);
        settingsSoundFileChooser = new PomodoroFileChooser();
        openSoundFileBtn = new JButton("Choose sound file");
        saveSettingBtn = new JButton("Save", new ImageIcon("static/save.png"));
    }

    private void initContent() {
        contentPanel = new JPanel();
        settingBtn = new JButton(new ImageIcon("static/settings-button.png"));
        playBtn = new JButton(new ImageIcon("static/play-button.png"));
        pauseBtn = new JButton(new ImageIcon("static/pause-button.png"));
        resetBtn = new JButton(new ImageIcon("static/stop-button.png"));
        soundBtn = new JButton(new ImageIcon("static/sound-on.png"));
        counterLbl = new JLabel("00:00");
        cyclesLbl = new JLabel("0");
    }

    @Override
    public void init() {
        initSettingDialog();
        initContent();
    }

    private void setUpSettingDialog() {
        settingDialog.setContentPane(settingPanel);
        settingDialog.setLocationRelativeTo(this);
        settingDialog.setTitle("Pomodoro Settings");
        settingDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        settingWorkTimeTxt.setEditable(false);
        settingPauseTimeTxt.setEditable(false);
        settingBigPauseTimeTxt.setEditable(false);
        settingCyclesOfTimeTxt.setEditable(false);
        settingSoundFilePathTxt.setEditable(false);
        FileFilter filter = new FileNameExtensionFilter("Allowed file .wav, .mp3 ", "wav", "mp3");
        settingsSoundFileChooser.setAcceptAllFileFilterUsed(false);
        settingsSoundFileChooser.setFileFilter(filter);
    }

    private void setUpContent() {
        Dimension d20 = new Dimension(20, 20);
        settingBtn.setPreferredSize(d20);
        playBtn.setPreferredSize(d20);
        pauseBtn.setPreferredSize(d20);
        resetBtn.setPreferredSize(d20);
        soundBtn.setPreferredSize(d20);

        counterLbl.setOpaque(true);
    }

    private void _setUp() {
        this.setTitle("POMODORO");
        this.setAlwaysOnTop(true);
        this.setContentPane(contentPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    @Override
    public void setup() {
        setUpSettingDialog();
        setUpContent();
        _setUp();
    }

    private void buildSettingDialog() {
        settingPanel.add(settingWorkTimeLbl, "");
        settingPanel.add(settingWorkTimeSlider, "dock center");
        settingPanel.add(settingWorkTimeTxt, "");
        settingPanel.add(settingPauseTimeLbl, "");
        settingPanel.add(settingPauseTimeSlider, "dock center");
        settingPanel.add(settingPauseTimeTxt, "");
        settingPanel.add(settingBigPauseTimeLbl, "");
        settingPanel.add(settingBigPauseTimeSlider, "dock center");
        settingPanel.add(settingBigPauseTimeTxt, "");
        settingPanel.add(settingCyclesOfTimeLbl, "");
        settingPanel.add(settingCyclesOfTimeSlider, "dock center");
        settingPanel.add(settingCyclesOfTimeTxt, "");
        settingPanel.add(settingSoundPathLbl, "");
        settingPanel.add(openSoundFileBtn, "span 2, dock center");
        settingPanel.add(settingSoundFilePathTxt, "span 3, grow");
        settingPanel.add(saveSettingBtn, "span 3, gapy 15, align center");
        settingDialog.pack();
    }

    private void _build() {
        contentPanel.add(settingBtn);
        contentPanel.add(counterLbl);
        contentPanel.add(playBtn);
        contentPanel.add(pauseBtn);
        contentPanel.add(resetBtn);
        contentPanel.add(soundBtn);
        contentPanel.add(cyclesLbl);
    }

    @Override
    public void build() {
        buildSettingDialog();
        _build();

        pack();
        GuiUtil.setPositionBottomRight(this);
    }

    @Override
    public void showException(Throwable throwable) {
        JOptionPane.showMessageDialog(null, throwable.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setSettingsDialogVisible(boolean visible) {
        settingDialog.setVisible(visible);
    }

    @Override
    public void setSoundImageOnOff(boolean mark) {
        soundBtn.setIcon(mark ? new ImageIcon("static/sound-on.png") : new ImageIcon("static/sound-off.png"));
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
    public void setWorkTime(int workTime) {
        settingWorkTimeSlider.setValue(workTime);
        settingWorkTimeTxt.setText(String.valueOf(workTime));
    }

    @Override
    public void setPauseTime(int pauseTime) {
        settingPauseTimeSlider.setValue(pauseTime);
        settingPauseTimeTxt.setText(String.valueOf(pauseTime));
    }

    @Override
    public void setBigPauseTime(int bigPauseTime) {
        settingBigPauseTimeSlider.setValue(bigPauseTime);
        settingBigPauseTimeTxt.setText(String.valueOf(bigPauseTime));
    }

    @Override
    public void setCyclesOfTime(int cycles) {
        settingCyclesOfTimeSlider.setValue(cycles);
        settingCyclesOfTimeTxt.setText(String.valueOf(cycles));
    }


    @Override
    public void setWorkSliderRangeModel(SliderRangeModel sliderModel) {
        settingWorkTimeSlider.setModel(sliderModel);
    }

    @Override
    public void setPauseSliderRangeModel(SliderRangeModel sliderModel) {
        settingPauseTimeSlider.setModel(sliderModel);
    }

    @Override
    public void setBigPauseSliderRangeModel(SliderRangeModel sliderModel) {
        settingBigPauseTimeSlider.setModel(sliderModel);
    }

    @Override
    public void setCyclesSliderRangeModel(SliderRangeModel sliderModel) {
        settingCyclesOfTimeSlider.setModel(sliderModel);
    }

    @Override
    public void setSoundFileChooserModel(PomodoroFileChooserModel fileChooserModel) {
        settingsSoundFileChooser.setFileChooserModel(fileChooserModel);
    }

    @Override
    public void setSoundFileChooserVisible() {
        settingsSoundFileChooser.showOpenDialog(this);
    }

    @Override
    public void whenSettingButtonClick(ButtonClickHandler handler) {
        settingBtn.addActionListener(clickListener -> handler.click());
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

    @Override
    public void whenSaveSettingsButtonClick(ButtonClickHandler handler) {
        saveSettingBtn.addActionListener(clickListener -> handler.click());
    }

    @Override
    public void whenSoundButtonClick(ButtonClickHandler handler) {
        soundBtn.addActionListener(clickListener -> handler.click());
    }

    @Override
    public void whenSoundChooserButtonClick(ButtonClickHandler handler) {
        openSoundFileBtn.addActionListener(clickListener -> handler.click());
    }

    @Override
    public void whenSettingsDialogClose(CloseDialogHandler handler) {
        settingDialog.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                handler.close();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    @Override
    public void whenWorkSliderChange(SliderChangeHandler handler) {
        settingWorkTimeSlider.addChangeListener(slideListener -> handler.slide());
    }

    @Override
    public void whenPauseSliderChange(SliderChangeHandler handler) {
        settingPauseTimeSlider.addChangeListener(slideListener -> handler.slide());
    }

    @Override
    public void whenBigPauseSliderChange(SliderChangeHandler handler) {
        settingBigPauseTimeSlider.addChangeListener(slideListener -> handler.slide());
    }

    @Override
    public void whenCyclesSliderChange(SliderChangeHandler handler) {
        settingCyclesOfTimeSlider.addChangeListener(slideListener -> handler.slide());
    }

    @Override
    public void whenSoundFileChooserSelectionChange(FileSelectionChangeHandler selectionChangeHandler) {
        settingsSoundFileChooser.addActionListener(listener -> selectionChangeHandler.selectionChange());
    }
}
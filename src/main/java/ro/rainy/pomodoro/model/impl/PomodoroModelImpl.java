package ro.rainy.pomodoro.model.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import ro.rainy.pomodoro.event.EventDispatcher;
import ro.rainy.pomodoro.handler.ClockChangeStateHandler;
import ro.rainy.pomodoro.handler.CycleIncreaseHandler;
import ro.rainy.pomodoro.handler.TimeTypeSwitchHandler;
import ro.rainy.pomodoro.handler.VisibilityChangeHandler;
import ro.rainy.pomodoro.model.PomodoroModel;
import ro.rainy.pomodoro.model.bean.Config;
import ro.rainy.pomodoro.timer.Timer;
import ro.rainy.pomodoro.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/11/2020__23:45
 */
public class PomodoroModelImpl implements PomodoroModel {
    private static final String MIN_SEC_FORMAT = "%d:%02d";
    private final EventDispatcher<VisibilityChangeHandler> visibilityChangeHandlerEventDispatcher;
    private final EventDispatcher<ClockChangeStateHandler> clockChangeStateHandlerEventDispatcher;
    private final EventDispatcher<TimeTypeSwitchHandler> timeTypeSwitchHandlerEventDispatcher;
    private final EventDispatcher<CycleIncreaseHandler> cycleIncreaseHandlerEventDispatcher;
    private final Timer timer;
    private final Gson gson;
    private Config currentConfig;
    private boolean frameVisible;
    private int countDown;
    private int cycles = 0;
    private boolean isRelax = true;

    public PomodoroModelImpl() {
        this.visibilityChangeHandlerEventDispatcher = new EventDispatcher<>("visibilityChange");
        this.clockChangeStateHandlerEventDispatcher = new EventDispatcher<>("nextState");
        this.timeTypeSwitchHandlerEventDispatcher = new EventDispatcher<>("timeSwitch");
        this.cycleIncreaseHandlerEventDispatcher = new EventDispatcher<>("cycleChange");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.timer = new Timer() {
            @Override
            protected void onTick() {
                tick();
            }
        };
    }

    /**
     * Get a default configuration for app.
     *
     * @return Config
     */
    private Config getDefaultConfig() {
        Config config = new Config();
        config.setWorkTime(25);
        config.setPauseTime(5);
        config.setBigPauseTime(15);
        config.setCyclesToBigPause(4);
        return config;
    }

    /**
     * Check if configs file exist, otherwise create him with default values.
     *
     * @throws IOException
     */
    private void checkFileStructureOrCreate() throws IOException {
        File confFile = new File(Constants.CONF_FILE);
        if (!confFile.exists()) {
            confFile.getParentFile().mkdirs();
            FileUtils.write(confFile, gson.toJson(getDefaultConfig()), StandardCharsets.UTF_8, false);
        }
        //todo check sanity of data
    }

    @Override
    public void loadConfigs() {
        try {
            checkFileStructureOrCreate();
            File confFile = new File(Constants.CONF_FILE);
            String configAsJson = FileUtils.readFileToString(confFile, StandardCharsets.UTF_8);
            this.currentConfig = gson.fromJson(configAsJson, Config.class);
            setCountDown(currentConfig.getWorkTime());
            setTimeOnLabel(countDown);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * This method should be called inside of Timer#onTick listener.
     * Decrease initial time & compute how ending period impact the data.
     */
    private void tick() {
        countDown--;
        if (countDown == 0) {
            switchTimeType();
            if (!isRelax) {
                setCountDown(currentConfig.getWorkTime());
            } else {
                cycles++;
                fireUpdateCycles();
                if (cycles % currentConfig.getCyclesToBigPause() == 0) {
                    setCountDown(currentConfig.getBigPauseTime());
                } else {
                    setCountDown(currentConfig.getPauseTime());
                }
            }
        }
        setTimeOnLabel(countDown);
    }

    /**
     * Reset to initial data
     */
    private void reset() {
        timer.cancel();
        cycles = 0;
        isRelax = true; // in next line will be switched to proper value
        switchTimeType();
        setCountDown(currentConfig.getWorkTime());
        setTimeOnLabel(countDown);
        fireUpdateCycles();
    }

    private int toSeconds(int timeInMinutes) {
        return timeInMinutes * 60;
    }

    public void setCountDown(int countDownInMinutes) {
        this.countDown = toSeconds(countDownInMinutes);
    }

    /**
     * Trigger handler that will update in UI current number of cycles.
     */
    private void fireUpdateCycles() {
        cycleIncreaseHandlerEventDispatcher.dispatch();
    }

    @Override
    public void whenVisibilityChange(VisibilityChangeHandler visibilityChangeHandler) {
        visibilityChangeHandlerEventDispatcher.addListener(visibilityChangeHandler);
    }

    @Override
    public void setVisible(boolean visible) {
        frameVisible = visible;
        visibilityChangeHandlerEventDispatcher.dispatch();
    }

    @Override
    public boolean isFrameVisible() {
        return frameVisible;
    }

    public void registerClockChangeStateHandler(ClockChangeStateHandler handler) {
        clockChangeStateHandlerEventDispatcher.addListener(handler);
    }

    private void setTimeOnLabel(Integer time) {
        int minutes = time / 60;
        int seconds = time % 60;
        clockChangeStateHandlerEventDispatcher.dispatch(String.format(MIN_SEC_FORMAT, minutes, seconds));
    }

    @Override
    public void whenTimeTypeSwitch(TimeTypeSwitchHandler timeTypeSwitchHandler) {
        timeTypeSwitchHandlerEventDispatcher.addListener(timeTypeSwitchHandler);
    }

    public void switchTimeType() {
        isRelax = !isRelax;
        timeTypeSwitchHandlerEventDispatcher.dispatch();
    }

    @Override
    public boolean isRelax() {
        return isRelax;
    }

    @Override
    public void whenCyclesIncrease(CycleIncreaseHandler handler) {
        cycleIncreaseHandlerEventDispatcher.addListener(handler);
    }

    @Override
    public int getCycles() {
        return cycles;
    }

    @Override
    public void whenClockStart() {
        timer.start();
    }

    @Override
    public void whenClockPause() {
        timer.pause();
    }

    @Override
    public void whenClockReset() {
        reset();
    }

    @Override
    public BufferedImage getLogo() {
        try {
            return ImageIO.read(new File("static/pomodoro-logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
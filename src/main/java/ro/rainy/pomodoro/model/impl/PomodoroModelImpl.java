package ro.rainy.pomodoro.model.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.rainy.pomodoro.event.EventDispatcher;
import ro.rainy.pomodoro.handler.*;
import ro.rainy.pomodoro.model.PomodoroModel;
import ro.rainy.pomodoro.model.SliderRangeModel;
import ro.rainy.pomodoro.model.bean.Config;
import ro.rainy.pomodoro.timer.Timer;
import ro.rainy.pomodoro.util.Constants;

import javax.imageio.ImageIO;
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
    private static final Logger LOG = LoggerFactory.getLogger(PomodoroModelImpl.class);

    private static final String MIN_SEC_FORMAT = "%d:%02d";
    private final EventDispatcher<ExceptionThrownHandler> exceptionThrownHandlerEventDispatcher;
    private final EventDispatcher<VisibilityChangeHandler> visibilityChangeHandlerEventDispatcher;
    private final EventDispatcher<VisibilityChangeHandler> visibilitySettingsDialogChangeHandlerEventDispatcher;
    private final EventDispatcher<ClockChangeStateHandler> clockChangeStateHandlerEventDispatcher;
    private final EventDispatcher<TimeTypeSwitchHandler> timeTypeSwitchHandlerEventDispatcher;
    private final EventDispatcher<CycleIncreaseHandler> cycleIncreaseHandlerEventDispatcher;
    private final EventDispatcher<SettingTimeChangeHandler> settingWorkTimeChangeHandlerEventDispatcher;
    private final EventDispatcher<SettingTimeChangeHandler> settingPauseTimeChangeHandlerEventDispatcher;
    private final EventDispatcher<SettingTimeChangeHandler> settingBigPauseTimeChangeHandlerEventDispatcher;
    private final EventDispatcher<SettingTimeChangeHandler> settingCyclesChangeHandlerEventDispatcher;
    private final SliderRangeModel workSliderModel;
    private final SliderRangeModel pauseSliderModel;
    private final SliderRangeModel bigPauseSliderModel;
    private final SliderRangeModel cyclesSliderModel;
    private final Timer timer;
    private final Gson gson;
    private Config currentConfig;
    private boolean frameVisible;
    private boolean settingsDialogVisible;
    private int countDown;
    private int cycles = 0;
    private boolean isRelax = true;

    public PomodoroModelImpl() {
        this.exceptionThrownHandlerEventDispatcher = new EventDispatcher<>("exceptionThrown");
        this.visibilityChangeHandlerEventDispatcher = new EventDispatcher<>("visibilityChange");
        this.visibilitySettingsDialogChangeHandlerEventDispatcher = new EventDispatcher<>("visibilityChange");
        this.clockChangeStateHandlerEventDispatcher = new EventDispatcher<>("nextState");
        this.timeTypeSwitchHandlerEventDispatcher = new EventDispatcher<>("timeSwitch");
        this.cycleIncreaseHandlerEventDispatcher = new EventDispatcher<>("cycleChange");
        this.settingWorkTimeChangeHandlerEventDispatcher = new EventDispatcher<>("timeChange");
        this.settingPauseTimeChangeHandlerEventDispatcher = new EventDispatcher<>("timeChange");
        this.settingBigPauseTimeChangeHandlerEventDispatcher = new EventDispatcher<>("timeChange");
        this.settingCyclesChangeHandlerEventDispatcher = new EventDispatcher<>("timeChange");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.timer = new Timer() {
            @Override
            protected void onTick() {
                tick();
            }
        };
        this.workSliderModel = new SliderRangeModelImpl(1, 0, 1, 100);
        this.pauseSliderModel = new SliderRangeModelImpl(1, 0, 1, 50);
        this.bigPauseSliderModel = new SliderRangeModelImpl(1, 0, 1, 50);
        this.cyclesSliderModel = new SliderRangeModelImpl(1, 0, 1, 10);
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

    private void writeConfToFile(File confFile, Config config) throws IOException {
        FileUtils.write(confFile, gson.toJson(config), StandardCharsets.UTF_8, false);
    }

    private void writeConf(Config config) throws IOException {
        File confFile = new File(Constants.CONF_FILE);
        writeConfToFile(confFile, config);
    }

    /**
     * Check if configs file exist, otherwise create him with default values.
     *
     * @throws IOException
     */
    private void checkFileStructureOrCreate() throws IOException {
        File confFile = new File(Constants.CONF_FILE);
        LOG.debug("Check if conf file exits, '{}'", confFile);
        if (!confFile.exists()) {
            confFile.getParentFile().mkdirs();
            writeConf(getDefaultConfig());
        }
        //todo check sanity of data
    }

    @Override
    public void loadConfigs() {
        LOG.info("Trying to load configuration file");
        try {
            checkFileStructureOrCreate();
            File confFile = new File(Constants.CONF_FILE);
            String configAsJson = FileUtils.readFileToString(confFile, StandardCharsets.UTF_8);
            LOG.debug("Actual configuration : {}", configAsJson);
            currentConfig = gson.fromJson(configAsJson, Config.class);
            setCountDown(currentConfig.getWorkTime());
            setTimeOnLabel(countDown);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            exceptionThrownHandlerEventDispatcher.dispatch(e);
            System.exit(1);
        }
    }

    /**
     * This method should be called inside of Timer#onTick listener.
     * Decrease initial time & compute how ending period impact the flow.
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

    private void bindDataFromSliderModelToDomain() {
        currentConfig.setWorkTime(workSliderModel.getValue());
        currentConfig.setPauseTime(pauseSliderModel.getValue());
        currentConfig.setBigPauseTime(bigPauseSliderModel.getValue());
        currentConfig.setCyclesToBigPause(cyclesSliderModel.getValue());
    }

    /**
     * Trigger handler that will update in UI current number of cycles.
     */
    private void fireUpdateCycles() {
        cycleIncreaseHandlerEventDispatcher.dispatch();
    }

    @Override
    public void whenExceptionThrown(ExceptionThrownHandler exceptionThrownHandler) {
        exceptionThrownHandlerEventDispatcher.addListener(exceptionThrownHandler);
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

    @Override
    public void whenSettingsDialogVisibilityChange(VisibilityChangeHandler visibilityChangeHandler) {
        visibilitySettingsDialogChangeHandlerEventDispatcher.addListener(visibilityChangeHandler);
    }

    @Override
    public void setSettingsDialogVisible(boolean visible) {
        this.settingsDialogVisible = visible;
        visibilitySettingsDialogChangeHandlerEventDispatcher.dispatch();
    }

    @Override
    public boolean isSettingsDialogVisible() {
        return this.settingsDialogVisible;
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
    public void whenSettingWorkTimeChange(SettingTimeChangeHandler timeChangeHandler) {
        settingWorkTimeChangeHandlerEventDispatcher.addListener(timeChangeHandler);
    }

    @Override
    public void whenSettingPauseTimeChange(SettingTimeChangeHandler timeChangeHandler) {
        settingPauseTimeChangeHandlerEventDispatcher.addListener(timeChangeHandler);
    }

    @Override
    public void whenSettingBigPauseTimeChange(SettingTimeChangeHandler timeChangeHandler) {
        settingBigPauseTimeChangeHandlerEventDispatcher.addListener(timeChangeHandler);
    }

    @Override
    public void whenSettingCyclesTimeChange(SettingTimeChangeHandler timeChangeHandler) {
        settingCyclesChangeHandlerEventDispatcher.addListener(timeChangeHandler);
    }

    @Override
    public int getWorkTime() {
        return currentConfig.getWorkTime();
    }

    @Override
    public int getPauseTime() {
        return currentConfig.getPauseTime();
    }

    @Override
    public int getBigPauseTime() {
        return currentConfig.getBigPauseTime();
    }

    @Override
    public int getCyclesOfTime() {
        return currentConfig.getCyclesToBigPause();
    }

    //------------
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
    public void whenSettingDialogClose() {
        setSettingsDialogVisible(false);
        populateSettingsDialog();
    }

    @Override
    public void whenConfigUpdate() {
        try {
            bindDataFromSliderModelToDomain();
            LOG.debug("New configurations : {}", currentConfig);
            writeConf(currentConfig);
            reset();
            setSettingsDialogVisible(false);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            exceptionThrownHandlerEventDispatcher.dispatch(e);
        }
    }

    @Override
    public BufferedImage getLogo() {
        try {
            return ImageIO.read(new File("static/pomodoro-logo.png"));
        } catch (IOException e) {
            LOG.error(e.getMessage());
            exceptionThrownHandlerEventDispatcher.dispatch(e);
        }
        return null;
    }

    @Override
    public SliderRangeModel getWorkSliderRangeModel() {
        return this.workSliderModel;
    }

    @Override
    public SliderRangeModel getPauseSliderRangeModel() {
        return this.pauseSliderModel;
    }

    @Override
    public SliderRangeModel getBigPauseSliderRangeModel() {
        return this.bigPauseSliderModel;
    }

    @Override
    public SliderRangeModel getCyclesSliderRangeModel() {
        return this.cyclesSliderModel;
    }

    @Override
    public void populateSettingsDialog() {
        settingWorkTimeChangeHandlerEventDispatcher.dispatch();
        settingPauseTimeChangeHandlerEventDispatcher.dispatch();
        settingBigPauseTimeChangeHandlerEventDispatcher.dispatch();
        settingCyclesChangeHandlerEventDispatcher.dispatch();
    }
}
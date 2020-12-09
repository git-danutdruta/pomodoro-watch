package ro.rainy.pomodoro.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/12/2020__19:53
 */
public class Config {
    @SerializedName("work_time")
    private int workTime;

    @SerializedName("pause_time")
    private int pauseTime;

    @SerializedName("big_pause_time")
    private int bigPauseTime;

    @SerializedName("cycles_to_big_pause")
    private int cyclesToBigPause;

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(int pauseTime) {
        this.pauseTime = pauseTime;
    }

    public int getBigPauseTime() {
        return bigPauseTime;
    }

    public void setBigPauseTime(int bigPauseTime) {
        this.bigPauseTime = bigPauseTime;
    }

    public int getCyclesToBigPause() {
        return cyclesToBigPause;
    }

    public void setCyclesToBigPause(int cyclesToBigPause) {
        this.cyclesToBigPause = cyclesToBigPause;
    }
}
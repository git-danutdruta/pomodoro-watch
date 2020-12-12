package ro.rainy.pomodoro.model.impl;

import ro.rainy.pomodoro.model.SliderRangeModel;

import javax.swing.*;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 10/12/2020__23:46
 */
public class SliderRangeModelImpl extends DefaultBoundedRangeModel implements SliderRangeModel {
    public SliderRangeModelImpl(int value, int extent, int min, int max) {
        super(value, extent, min, max);
    }
}
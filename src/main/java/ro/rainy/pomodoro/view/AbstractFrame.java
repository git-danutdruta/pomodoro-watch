package ro.rainy.pomodoro.view;

import javax.swing.*;
import java.awt.*;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 10/11/2020__01:27
 */
public abstract class AbstractFrame extends JFrame implements Structure {
    public AbstractFrame() throws HeadlessException {
        super();
        init();
        setup();
        build();
    }
}
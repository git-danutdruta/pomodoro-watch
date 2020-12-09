package ro.rainy.pomodoro.util;

import java.awt.*;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/12/2020__09:51
 */
public class GuiUtil {

    public static void setPositionBottomRight(Component c) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle screen = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) screen.getMaxX() - c.getX();
        int y = (int) screen.getMaxY() - c.getY();
        c.setLocation(x, y);
    }
}
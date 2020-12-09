package ro.rainy.pomodoro.view;

/**
 * @proiect: pomodoro
 * @autor: daniel
 * @data: 09/11/2020__23:46
 */
public interface Structure {
    /**
     * Init all members.
     */
    void init();

    /**
     * settings.
     */
    void setup();

    /**
     * Fetch position of components.
     */
    void build();
}

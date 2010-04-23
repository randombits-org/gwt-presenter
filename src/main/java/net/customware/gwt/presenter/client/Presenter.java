package net.customware.gwt.presenter.client;

public interface Presenter {

    /**
     * Called then the presenter is initialised. This is called before any other
     * methods. Any event handlers and other setup should be done here rather
     * than in the constructor.
     */
    void bind();

    /**
     * Called after the presenter and display have been finished with for the
     * moment.
     */
    void unbind();

    /**
     * Returns the {@link Display} for the current presenter.
     * 
     * @return The display.
     */
    Display getDisplay();

    /**
     * Requests the presenter to refresh the contents of the display. This does
     * <b>not</b> force the display to be revealed on screen.
     */
    void refreshDisplay();

    /**
     * Requests the presenter to reveal the display on screen. It should
     * automatically ask any parent displays/presenters to reveal themselves
     * also. It should <b>not</b> trigger a refresh.
     */
    void revealDisplay();
}

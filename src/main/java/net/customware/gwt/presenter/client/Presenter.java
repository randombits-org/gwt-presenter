package net.customware.gwt.presenter.client;

public interface Presenter {

    /**
     * Called when the presenter is initialised. This is called before any other
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
     * Returns true if the presenter is currently in a 'bound' state. That is,
     * the {@link #bind()} method has completed and {@link #unbind()} has not
     * been called.
     *
     * @return <code>true</code> if bound.
     */
    boolean isBound();

    /**
     * Returns the {@link Display} for the current presenter.
     *
     * @return The display.
     */
    Display getDisplay();

    /**
     * Requests the presenter to reveal the display on screen. It should
     * automatically ask any parent displays/presenters to reveal themselves
     * also. It should <b>not</b> trigger a refresh.
     */
    void revealDisplay();
}

package net.customware.gwt.presenter.client;

import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;

public abstract class BasicPresenter<D extends Display> implements Presenter {

    /**
     * The display for the presenter.
     */
    protected final D display;

    /**
     * The {@link EventBus} for the application.
     */
    protected final EventBus eventBus;

    private List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();

    private boolean bound = false;

    public BasicPresenter( D display, EventBus eventBus ) {
        this.display = display;
        this.eventBus = eventBus;
    }

    public void bind() {
        onBind();
        bound = true;
    }

    /**
     * Any {@link HandlerRegistration}s added will be removed when
     * {@link #unbind()} is called. This provides a handy way to track event
     * handler registrations when binding and unbinding.
     * 
     * @param handlerRegistration
     *            The registration.
     */
    protected void registerHandler( HandlerRegistration handlerRegistration ) {
        handlerRegistrations.add( handlerRegistration );
    }

    public void unbind() {
        bound = false;

        for ( HandlerRegistration reg : handlerRegistrations ) {
            reg.removeHandler();
        }
        handlerRegistrations.clear();

        onUnbind();

    }

    /**
     * This method is called when binding the presenter. Any additional bindings
     * should be done here.
     */
    protected abstract void onBind();

    /**
     * This method is called when unbinding the presenter. Any handler
     * registrations recorded with {@link #registerHandler(HandlerRegistration)}
     * will have already been removed at this point.
     */
    protected abstract void onUnbind();

    /**
     * Checks if the presenter has been bound. Will be set to false after a call
     * to {@link #unbind()}.
     * 
     * @return The current bound status.
     */
    public boolean isBound() {
        return bound;
    }

    /**
     * Returns the display for the presenter.
     * 
     * @return The display.
     */
    public D getDisplay() {
        return display;
    }

    /**
     * Fires a {@link PresenterRefreshedEvent} to the {@link EventBus}.
     * Implementations should call this <em>after</em> any work has been
     * completed and the display has actually been refreshed.
     */
    protected void firePresenterRefreshedEvent() {
        eventBus.fireEvent( new PresenterRefreshedEvent( this ) );
    }

    protected void firePresenterRevealedEvent( boolean originator ) {
        eventBus.fireEvent( new PresenterRevealedEvent( this, originator ) );
    }

    /**
     * Triggers a {@link PresenterRevealedEvent}. Subclasses should override
     * this method and call <code>super.revealDisplay()</code> if they need to
     * perform extra operations when being revealed.
     */
    public void revealDisplay() {
        revealDisplay( true );
    }

    protected void revealDisplay( boolean originator ) {
        firePresenterRevealedEvent( originator );
    }

}

package net.customware.gwt.presenter.client;

import com.google.gwt.event.shared.HandlerRegistration;

import java.util.List;

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
        if ( !bound ) {
            onBind();
            bound = true;
        }
    }

    /**
     * Any {@link HandlerRegistration}s added will be removed when
     * {@link #unbind()} is called. This provides a handy way to track event
     * handler registrations when binding and unbinding.
     *
     * @param handlerRegistration The registration.
     */
    protected void registerHandler( HandlerRegistration handlerRegistration ) {
        handlerRegistrations.add( handlerRegistration );
    }

    public void unbind() {
        if ( bound ) {
            bound = false;

            for ( HandlerRegistration reg : handlerRegistrations ) {
                reg.removeHandler();
            }
            handlerRegistrations.clear();

            onUnbind();
        }

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
     * Fires a {@link PresenterChangedEvent} to the {@link EventBus}.
     * Call this method any time the presenter's state has been modified.
     */
    protected void firePresenterChangedEvent() {
        eventBus.fireEvent( new PresenterChangedEvent( this ) );
    }

    /**
     * Fires a {@link PresenterRevealedEvent} to the {@link EventBus}.
     * Implementations should call this when the presenter has been
     * revealed onscreen.
     *
     * @param originator If set to true, this specifies that this presenter
     *                   was the originator of the 'revelation' request.
     */
    protected void firePresenterRevealedEvent( boolean originator ) {
        eventBus.fireEvent( new PresenterRevealedEvent( this, originator ) );
    }

    /**
     * Triggers a {@link PresenterRevealedEvent}. Subclasses should override
     * this method and call <code>super.revealDisplay()</code> if they need to
     * perform extra operations when being revealed.
     */
    public void revealDisplay() {
        onRevealDisplay();
        firePresenterRevealedEvent( true );
    }

    /**
     * Called before firing a {@link net.customware.gwt.presenter.client.PresenterRevealedEvent}.
     */
    protected abstract void onRevealDisplay();

}

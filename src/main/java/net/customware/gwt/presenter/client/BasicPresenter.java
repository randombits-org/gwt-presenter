package net.customware.gwt.presenter.client;

import java.util.List;

import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.place.PlaceRequestHandler;

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

        if ( getPlace() != null ) {
            registerHandler( eventBus.addHandler( PlaceRequestEvent.getType(), new PlaceRequestHandler() {

                public void onPlaceRequest( PlaceRequestEvent event ) {
                    Place place = getPlace();
                    if ( place != null && place.equals( event.getRequest().getPlace() ) ) {
                        BasicPresenter.this.onPlaceRequest( event.getRequest() );
                        revealDisplay();
                    }
                }
            } ) );
        }
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
        for ( HandlerRegistration reg : handlerRegistrations ) {
            reg.removeHandler();
        }
        handlerRegistrations.clear();

        onUnbind();

        bound = false;
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

    protected PlaceRequest getPlaceRequest() {
        return getPlace().request();
    }

    /**
     * The {@link Place} that represents this Presenter.
     * 
     * @return The presenter's place.
     */
    public abstract Place getPlace();

    /**
     * This method is called when a {@link PlaceRequestEvent} occurs that
     * matches with the value from {@link #getPlace()}.
     * 
     * @param request
     *            The request.
     */
    protected abstract void onPlaceRequest( PlaceRequest request );

    /**
     * Triggers a {@link PresenterRevealedEvent}. Subclasses should override
     * this method and call <code>super.revealDisplay()</code> if they need to
     * perform extra operations when being revealed.
     */
    public void revealDisplay() {
        eventBus.fireEvent( new PresenterRevealedEvent( this ) );
    }

}

package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.shared.HandlerRegistration;
import net.customware.gwt.presenter.client.EventBus;

/**
 * A place represents a particular 'bookmark' or location inside the
 * application. A place is stateful - it may represent a location with it's
 * current settings, such as a particular ID value, or other unique indicators
 * that will allow a user to track back to that location later, either via a
 * browser bookmark, or by clicking the 'back' button.
 * <p/>
 * However, there may be more than one instance of concrete Place classes, so
 * the state should be shared between all instances of any given class. Usually
 * this is done via a shared class, such as a {@link net.customware.gwt.presenter.client.Presenter} instance.
 *
 * @author David Peterson
 */
public abstract class Place {
    private HandlerRegistration placeRequestRegistration;

    public Place() {
    }

    /**
     * Returns the unique name for this place. Uniqueness is not enforced -
     * creators must ensure that the name is unique or there will be potential
     * issues with multiple places responding to the same History token.
     *
     * @return The place ID.
     */
    public abstract String getName();

    @Override
    public boolean equals( Object o ) {
        if ( o instanceof Place ) {
            Place place = (Place) o;
            return getName().equals( place.getName() );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 17 * getName().hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * This method is called when a matching request is received.
     *
     * @param request The place request.
     */
    protected abstract void handleRequest( PlaceRequest request );

    /**
     * This method is checked before calling
     * {@link #handleRequest(PlaceRequest)}.
     *
     * @param request The request to check.
     * @return <code>true</code> if the ID matches this place's name.
     */
    public boolean matchesRequest( PlaceRequest request ) {
        return getName().equals( request.getName() );
    }

    /**
     * Returns a new request for this place in its current state. This method
     * calls {@link #prepareRequest(PlaceRequest)} before returning.
     *
     * @return The new {@link PlaceRequest}.
     */
    public PlaceRequest createRequest() {
        return prepareRequest( new PlaceRequest( getName() ) );
    }

    /**
     * Returns the request with any extra values relevant to the current place
     * initialised. If nothing needs to be done, return the request that was
     * passed in.
     * <p/>
     * <p/>
     * Typical usage:
     * <p/>
     * <pre>
     * return request.with( &quot;name&quot;, idValue ).with( &quot;name&quot;, &quot;Foo&quot; );
     * </pre>
     *
     * @param request The current request object.
     * @return The prepared request.
     */
    protected abstract PlaceRequest prepareRequest( PlaceRequest request );

    protected abstract void reveal();

    /**
     * This method is called if the place should register itself with the event bus. Only one
     * instance of any given concrete Place should be registered. The {@link PlaceManager} should
     * enforce this however, so in general, don't call this method directly.
     *
     * @param eventBus The event bus.
     */
    protected void addHandlers( final EventBus eventBus ) {
        placeRequestRegistration = eventBus.addHandler( PlaceRequestEvent.getType(), new PlaceRequestHandler() {
            public void onPlaceRequest( PlaceRequestEvent event ) {
                PlaceRequest request = event.getRequest();
                if ( matchesRequest( request ) ) {
                    handleRequest( request );
                    eventBus.fireEvent( new PlaceRevealedEvent( Place.this ) );
                }
            }
        } );
    }

    /**
     * This method is called when it should deregister any event handlers that were registered with the
     * {@link #addHandlers(net.customware.gwt.presenter.client.EventBus)} method.
     *
     * @param eventBus The event bus.
     */
    protected void removeHandlers( EventBus eventBus ) {
        if ( placeRequestRegistration != null ) {
            placeRequestRegistration.removeHandler();
            placeRequestRegistration = null;
        }
    }
}

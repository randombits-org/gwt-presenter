package net.customware.gwt.presenter.client.place;

import net.customware.gwt.presenter.client.EventBus;

/**
 * A place represents a particular 'bookmark' or location inside the
 * application. A place is stateful - it may represent a location with it's
 * current settings, such as a particular ID value, or other unique indicators
 * that will allow a user to track back to that location later, either via a
 * browser bookmark, or by clicking the 'back' button.
 * 
 * @author David Peterson
 * 
 */
public abstract class Place implements PlaceRequestHandler {
    private final String name;

    private final EventBus eventBus;

    public Place( String name, EventBus eventBus ) {
        this.name = name;
        this.eventBus = eventBus;
        eventBus.addHandler( PlaceRequestEvent.getType(), this );
    }

    /**
     * Provides access to the {@link EventBus}.
     * 
     * @return The event bus.
     */
    protected EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Returns the unique name for this place. Uniqueness is not enforced -
     * creators must ensure that the name is unique or there will be potential
     * issues with multiple places responding to the same History token.
     * 
     * @return The place ID.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o instanceof Place ) {
            Place place = ( Place ) o;
            return name.equals( place.name );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 17 * name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public void onPlaceRequest( PlaceRequestEvent event ) {
        PlaceRequest request = event.getRequest();
        if ( matchesRequest( request ) ) {
            handleRequest( request );
            firePlaceChangedEvent();
        }
    }

    /**
     * This method is called when a matching request is received.
     * 
     * @param request
     *            The place request.
     */
    protected abstract void handleRequest( PlaceRequest request );

    /**
     * This method is checked before calling
     * {@link #handleRequest( PlaceRequest )}.
     * 
     * @param name
     *            The name to check.
     * @return <code>true</code> if the ID matches this place's name.
     */
    public boolean matchesRequest( PlaceRequest request ) {
        return this.name.equals( request.getId() );
    }

    /**
     * Fires a {@link PlaceChangedEvent} event into the {@link EventBus}.
     * 
     * @param request
     *            The place request to send.
     */
    protected void firePlaceChangedEvent() {
        eventBus.fireEvent( new PlaceChangedEvent( this ) );
    }

    /**
     * Fires a {@link PlaceRevealedEvent} into the {@link EventBus}.
     * 
     * @param request
     *            The place request to send.
     */
    protected void firePlaceRevealedEvent() {
        eventBus.fireEvent( new PlaceRevealedEvent( this ) );
    }

    /**
     * Returns a new request for this place in its current state. This method
     * calls {@link #prepareRequest(PlaceRequest)} before returning.
     * 
     * @return The new {@link PlaceRequest}.
     */
    public PlaceRequest createRequest() {
        return prepareRequest( new PlaceRequest( name ) );
    }

    /**
     * Returns the request with any extra values relevant to the current place
     * initialised. If nothing needs to be done, return the request that was
     * passed in.
     * 
     * <p>
     * Typical usage:
     * 
     * <pre>
     * return request.with( &quot;name&quot;, idValue ).with( &quot;name&quot;, &quot;Foo&quot; );
     * </pre>
     * 
     * @param request
     *            The current request object.
     * @return The prepared request.
     */
    protected abstract PlaceRequest prepareRequest( PlaceRequest request );

    protected abstract void reveal();
}

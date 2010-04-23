package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This event is triggered when the request has changed manually (ie, not due to a
 * {@link PlaceRequestEvent}). This allows the {@link PlaceManager} to keep
 * track of the current location. Other classes may, but will typically not need
 * to, implement {@link PlaceChangedHandler} to be informed of manual changes.
 * 
 * @author David Peterson
 * 
 */
public class PlaceChangedEvent extends GwtEvent<PlaceChangedHandler> {

    private static Type<PlaceChangedHandler> TYPE;

    public static Type<PlaceChangedHandler> getType() {
        if ( TYPE == null )
            TYPE = new Type<PlaceChangedHandler>();
        return TYPE;
    }

    private final PlaceRequest request;

    public PlaceChangedEvent( PlaceRequest request ) {
        this.request = request;
    }

    @Override
    protected void dispatch( PlaceChangedHandler handler ) {
        handler.onPlaceChange( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<PlaceChangedHandler> getAssociatedType() {
        return getType();
    }

    public PlaceRequest getRequest() {
        return request;
    }
}

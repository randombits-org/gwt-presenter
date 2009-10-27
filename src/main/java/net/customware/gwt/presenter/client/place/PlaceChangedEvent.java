package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This event is triggered when any {@link Place} has changed state. It may or
 * may not be the current 'revealed' place.
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

    private final Place place;

    public PlaceChangedEvent( Place place ) {
        this.place = place;
    }

    @Override
    protected void dispatch( PlaceChangedHandler handler ) {
        handler.onPlaceChange( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<PlaceChangedHandler> getAssociatedType() {
        return getType();
    }

    public Place getPlace() {
        return place;
    }
}

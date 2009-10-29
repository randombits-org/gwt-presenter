package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.shared.GwtEvent;

public class PlaceRevealedEvent extends GwtEvent<PlaceRevealedHandler> {

    private static GwtEvent.Type<PlaceRevealedHandler> TYPE = new GwtEvent.Type<PlaceRevealedHandler>();

    public static GwtEvent.Type<PlaceRevealedHandler> getType() {
        return TYPE;
    }

    private final Place place;

    public PlaceRevealedEvent( Place place ) {
        this.place = place;
    }

    /**
     * Returns the place that was revealed.
     *
     * @return the place.
     */
    public Place getPlace() {
        return place;
    }

    @Override
    protected void dispatch( PlaceRevealedHandler handler ) {
        handler.onPlaceRevealed( this );
    }

    @Override
    public GwtEvent.Type<PlaceRevealedHandler> getAssociatedType() {
        return TYPE;
    }
}

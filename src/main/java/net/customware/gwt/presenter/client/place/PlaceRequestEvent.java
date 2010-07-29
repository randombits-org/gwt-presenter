package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.shared.GwtEvent;
import net.customware.gwt.presenter.client.EventBus;

public class PlaceRequestEvent extends GwtEvent<PlaceRequestHandler> {

    private static Type<PlaceRequestHandler> TYPE;

    public static Type<PlaceRequestHandler> getType() {
        if ( TYPE == null )
            TYPE = new Type<PlaceRequestHandler>();
        return TYPE;
    }

    private final PlaceRequest request;

    private final boolean fromHistory;

    public PlaceRequestEvent( PlaceRequest request ) {
        this( request, false );
    }

    PlaceRequestEvent( PlaceRequest request, boolean fromHistory ) {
        this.request = request;
        this.fromHistory = fromHistory;
    }

    @Override
    protected void dispatch( PlaceRequestHandler handler ) {
        handler.onPlaceRequest( this );
    }

    @Override
    public Type<PlaceRequestHandler> getAssociatedType() {
        return getType();
    }

    public PlaceRequest getRequest() {
        return request;
    }

    boolean isFromHistory() {
        return fromHistory;
    }

    public static void fire( EventBus eventBus, PlaceRequest request ) {
        fire( eventBus, request, false );
    }

    static void fire( EventBus eventBus, PlaceRequest request, boolean fromHistory ) {
        eventBus.fireEvent( new PlaceRequestEvent( request, fromHistory ) );
    }
}

package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.shared.EventHandler;

public interface PlaceChangedHandler extends EventHandler {
    /**
     * Called after the current place has already changed. Allows handlers to
     * update any internal tracking, etc.
     * 
     * @param event
     *            The event.
     */
    void onPlaceChange( PlaceChangedEvent event );
}

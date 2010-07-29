package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.shared.EventHandler;

public interface PlaceRevealedHandler extends EventHandler {
    /**
     * Called when a {@link Place} has been revealed to the user.
     *
     * @param event The even.
     */
    void onPlaceRevealed( PlaceRevealedEvent event );
}

package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.customware.gwt.presenter.client.EventBus;

@Singleton
public class PlaceManager {

    private class PlaceEventHandler implements ValueChangeHandler<String>, PlaceRevealedHandler,
            PlaceChangedHandler {

        @Override
        public void onPlaceRevealed( PlaceRevealedEvent event ) {
            updateHistory( event.getPlace() );
        }

        public void onPlaceChange( PlaceChangedEvent event ) {
            Place place = event.getPlace();
            try {
                if ( place.matchesRequest( PlaceRequest.fromHistoryToken( History.getToken() ) ) ) {
                    // Only update if the change comes from a place that matches
                    // the current location.
                    updateHistory( event.getPlace() );
                }
            } catch ( PlaceParsingException e ) {
                // Do nothing...
            }
        }

        /**
         * Handles change events from {@link History}.
         */
        public void onValueChange( ValueChangeEvent<String> event ) {
            try {
                eventBus
                        .fireEvent( new PlaceRequestEvent( PlaceRequest.fromHistoryToken( event.getValue() ), true ) );
            } catch ( PlaceParsingException e ) {
                e.printStackTrace();
            }
        }
    }

    private final EventBus eventBus;

    @Inject
    public PlaceManager( EventBus eventBus ) {
        this.eventBus = eventBus;

        PlaceEventHandler handler = new PlaceEventHandler();

        // Register ourselves with the History API.
        History.addValueChangeHandler( handler );

        // Listen for manual place change events.
        eventBus.addHandler( PlaceChangedEvent.getType(), handler );

        // Listen for place revelation requests.
        eventBus.addHandler( PlaceRevealedEvent.getType(), handler );
    }

    private void updateHistory( Place place ) {
        updateHistory( place.createRequest() );
    }

    // Updates History if it has changed, without firing another
    // 'ValueChangeEvent'.
    private void updateHistory( PlaceRequest request ) {
        String requestToken = request.toHistoryToken();
        String historyToken = History.getToken();
        if ( historyToken == null || !historyToken.equals( requestToken ) )
            History.newItem( requestToken, false );
    }

    /**
     * Fires a {@link PlaceRequestEvent} with the current history token, if
     * present. If no history token is set, <code>false</code> is returned.
     * 
     * @return <code>true</code>
     */
    public boolean fireCurrentPlace() {
        if ( History.getToken() != null ) {
            History.fireCurrentHistoryState();
            return true;
        }
        return false;
    }
}

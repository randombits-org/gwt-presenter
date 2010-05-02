package net.customware.gwt.presenter.client;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a presenter revelation event.
 * 
 * @author David Peterson
 * 
 */
public interface PresenterRevealedHandler extends EventHandler {

    void onPresenterRevealed( PresenterRevealedEvent event );

}

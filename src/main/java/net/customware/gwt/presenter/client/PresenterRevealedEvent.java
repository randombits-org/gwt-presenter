package net.customware.gwt.presenter.client;

import net.customware.gwt.presenter.client.Presenter;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Presenters can send this event to the {@link EventBus} to notify other
 * interested parties when the presenter has been 'revealed' on the screen. This
 * is particularly useful for situations where a presenter contains other
 * presenters and wants needs to reveal itself when a child presenter is
 * revealed.
 * 
 * @author David Peterson
 * 
 */
public class PresenterRevealedEvent extends GwtEvent<PresenterRevealedHandler> {

    private static final GwtEvent.Type<PresenterRevealedHandler> TYPE = new GwtEvent.Type<PresenterRevealedHandler>();

    private Presenter presenter;

    public static GwtEvent.Type<PresenterRevealedHandler> getType() {
        return TYPE;
    }

    public PresenterRevealedEvent( Presenter presenter ) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void dispatch( PresenterRevealedHandler handler ) {
        handler.onPresenterRevealed( this );
    }

    @Override
    public GwtEvent.Type<PresenterRevealedHandler> getAssociatedType() {
        return getType();
    }

}

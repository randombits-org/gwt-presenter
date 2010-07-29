package net.customware.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent;

public class PresenterChangedEvent extends GwtEvent<PresenterChangedHandler> {

    private static final Type<PresenterChangedHandler> TYPE = new Type<PresenterChangedHandler>();

    public static Type<PresenterChangedHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, Presenter presenter ) {
        eventBus.fireEvent( new PresenterChangedEvent( presenter ) );
    }

    private final Presenter presenter;

    public PresenterChangedEvent( Presenter presenter ) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void dispatch( PresenterChangedHandler handler ) {
        handler.onPresenterChanged( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<PresenterChangedHandler> getAssociatedType() {
        return getType();
    }
}

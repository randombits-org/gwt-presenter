package net.customware.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent;

public class PresenterRefreshedEvent extends GwtEvent<PresenterRefreshedHandler> {

    private static final Type<PresenterRefreshedHandler> TYPE = new Type<PresenterRefreshedHandler>();

    public static Type<PresenterRefreshedHandler> getType() {
        return TYPE;
    }

    private final Presenter presenter;

    public PresenterRefreshedEvent( Presenter presenter ) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void dispatch( PresenterRefreshedHandler handler ) {
        handler.onPresenterRefreshed( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<PresenterRefreshedHandler> getAssociatedType() {
        return getType();
    }

}

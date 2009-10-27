package net.customware.gwt.presenter.client.place;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.Presenter;

/**
 * A simple implementation of {@link PresenterPlace} for {@link Presenter}s
 * that don't need to be prepared for display. These are mostly presenters which
 * don't have any initialisation parameters.
 * 
 * @author David Peterson
 * 
 * @param <T>
 *            The presenter class type.
 */
public abstract class SimplePresenterPlace<T extends Presenter> extends PresenterPlace<T> {

    public SimplePresenterPlace( String value, EventBus eventBus, T presenter ) {
        super( value, eventBus, presenter );
    }

    /**
     * Does nothing by default.
     */
    @Override
    protected void preparePresenter( PlaceRequest request, T presenter ) {
        // Do nothing.
    }

    /**
     * Returns the request unchanged.
     */
    @Override
    protected PlaceRequest prepareRequest( PlaceRequest request, T presenter ) {
        return request;
    }

}

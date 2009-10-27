package net.customware.gwt.presenter.client.place;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.Presenter;
import net.customware.gwt.presenter.client.PresenterRefreshedEvent;
import net.customware.gwt.presenter.client.PresenterRefreshedHandler;
import net.customware.gwt.presenter.client.PresenterRevealedEvent;
import net.customware.gwt.presenter.client.PresenterRevealedHandler;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * This is a subclass of {@link Place} with some helper values for working with
 * {@link Presenter}s.
 * 
 * @author David Peterson
 * 
 */
public abstract class PresenterPlace<T extends Presenter> extends Place implements PresenterRefreshedHandler,
        PresenterRevealedHandler {

    private final T presenter;

    public PresenterPlace( String value, EventBus eventBus, T presenter ) {
        super( value, eventBus );
        this.presenter = presenter;

        eventBus.addHandler( PresenterRefreshedEvent.getType(), this );
        eventBus.addHandler( PresenterRevealedEvent.getType(), this );
    }

    public T getPresenter() {
        return presenter;
    }

    /**
     * Calls the {@link Presenter#revealDisplay()} method for the place's
     * presenter.
     */
    @Override
    public void reveal() {
        getPresenter().revealDisplay();
    }

    /**
     * Listens for {@link PresenterRefreshedEvent}s that match the place's
     * {@link Presenter} and fires {@link PlaceChangedEvent} based on the
     * {@link Presenter}'s current state, calling
     * {@link #prepareRequest(PlaceRequest, Presenter)} to configure the
     * request.
     * 
     * @param event
     *            The event.
     */
    public void onPresenterRefreshed( PresenterRefreshedEvent event ) {
        if ( this.presenter == event.getPresenter() )
            firePlaceChangedEvent();
    }

    public void onPresenterRevealed( PresenterRevealedEvent event ) {
        if ( this.presenter == event.getPresenter() )
            firePlaceRevealedEvent();
    }

    /**
     * Reveals the display. Subclasses should override this method to perform
     * any custom handling.
     */
    @Override
    protected void handleRequest( PlaceRequest request ) {
        T presenter = getPresenter();
        preparePresenter( request, presenter );
        presenter.revealDisplay();
    }

    /**
     * This method is called on matching place requests before the presenter is
     * revealed. Subclasses can perform any calls to the presenter to prepare it
     * for display based on the request.
     * 
     * @param request
     *            The request.
     * @param presenter
     *            The presenter.
     */
    protected abstract void preparePresenter( PlaceRequest request, T presenter );

    @Override
    protected PlaceRequest prepareRequest( PlaceRequest request ) {
        return prepareRequest( request, getPresenter() );
    }

    /**
     * This method is called when creating a {@link PlaceRequest} for this
     * place. It should add any state to the request as defined by the current
     * presenter.
     * 
     * <p>
     * If nothing is to be done, simply return the <code>request</code>
     * unchanged. Otherwise, call {@link PlaceRequest#with(String, String)} to
     * add parameters. Eg:
     * 
     * <pre>
     * return request.with( &quot;id&quot;, presenter.getId() );
     * </pre>
     * 
     * @param request
     *            The current request.
     * @param presenter
     *            The presenter.
     * @return The prepared place request.
     */
    protected abstract PlaceRequest prepareRequest( PlaceRequest request, T presenter );

    public static WidgetPresenter<?> getWidgetPresenter( Place place ) {
        Presenter presenter = getPresenter( place );
        if ( presenter instanceof WidgetPresenter )
            return ( WidgetPresenter<?> ) presenter;
        else
            return null;
    }

    /**
     * Returns the {@link Presenter} for the provided {@link Place} if the place
     * is an instance of {@link PresenterPlace} and the contained
     * {@link Presenter} is an instance of the <code>presenterClass</code>.
     * If not, <code>null</code> is returned.
     * 
     * @param <T>
     *            The {@link Presenter} type.
     * @param place
     *            The place.
     * @return The {@link Presenter}, if appropriate.
     */
    public static Presenter getPresenter( Place place ) {
        if ( place instanceof PresenterPlace ) {
            return ( ( PresenterPlace<?> ) place ).getPresenter();
        }
        return null;
    }

}

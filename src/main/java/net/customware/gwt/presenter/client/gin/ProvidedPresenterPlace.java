package net.customware.gwt.presenter.client.gin;

import com.google.inject.Provider;
import net.customware.gwt.presenter.client.Presenter;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PresenterPlace;

/**
 * A place that represents a {@link Presenter}, constructed by using a {@link Provider}.
 * This is the recommended method for creating places when using GIN, since it will help
 * avoid any circular dependencies when you have two Presenters that want to link to each
 * other. Implementing classes should simply inject a <code>Provider<X></code> rather than
 * the actual Presenter class directly, and pass it to the super() constructor.
 * <p/>
 * <p>Also, places should <b>not</b> be @Singleton values.</p>
 * <p/>
 * <p>Eg:
 * <p/>
 * <pre>
 * public class FooPlace extends ProvidedPresenterPlace<FooPresenter> {
 *      \@Inject
 *      public FooPlace( Provider<FooPresenter> presenter ) {
 *          super( presenter );
 *      }
 * }
 * </pre>
 *
 * @author David Peterson
 */
public abstract class ProvidedPresenterPlace<T extends Presenter> extends PresenterPlace<T> {
    private final Provider<T> presenter;

    public ProvidedPresenterPlace( Provider<T> presenter ) {
        this.presenter = presenter;
    }

    @Override
    public T getPresenter() {
        return presenter.get();
    }

    /**
     * Override this method to handle input values from the request that should
     * be passed to the presenter before being revealed. The default implementation
     * does nothing. For example:
     * <p/>
     * <code>
     * presenter.setId( request.getParam( "id" );
     * </code>
     *
     * @param request   The request.
     * @param presenter The presenter.
     */
    @Override
    protected void preparePresenter( PlaceRequest request, T presenter ) {
        // Do nothing
    }

    /**
     * Prepares the request based on the current state of the presenter. If the
     * presenter has state that you wish to be reflected in the History token,
     * add it to the request before returning. Eg:
     * <p/>
     * <pre>
     *   return request.with( "id", presenter.getId() );
     * </pre>
     * <p/>
     * The default implementation simply returns the request unchanged.
     *
     * @param request   The current request.
     * @param presenter The presenter.
     * @return The updated request.
     */
    @Override
    protected PlaceRequest prepareRequest( PlaceRequest request, T presenter ) {
        return request;
    }
}

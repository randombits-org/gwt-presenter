package net.customware.gwt.presenter.client.gin;

import static com.google.inject.name.Names.named;
import net.customware.gwt.presenter.client.Display;
import net.customware.gwt.presenter.client.Presenter;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.binder.GinLinkedBindingBuilder;
import com.google.inject.Singleton;

public abstract class AbstractPresenterModule extends AbstractGinModule {

    public AbstractPresenterModule() {
        super();
    }

    /**
     * Convenience method for binding a presenter as well as it's display.
     * 
     * @param <D>
     *            The display type.
     * @param presenter
     *            The presenter.
     * @param display
     *            The display type.
     * @param displayImpl
     *            The display implementation.
     */
    protected <D extends Display> void bindPresenter( Class<? extends Presenter> presenter, Class<D> display,
            Class<? extends D> displayImpl ) {
        bind( presenter ).in( Singleton.class );
        bindDisplay( display, displayImpl );
    }

    /**
     * Convenience method for binding a display implementation.
     * 
     * @param <D>
     * @param display
     * @param displayImpl
     */
    protected <D extends Display> void bindDisplay( Class<D> display, Class<? extends D> displayImpl ) {
        bind( display ).to( displayImpl );
    }

    /**
     * Convenience method for binding a type to a {@link Named} attribute. Use
     * it something like this:
     * 
     * <pre>
     * bindNamed( MyType.class, &quot;Foo&quot; ).to( MyImplementation.class );
     * </pre>
     * 
     * @param <T>
     *            The type.
     * @param type
     *            The type.
     * @param named
     *            The string to name with.
     * @return the binding builder.
     */
    protected <T> GinLinkedBindingBuilder<T> bindNamed( Class<T> type, String named ) {
        return bind( type ).annotatedWith( named( named ) );
    }

}
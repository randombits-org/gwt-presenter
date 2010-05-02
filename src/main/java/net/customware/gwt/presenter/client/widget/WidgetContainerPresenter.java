package net.customware.gwt.presenter.client.widget;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.PresenterRevealedEvent;
import net.customware.gwt.presenter.client.PresenterRevealedHandler;

import java.util.List;

/**
 * This class provides support for widgets that contain other widgets. It will
 * listen for {@link PresenterRevealedEvent}s and reveal itself if the source
 * was a direct child of this presenter.
 *
 * @author David Peterson
 */
public abstract class WidgetContainerPresenter<T extends WidgetContainerDisplay> extends WidgetPresenter<T> {

    private final List<WidgetPresenter<?>> presenters;

    private WidgetPresenter<?> currentPresenter;

    public WidgetContainerPresenter( T display, EventBus eventBus, WidgetPresenter<?>... presenters ) {
        super( display, eventBus );
        this.presenters = new java.util.ArrayList<WidgetPresenter<?>>();
        for ( WidgetPresenter<?> presenter : presenters ) {
            addPresenter( presenter );
        }
    }

    /**
     * Adds the list of presenters, if they are unbound. Bound presenters will
     * simply be ignored, and <code>false</code> will be returned if any were
     * ignored.
     *
     * @param presenters The list of presenters.
     * @return <code>false</code> if any of the presenters were bound, and
     *         therefor not added.
     */
    protected boolean addPresenters( WidgetPresenter<?>... presenters ) {
        boolean allSuccess = true;
        for ( WidgetPresenter<?> presenter : presenters ) {
            if ( !addPresenter( presenter ) )
                allSuccess = false;
        }
        return allSuccess;
    }

    /**
     * Adds the presenter, if the current presenter is unbound.
     *
     * @param presenter The presenter to add.
     * @return If added, returns <code>true</code>.
     */
    protected boolean addPresenter( WidgetPresenter<?> presenter ) {
        if ( !isBound() ) {
            presenters.add( presenter );
            return true;
        }
        return false;
    }

    @Override
    protected void onBind() {
        for ( WidgetPresenter<?> presenter : presenters ) {
            presenter.bind();
            display.addWidget( presenter.getDisplay().asWidget() );
        }

        // Handle revelation events from children
        registerHandler( eventBus.addHandler( PresenterRevealedEvent.getType(), new PresenterRevealedHandler() {
            public void onPresenterRevealed( PresenterRevealedEvent event ) {
                if ( event.getPresenter() instanceof WidgetPresenter<?> ) {
                    WidgetPresenter<?> presenter = (WidgetPresenter<?>) event.getPresenter();
                    if ( presenters.contains( presenter ) ) {
                        // Make this presenter the focus
                        setCurrentPresenter( presenter );
                        // Reveal ourselves so that the child will be revealed.
                        firePresenterRevealedEvent( false );
                    }
                }
            }
        } ) );
    }

    @Override
    protected void onUnbind() {
        currentPresenter = null;
        for ( WidgetPresenter<?> presenter : presenters ) {
            presenter.unbind();
            display.removeWidget( presenter.getDisplay().asWidget() );
        }
    }

    /**
     * @return The currently displaying presenter.
     */
    protected WidgetPresenter<?> getCurrentPresenter() {
        return currentPresenter;
    }

    /**
     * Sets the specified presenter to the be currently displaying presenter.
     * If the presenter has not been added ({@see #addPresenter(WidgetPresenter<?>)}),
     * it will not be set as the current presenter.
     *
     * @param presenter The presenter.
     * @return <code>true</code> if the presenter was successfully set as the current presenter.
     */
    protected boolean setCurrentPresenter( WidgetPresenter<?> presenter ) {
        if ( indexOf( presenter ) >= 0 ) {
            currentPresenter = presenter;
            display.showWidget( presenter.getDisplay().asWidget() );
            return true;
        }
        return false;
    }

    protected int indexOf( WidgetPresenter<?> presenter ) {
        return presenters.indexOf( presenter );
    }

    @Override
    protected void onRevealDisplay() {
        if ( currentPresenter == null && presenters.size() > 0 ) {
            // only reveal display of this presenter, event handler
            // will set this presenter to be the current presenter
            // and as a result the view of this presenter will be shown
            presenters.get( 0 ).revealDisplay();
        } else {
            currentPresenter.revealDisplay();
        }
    }
}

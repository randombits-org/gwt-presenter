package net.customware.gwt.presenter.client.widget;

import java.util.List;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.PresenterRevealedEvent;
import net.customware.gwt.presenter.client.PresenterRevealedHandler;

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
     * @param presenters
     *            The list of presenters.
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
     * @param presenter
     *            The presenter to add.
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
            display.addWidget( presenter.getDisplay().asWidget() );
        }
        if ( presenters.size() > 0 )
            display.showWidget( presenters.get( 0 ).getDisplay().asWidget() );

        eventBus.addHandler( PresenterRevealedEvent.getType(), new PresenterRevealedHandler() {
            public void onPresenterRevealed( PresenterRevealedEvent event ) {
                if ( presenters.contains( event.getPresenter() ) ) {
                    showPresenter( ( WidgetPresenter<?> ) event.getPresenter() );
                    revealDisplay( false );
                }
            }
        } );
    }

    @Override
    protected void onUnbind() {
        currentPresenter = null;
        for ( WidgetPresenter<?> presenter : presenters ) {
            display.removeWidget( presenter.getDisplay().asWidget() );
        }
    }

    protected WidgetPresenter<?> getCurrentPresenter() {
        return currentPresenter;
    }

    protected int indexOf( WidgetPresenter<?> presenter ) {
        return presenters.indexOf( presenter );
    }

    protected void showPresenter( WidgetPresenter<?> presenter ) {
        if ( indexOf( presenter ) >= 0 ) {
            currentPresenter = presenter;
            display.showWidget( presenter.getDisplay().asWidget() );
        }
    }

    public void refreshDisplay() {
        if ( currentPresenter != null )
            currentPresenter.refreshDisplay();
    }

    @Override
    public void revealDisplay() {
        if ( currentPresenter != null ) {
            currentPresenter.revealDisplay();
        }
        super.revealDisplay();
    }

}

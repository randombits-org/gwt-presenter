package net.customware.gwt.presenter.client.widget;

import com.google.gwt.user.client.ui.DeckPanel;

import net.customware.gwt.presenter.client.EventBus;

/**
 * A simple implementation of {@link WidgetContainerPresenter} that uses a
 * {@link DeckPanel} to contain the provided presenters. Only one will be
 * visible at a time.
 * 
 * @author David Peterson
 */
public abstract class DeckPresenter extends WidgetContainerPresenter<DeckDisplay> {

    public DeckPresenter( DeckDisplay display, EventBus eventBus, WidgetPresenter<?>... presenters ) {
        super( display, eventBus, presenters );
    }

}

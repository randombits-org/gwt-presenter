package net.customware.gwt.presenter.client.widget;

import com.google.gwt.user.client.ui.Widget;

import net.customware.gwt.presenter.client.BasicPresenter;
import net.customware.gwt.presenter.client.EventBus;

/**
 * Abstract super-class for {@link BasicPresenter}s that work with GWT
 * {@link Widget}s via {@link WidgetDisplay}s.
 * 
 * @author David Peterson
 * 
 * @param <D>
 *            The {@link WidgetDisplay} type.
 */
public abstract class WidgetPresenter<D extends WidgetDisplay> extends BasicPresenter<D> {
    public WidgetPresenter( D display, EventBus eventBus ) {
        super( display, eventBus );
    }
}

package net.customware.gwt.presenter.client.widget;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

public class DeckDisplay extends DeckPanel implements WidgetContainerDisplay {

    public DeckDisplay() {
    }

    public void addWidget( Widget widget ) {
        this.add( widget );
    }

    public void removeWidget( Widget widget ) {
        this.remove( widget );
    }

    public void showWidget( Widget widget ) {
        int index = this.getWidgetIndex( widget );
        if ( index >= 0 )
            this.showWidget( index );
    }

    public Widget asWidget() {
        return this;
    }
}

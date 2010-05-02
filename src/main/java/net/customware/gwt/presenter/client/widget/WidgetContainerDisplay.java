/**
 * 
 */
package net.customware.gwt.presenter.client.widget;

import com.google.gwt.user.client.ui.Widget;

/**
 * An extension of {@link WidgetDisplay} for widgets that can contain other widgets.
 * 
 * @author David Peterson
 */
public interface WidgetContainerDisplay extends WidgetDisplay {

    void addWidget( Widget widget );

    void removeWidget( Widget widget );

    void showWidget( Widget widget );

}
/**
 * 
 */
package net.customware.gwt.presenter.client;

public interface Display {
    /**
     * Indicate to the display that processing is being done.
     */
    void startProcessing();

    /**
     * Indicate to the display that processing has completed.
     */
    void stopProcessing();
}
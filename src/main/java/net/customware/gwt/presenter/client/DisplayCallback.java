package net.customware.gwt.presenter.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * An abstract base class implementation of {@link AsyncCallback} which will
 * automatically call the {@link Display#startProcessing()} and
 * {@link Display#stopProcessing()} methods to inform the display that work is
 * being done.
 * 
 * @author David Peterson
 * 
 * @param <T> The type to return.
 */
public abstract class DisplayCallback<T> implements AsyncCallback<T> {

    private final Display display;

    public DisplayCallback( Display display ) {
        this.display = display;
        display.startProcessing();
    }

    public void onSuccess( T value ) {
        try {
            handleSuccess( value );
        } finally {
            reset( null );
        }

    }

    /**
     * This method is called when the call returns successfully.
     * 
     * @param value The returned value.
     */
    protected abstract void handleSuccess( T value );

    public void onFailure( Throwable e ) {
        try {
            handleFailure( e );
        } finally {
            reset( e );
        }
    }

    /**
     * 
     * @param e
     */
    protected abstract void handleFailure( Throwable e );

    protected void reset( Throwable e ) {
        display.stopProcessing();
    }

}

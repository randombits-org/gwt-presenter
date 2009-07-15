package net.customware.gwt.presenter.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

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

    protected abstract void handleSuccess( T value );

    public void onFailure( Throwable e ) {
        try {
            handleFailure( e );
        } finally {
            reset( e );
        }
    }

    protected abstract void handleFailure( Throwable e );

    protected void reset( Throwable e ) {
        display.stopProcessing();
    }

}

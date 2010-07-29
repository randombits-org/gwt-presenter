package net.customware.gwt.presenter.client.place;

/**
 * Place managers work as an intermediary between the GWT {@link com.google.gwt.user.client.History} API
 * and {@link Place}s. It sets up event listener relationships to synchronize them.
 *
 * @author David Peterson
 */
public interface PlaceManager {

    /**
     * Fires an event for the current place.
     *
     * @return <code>false</code> if there was no current place to fire.
     */
    public boolean fireCurrentPlace();

    /**
     * Registers the place with the manager. This allows the place to be updated when
     * the browser's history token is updated. Only a single instance of a given concrete
     * Place implementation is registered
     *
     * @param place The place to register.
     * @return <code>true</code> if an instance of the Place class had not already been registered.
     */
    boolean registerPlace( Place place );

    /**
     * Deregisters the place from the manager. Any instance of a given concrete Place will cause the
     * deregistration - it doesn't have to be the original Place.
     *
     * @param place The place to deregister.
     * @return <code>true</code> if the place was registered and has been successfully deregistered.
     */
    boolean deregisterPlace( Place place );
}

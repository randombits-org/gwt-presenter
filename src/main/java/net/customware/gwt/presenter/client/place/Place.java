package net.customware.gwt.presenter.client.place;

public class Place {
    private final String id;

    public Place( Class<?> type ) {
        this( getPlaceName( type ) );
    }

    public Place( String value ) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o instanceof Place ) {
            Place place = ( Place ) o;
            return id.equals( place.id );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 16 * id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }

    /**
     * Returns a new request for this place.
     * 
     * @return The new {@link PlaceRequest}.
     */
    public PlaceRequest request() {
        return new PlaceRequest( this );
    }

    /**
     * A convenience method for calling {@link #request()}.with( name, id ).
     * 
     * @param name
     *            The parameter name.
     * @param id
     *            The parameter id.
     * @return
     */
    public PlaceRequest requestWith( String name, String value ) {
        return new PlaceRequest( this ).with( name, value );
    }

    private static String getPlaceName( Class<?> type ) {
        String id = getSimpleName( type );

        Class<?> supertype = type.getSuperclass();
        if ( supertype != null ) {
            String stId = getSimpleName( supertype );
            if ( id.endsWith( stId ) )
                id = id.substring( 0, id.length() - stId.length() );
        }

        return id;

    }

    private static String getSimpleName( Class<?> type ) {
        String id = type.getName();
        int lastDot = id.lastIndexOf( '.' );
        if ( lastDot > -1 )
            id = id.substring( lastDot + 1 );

        return id;
    }
}

package net.customware.gwt.presenter.client.place;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class PlaceRequest {

    private static final String PARAM_SEPARATOR = ";";

    private static final String PARAM_PATTERN = PARAM_SEPARATOR + "(?!" + PARAM_SEPARATOR + ")";

    private static final String PARAM_ESCAPE = PARAM_SEPARATOR + PARAM_SEPARATOR;

    private static final String VALUE_SEPARATOR = "=";

    private static final String VALUE_PATTERN = VALUE_SEPARATOR + "(?!" + VALUE_SEPARATOR + ")";

    private static final String VALUE_ESCAPE = VALUE_SEPARATOR + VALUE_SEPARATOR;

    private final Place place;

    private final Map<String, String> params;

    public PlaceRequest( Place id ) {
        this.place = id;
        this.params = null;
    }

    private PlaceRequest( PlaceRequest req, String name, String value ) {
        this.place = req.place;
        this.params = new java.util.HashMap<String, String>();
        if ( req.params != null )
            this.params.putAll( req.params );
        this.params.put( name, value );
    }

    public Place getPlace() {
        return place;
    }

    public Set<String> getParameterNames() {
        if ( params != null ) {
            return params.keySet();
        } else {
            return Collections.EMPTY_SET;
        }
    }

    public String getParameter( String key, String defaultValue ) {
        String value = null;

        if ( params != null )
            value = params.get( key );

        if ( value == null )
            value = defaultValue;
        return value;
    }

    /**
     * Returns a new instance of the request with the specified parameter name
     * and value. If a parameter with the same name was previously specified,
     * the new request contains the new value.
     * 
     * @param name
     *            The new parameter name.
     * @param value
     *            The new parameter value.
     * @return The new place request instance.
     */
    public PlaceRequest with( String name, String value ) {
        return new PlaceRequest( this, name, value );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof PlaceRequest ) {
            PlaceRequest req = ( PlaceRequest ) obj;
            if ( !req.equals( req.place ) )
                return false;

            if ( params == null )
                return req.params == null;
            else
                return params.equals( req.params );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 11 * ( place.hashCode() + ( params == null ? 0 : params.hashCode() ) );
    }

    /**
     * Outputs the place as a GWT history token.
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append( place.toString() );

        if ( params != null && params.size() > 0 ) {
            for ( Map.Entry<String, String> entry : params.entrySet() ) {
                out.append( PARAM_SEPARATOR );
                out.append( escape( entry.getKey() ) ).append( VALUE_SEPARATOR )
                        .append( escape( entry.getValue() ) );
            }
        }
        return out.toString();
    }

    /**
     * Parses a GWT history token into a {@link Place} instance.
     * 
     * @param token
     *            The token.
     * @return The place, or <code>null</code> if the token could not be
     *         parsed.
     */
    public static PlaceRequest fromString( String token ) throws PlaceParsingException {
        PlaceRequest req = null;

        int split = token.indexOf( PARAM_SEPARATOR );
        if ( split == 0 ) {
            throw new PlaceParsingException( "Place id is missing." );
        } else if ( split == -1 ) {
            req = new PlaceRequest( new Place( token ) );
        } else if ( split >= 0 ) {
            req = new PlaceRequest( new Place( token.substring( 0, split ) ) );
            String paramsChunk = token.substring( split + 1 );
            String[] paramTokens = paramsChunk.split( PARAM_PATTERN );
            for ( String paramToken : paramTokens ) {
                String[] param = paramToken.split( VALUE_PATTERN );
                if ( param.length != 2 )
                    throw new PlaceParsingException( "Bad parameter: Parameters require a single '"
                            + VALUE_SEPARATOR + "' between the key and value." );
                req = req.with( unescape( param[0] ), unescape( param[1] ) );
            }
        }

        return req;
    }

    private static String escape( String value ) {
        return value.replaceAll( PARAM_SEPARATOR, PARAM_ESCAPE ).replaceAll( VALUE_SEPARATOR, VALUE_ESCAPE );
    }

    private static String unescape( String value ) {
        return value.replaceAll( PARAM_ESCAPE, PARAM_SEPARATOR ).replaceAll( VALUE_ESCAPE, VALUE_SEPARATOR );
    }

}

package net.customware.gwt.presenter.client.place;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a 'request' for a place location. It includes the 'id'
 * of the place as well as any parameter values. It can convert from and to
 * String tokens for use with the GWT History.
 * <p/>
 * <p/>
 * Place request tokens are formatted like this:
 * <p/>
 * <code>#id(;key=value)*</code>
 * <p/>
 * <p/>
 * There is a mandatory 'id' value, followed by 0 or more key/value pairs,
 * separated by semi-colons (';'). A few examples follow:
 * <p/>
 * <ul>
 * <li> <code>#users</code> </li>
 * <li> <code>#user;name=j.blogs</code> </li>
 * <li> <code>#user-email;name=j.blogs;type=home</code> </li>
 * </ul>
 *
 * @author David Peterson
 */
public class PlaceRequest {

    private static final String PARAM_SEPARATOR = ";";

    private static final String PARAM_PATTERN = PARAM_SEPARATOR + "(?!" + PARAM_SEPARATOR + ")";

    private static final String PARAM_ESCAPE = PARAM_SEPARATOR + PARAM_SEPARATOR;

    private static final String VALUE_SEPARATOR = "=";

    private static final String VALUE_PATTERN = VALUE_SEPARATOR + "(?!" + VALUE_SEPARATOR + ")";

    private static final String VALUE_ESCAPE = VALUE_SEPARATOR + VALUE_SEPARATOR;

    private final String id;

    private final Map<String, String> params;

    public PlaceRequest( String id ) {
        this.id = id;
        this.params = null;
    }

    private PlaceRequest( PlaceRequest req, String name, String value ) {
        this.id = req.id;
        this.params = new java.util.HashMap<String, String>();
        if ( req.params != null )
            this.params.putAll( req.params );
        this.params.put( name, value );
    }

    public String getId() {
        return id;
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
     * @param name  The new parameter name.
     * @param value The new parameter value.
     * @return The new place request instance.
     */
    public PlaceRequest with( String name, String value ) {
        return new PlaceRequest( this, name, value );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof PlaceRequest ) {
            PlaceRequest req = (PlaceRequest) obj;
            if ( !id.equals( req.id ) )
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
        return 11 * ( id.hashCode() + ( params == null ? 0 : params.hashCode() ) );
    }

    /**
     * Outputs the place as a GWT history token.
     */
    public String toHistoryToken() {
        StringBuilder out = new StringBuilder();
        out.append( id.toString() );

        if ( params != null && params.size() > 0 ) {
            for ( Map.Entry<String, String> entry : params.entrySet() ) {
                out.append( PARAM_SEPARATOR );
                out.append( escape( entry.getKey() ) ).append( VALUE_SEPARATOR )
                    .append( escape( entry.getValue() ) );
            }
        }
        return out.toString();
    }

    @Override
    public String toString() {
        return toHistoryToken();
    }

    /**
     * Parses a GWT history token into a {@link String} instance.
     *
     * @param token The token.
     * @return The place, or <code>null</code> if the token could not be
     *         parsed.
     */
    public static PlaceRequest fromHistoryToken( String token ) throws PlaceParsingException {
        PlaceRequest req = null;

        int split = token.indexOf( PARAM_SEPARATOR );
        if ( split == 0 ) {
            throw new PlaceParsingException( "Place id is missing." );
        } else if ( split == -1 ) {
            req = new PlaceRequest( new String( token ) );
        } else if ( split >= 0 ) {
            req = new PlaceRequest( new String( token.substring( 0, split ) ) );
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

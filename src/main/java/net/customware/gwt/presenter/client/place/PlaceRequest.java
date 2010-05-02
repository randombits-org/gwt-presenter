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

    private final String name;

    private final Map<String, String> params;

    public PlaceRequest( String name ) {
        this.name = name;
        this.params = null;
    }

    private PlaceRequest( PlaceRequest req, String name, String value ) {
        this.name = req.name;
        this.params = new java.util.HashMap<String, String>();
        if ( req.params != null )
            this.params.putAll( req.params );
        if ( value != null )
            this.params.put( name, value );
    }

    public String getName() {
        return name;
    }

    public Set<String> getParameterNames() {
        if ( params != null ) {
            return params.keySet();
        } else {
            return Collections.emptySet();
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
            if ( !name.equals( req.name ) )
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
        return 11 * ( name.hashCode() + ( params == null ? 0 : params.hashCode() ) );
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append( "{" ).append( name );
        if ( params != null && params.size() > 0 ) {
            out.append( ": " );
            for ( Map.Entry<String, String> entry : params.entrySet() ) {
                out.append( entry.getKey() ).append( " = " ).append( entry.getValue() ).append( ";" );
            }
        }
        out.append( "}" );
        return out.toString();
    }
}

package net.customware.gwt.presenter.client.place;

import java.util.Set;

/**
 * Formats tokens from String values into PlaceRequest values and back again. This implementation
 * parses the token format like so:
 *
 * <pre>[name](;param=value)*</pre>
 */
public class ParameterTokenFormatter implements TokenFormatter {

    private static final String PARAM_SEPARATOR = ";";

    private static final String PARAM_PATTERN = PARAM_SEPARATOR + "(?!" + PARAM_SEPARATOR + ")";

    private static final String PARAM_ESCAPE = PARAM_SEPARATOR + PARAM_SEPARATOR;

    private static final String VALUE_SEPARATOR = "=";

    private static final String VALUE_PATTERN = VALUE_SEPARATOR + "(?!" + VALUE_SEPARATOR + ")";

    private static final String VALUE_ESCAPE = VALUE_SEPARATOR + VALUE_SEPARATOR;

    public ParameterTokenFormatter() {}

    public String toHistoryToken( PlaceRequest placeRequest ) {
        StringBuilder out = new StringBuilder();
        out.append( placeRequest.getName() );

        Set<String> params = placeRequest.getParameterNames();
        if ( params != null && params.size() > 0 ) {
            for ( String name : params ) {
                out.append( PARAM_SEPARATOR );
                out.append( escape( name ) ).append( VALUE_SEPARATOR )
                    .append( escape( placeRequest.getParameter( name, null )) );
            }
        }
        return out.toString();
    }

    public PlaceRequest toPlaceRequest( String token ) throws TokenFormatException {
        PlaceRequest req = null;

        int split = token.indexOf( PARAM_SEPARATOR );
        if ( split == 0 ) {
            throw new TokenFormatException( "Place name is missing." );
        } else if ( split == -1 ) {
            req = new PlaceRequest( token );
        } else if ( split >= 0 ) {
            req = new PlaceRequest( token.substring( 0, split ) );
            String paramsChunk = token.substring( split + 1 );
            String[] paramTokens = paramsChunk.split( PARAM_PATTERN );
            for ( String paramToken : paramTokens ) {
                String[] param = paramToken.split( VALUE_PATTERN );
                if ( param.length != 2 )
                    throw new TokenFormatException( "Bad parameter: Parameters require a single '"
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

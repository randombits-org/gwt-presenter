package net.customware.gwt.presenter.client.place;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParameterTokenFormatterTest {

    @Test
    public void renderParameterValue() {

        assertPlaceIsRenderedAs(new PlaceRequest("test").with("name", "value"), "test;name=value");
    }

    @Test
    public void renderEscapedParameterValue() {

        assertPlaceIsRenderedAs(new PlaceRequest("test").with("name", "value;value"), "test;name=value;;value");
    }

    @Test
    public void renderNoParameterValue() {
        
        assertPlaceIsRenderedAs(new PlaceRequest("test"), "test");
    }

    @Test
    public void renderEmptyParameterValue() {
    	
    	assertPlaceIsRenderedAs(new PlaceRequest("test").with("Description", "").with("SomethingElse", "hello"), "test;Description=;SomethingElse=hello");
    }
    
    @Test
    public void parseParameterValue() {

    	assertTokenIsParsedAs("test;name=value", new PlaceRequest("test").with("name", "value"));
    }

    @Test
    public void parseNoParameterValue() {
        
    	assertTokenIsParsedAs("test", new PlaceRequest("test"));
    }
    
    @Test
    public void parseEmptyParameterValue() {
    	
    	assertTokenIsParsedAs("test;Description=;SomethingElse=hello", new PlaceRequest("test").with("Description", "").with("SomethingElse", "hello"));
    }
    
    private void assertTokenIsParsedAs(String historyToken, PlaceRequest expectedPlaceRequest) {
    	
    	ParameterTokenFormatter formatter = new ParameterTokenFormatter();
    	PlaceRequest placeRequest = formatter.toPlaceRequest(historyToken);
    	
    	assertEquals("Token improperly parsed", expectedPlaceRequest, placeRequest);
    }

    private void assertPlaceIsRenderedAs(PlaceRequest placeRequest, String expectedHistoryToken) {

        ParameterTokenFormatter formatter = new ParameterTokenFormatter();

        assertEquals("PlaceRequest improperly rendered", expectedHistoryToken, formatter.toHistoryToken(placeRequest));
    }

}

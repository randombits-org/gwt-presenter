package net.customware.gwt.presenter.client.place;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParameterTokenFormatterTest {

    @Test
    public void parameterValue() {

        assertPlaceIsRenderedAs(new PlaceRequest("test").with("name", "value"), "test;name=value");
    }

    @Test
    public void escapedParameterValue() {

        assertPlaceIsRenderedAs(new PlaceRequest("test").with("name", "value;value"), "test;name=value;;value");
    }

    @Test
    public void noParameterValue() {
        
        assertPlaceIsRenderedAs(new PlaceRequest("test"), "test");
    }

    private void assertPlaceIsRenderedAs(PlaceRequest placeRequest, String expectedHistoryToken) {

        ParameterTokenFormatter formatter = new ParameterTokenFormatter();

        assertEquals("PlaceRequest improperly rendered", expectedHistoryToken, formatter.toHistoryToken(placeRequest));
    }

}

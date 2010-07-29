package net.customware.gwt.presenter.client.place;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PlaceRequestTest {
    
    @Test
    public void twoRequestsWithSamePlaceNameAreEqual() {
        
        String placeName = "SomePlace";
        
        PlaceRequest request = new PlaceRequest(placeName);
        PlaceRequest otherRequest = new PlaceRequest(placeName);
        
        assertEquals(request, otherRequest);
    }
    
    @Test
    public void twoRequestsWithDifferentPlaceNamesAreNotEqual() {
        
        PlaceRequest request = new PlaceRequest("SomePlace");
        PlaceRequest otherRequest = new PlaceRequest("SomeOtherPlace");
        
        assertFalse(request.equals(otherRequest));
    }
    
    @Test
    public void twoRequestsWithSamePlaceNameAndParametersAreEqual() {
        
        String placeName = "SomePlace";
        String parameterName = "parameter";
        String parameterValue = "value";
        
        PlaceRequest request = new PlaceRequest(placeName).with(parameterName, parameterValue);
        PlaceRequest otherRequest = new PlaceRequest(placeName).with(parameterName, parameterValue);
        
        assertEquals(request, otherRequest);
    }
    
    @Test
    public void twoRequestsWithSamePlaceNameButDifferentParametersAreNotEqual() {
        
        String placeName = "SomePlace";
        String parameterName = "parameter";
        String parameterValue = "value";
        String secondParameterValue = "anotherValue";
        
        PlaceRequest request = new PlaceRequest(placeName).with(parameterName, parameterValue);
        PlaceRequest otherRequest = new PlaceRequest(placeName).with(parameterName, secondParameterValue);
        
        assertFalse(request.equals(otherRequest));
    }

}

package WeatherApp;

import java.util.List;

public class LocationSearchException extends RuntimeException {
    private List matchingCities;
    public LocationSearchException(List matches){matchingCities = matches;}
    public List getMatchingCities(){return matchingCities;}
}

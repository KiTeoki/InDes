package WeatherApp;

import java.util.ArrayList;

public class LocationSearchException extends RuntimeException {
    private ArrayList<String> matchingCities;
    public LocationSearchException(ArrayList<String> matches){matchingCities = matches;}
    public ArrayList<String> getMatchingCities(){return matchingCities;}
}

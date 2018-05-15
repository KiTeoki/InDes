package WeatherApp;

public class LocationSearchException extends RuntimeException {
    public Boolean match = false;
    public LocationSearchException(Boolean matchFound){match=matchFound;}
}

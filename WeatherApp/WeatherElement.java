package WeatherApp;

import java.util.ArrayList;

public class WeatherElement {
    private Weather mWeather;
    private float mTemp;

    public WeatherElement(Weather initWeather, float initTemp){
        mWeather=initWeather;
        mTemp=initTemp;
    }


    public Weather getWeather() {
        return mWeather;
    }
    public double getTemp() {
        return (mTemp- 273.15);
    }
}

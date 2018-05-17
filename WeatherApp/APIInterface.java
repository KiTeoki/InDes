package WeatherApp;
//JSON compatibility imports

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

//File/URL reader imports
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;

public class APIInterface {
    //API token, current location, cached version of the cityList array
    private final static String token = "177fdc70ad38a8d70ae1b7ef94eb29b2";
    private int location = -1;
    private JSONArray cityList;

    public APIInterface() {
        try {
            //open file, tokenise, interpret as JSONArray, close file
            FileReader cityListFile = new FileReader("WeatherApp/city.list.min.json");
            JSONTokener cityListTokens = new JSONTokener(cityListFile);
            cityList = new JSONArray(cityListTokens);
            cityListFile.close();
        } catch (IOException e) {
            System.out.println("City list file not found");
        }
    }

    //return list of all available cities
    public ArrayList<String> getCityList(){
        ArrayList<String> cities = new ArrayList<>();
        for (int i=0; i<cityList.length(); i++) {
            JSONObject curCity = cityList.getJSONObject(i);
            cities.add(curCity.getString("name"));
        }
        return cities;
    }

    //Look for location 'loc' in the valid locations file and return all matching IDs
    private ArrayList<Integer> searchFile(String loc) {
        //search for Location as lowercase
        String sfLoc = loc.toLowerCase();
        //Initialise array of locations that match input (may be multiple if not whole location name given)
        ArrayList<Integer> matchingLocations = new ArrayList<>();
        for (int i=0; i<cityList.length(); i++) {
            JSONObject curCity = cityList.getJSONObject(i);
            if (curCity.getString("name").toLowerCase().equals(sfLoc)){
                //Exact location so can stop searching here
                matchingLocations = new ArrayList<>();
                matchingLocations.add(Integer.valueOf(curCity.getInt("id")));
                break;
            } else if (curCity.getString("name").toLowerCase().contains(sfLoc)){
                //Close match so add in case no exact match found
                matchingLocations.add(Integer.valueOf(curCity.getInt("id")));
            }
        }
        return matchingLocations;
    }

    //Lookup in file, set id to number found. Throw error if not found, or if not specific enough.
    public void setLocation(String newLoc) throws LocationSearchException {
        ArrayList<Integer> matchingLocations = this.searchFile(newLoc);
        //Validation: should have only 1 result
        if (matchingLocations.size()==0){
            throw new LocationSearchException(false);
        } else if (matchingLocations.size()>1){
            System.out.println(matchingLocations);
            throw new LocationSearchException(true);
        } else {
            location = Integer.valueOf(matchingLocations.get(0));
        }
    }

    //Take OpenWeatherAPI code and return Weather ENUM
    private Weather interpretCode(int code){
        if (code<300) return Weather.THUNDER;
        else if (code<600) return Weather.RAINY;
        else if (code<700) return Weather.SNOWY;
        else if ((code < 750) && (code != 731)) return Weather.CLOUD;
        else if (code<800) return Weather.WINDY;
        else if (code==800) return Weather.SUNNY;
        else if (code<803) return Weather.SUNCLOUD;
        else return Weather.CLOUD;
    }

    //Get 5 day forecast, split into daily lists of 3-hourly weather/temp forecasts
    public ArrayList<ArrayList<WeatherElement>> getWeather() throws IOException{
        URL apiCall = new URL("http://api.openweathermap.org/data/2.5/forecast?id=" + location + "&APPID=" + token);
        URLConnection conn = apiCall.openConnection();
        BufferedReader b = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONTokener forecastRaw = new JSONTokener(b);
        JSONArray forecastData = new JSONObject(forecastRaw).getJSONArray("list");
        //Need to iterate through to get 5 day forecast. Split by day. Contains weather/temperature pairs
        ArrayList<ArrayList<WeatherElement>> forecast = new ArrayList<>();
        String currentDate = "";
        ArrayList<WeatherElement> dailyWeather = null;
        for (int i=0; i<forecastData.length(); i++) {
            JSONObject hourForecast = forecastData.getJSONObject(i);
            //check if this hour is on a different date
            if (!(hourForecast.getString("dt_txt").split(" ")[0].equals(currentDate))) {
                //If data to add then add it.
                if (!(currentDate.equals(""))) {
                    forecast.add(dailyWeather);
                }
                //Reset daily vars
                currentDate=hourForecast.getString("dt_txt").split(" ")[0];
                dailyWeather = new ArrayList<>();
            }
            Weather dWeather = interpretCode(hourForecast.getJSONArray("weather").getJSONObject(0).getInt("id"));
            float dTemp = hourForecast.getJSONObject("main").getFloat("temp");
            dailyWeather.add(new WeatherElement(dWeather, dTemp));
        }
        //Add last day
        forecast.add(dailyWeather);
        return forecast;
    }

    public static void main(String[] args){
        APIInterface t = new APIInterface();
        //Test api call used: Cambridge
        //http://api.openweathermap.org/data/2.5/forecast?id=6240770&APPID=177fdc70ad38a8d70ae1b7ef94eb29b2

        //System.out.print(t.getCityList());

        /*t.setLocation("Cambridge");
        try {
            ArrayList<ArrayList<WeatherElement>> f = t.getWeather();
            int i=0;
            for (ArrayList dailyWeather : f) {
                System.out.println("\n Today + "+ i++ +" days");
                for (int j=0;j<dailyWeather.size(); j++){
                    WeatherElement w = (WeatherElement) dailyWeather.get(j);
                    System.out.print(w.getWeather() + " | " + w.getTemp()+" , ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}

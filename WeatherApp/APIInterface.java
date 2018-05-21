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
        //Load JSON of cities
        try {
            //open file, tokenise, interpret as JSONArray, close file
            FileReader cityListFile = new FileReader("InDes/WeatherApp/city.list.min.json");
            JSONTokener cityListTokens = new JSONTokener(cityListFile);
            cityList = new JSONArray(cityListTokens);
            cityListFile.close();
        } catch (IOException e) {
            System.out.println("City list file not found");
        }
        //Read current location from file
        File currentCityFile = new File("InDes/WeatherApp/config.txt");
        try {
            FileReader cityFileReader = new FileReader(currentCityFile);
            BufferedReader cityIDReader = new BufferedReader(cityFileReader);
            this.location=Integer.valueOf(cityIDReader.readLine());
            cityIDReader.close();
            cityFileReader.close();
        } catch (IOException e) {
            //could not load location file, make new file and set location (default london)
            try {
                currentCityFile.createNewFile();
                FileWriter w = new FileWriter(currentCityFile);
                w.write("2643743");
                w.close();
            } catch (IOException e2) {
                //Cannot create new file as already exists, which would mean the first test passed.
                //Case cannot occur but must be caught.
            } finally {
                //Make sure location does get set
                this.location = 2643743;
            }
        }
    }

    //return list of all available cities
    public ArrayList<String> getCityList(){
        ArrayList<String> cities = new ArrayList<>();
        for (int i=0; i<cityList.length(); i++) {
            JSONObject curCity = cityList.getJSONObject(i);
            if (curCity.getString("country").equals("GB"))
                cities.add(curCity.getString("name"));
        }
        return cities;
    }

    //Look for location 'loc' in the valid locations file
    //Either returns the matching ID or throws an exception with list of similar city names
    private int searchFile(String loc) throws LocationSearchException{
        //search for Location as lowercase
        String sfLoc = loc.toLowerCase();
        //Initialise array of locations that match input (may be multiple if not whole location name given)
        ArrayList<String> matchingLocations = new ArrayList<>();
        int perfectMatch = -1;
        for (int i=0; i<cityList.length(); i++) {
            JSONObject curCity = cityList.getJSONObject(i);
            //Filter by GB only
            if (curCity.getString("country").equals("GB")) {
                if (curCity.getString("name").toLowerCase().equals(sfLoc)) {
                    //Exact location so can stop searching here
                    perfectMatch = curCity.getInt("id");
                    break;
                } else if (curCity.getString("name").toLowerCase().contains(sfLoc)) {
                    //Close match so add in case no exact match found
                    matchingLocations.add(curCity.getString("name"));
                }
            }
        }
        if (perfectMatch!=-1) return perfectMatch;
        //If no perfect match (multiple partial matches / no matches) throw exception with list of partial matches
        else throw new LocationSearchException(matchingLocations);
    }

    //Lookup in file, set id to number found. Pass error up if not found, or if not specific enough.
    public void setLocation(String newLoc) throws LocationSearchException {
        //get ID
        int matchID = this.searchFile(newLoc);
        try {
            //Update config.txt with new location
            File currentCityFile = new File("InDes/WeatherApp/config.txt");
            //make file if it does not exist
            currentCityFile.createNewFile();
            FileWriter w = new FileWriter(currentCityFile);
            w.write(String.valueOf(matchID));
            w.close();
        } catch (IOException e) {
            //could not write to file
        } finally {
            location = Integer.valueOf(matchID);
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
        //See result of cityList
        //System.out.print(t.getCityList());

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
        } catch (LocationSearchException e) {
            System.out.println(e.getMatchingCities());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

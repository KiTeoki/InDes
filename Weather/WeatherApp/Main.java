package WeatherApp;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        try {
            APIInterface apii = new APIInterface();
            apii.setLocation("london");
            ArrayList<ArrayList<WeatherElement>> forecast = apii.getWeather();
            WeatherElement now = forecast.get(0).get(0);
            Clothes items[] =WeatherApp.ClothesPickingLogic.whatClothes(now);
            System.out.println(items[0]+" "+ items[1] +" " + items[2]);
            JFrame base = GUIBasic.loadhomeScreen();
            base.add(GUIHome.loadHome());
            base.setVisible(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

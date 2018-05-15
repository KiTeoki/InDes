package WeatherApp;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String args[]) {
        try {
            JFrame base = GUIBasic.loadhomeScreen();
            base.add(GUIHome.loadHome());
            base.setVisible(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

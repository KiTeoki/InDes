package WeatherApp;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        try {
            //Create global APII. Passed to load[relevant screen]. Uses location in file.
            APIInterface apii = new APIInterface();
            JFrame base = GUIBasic.loadhomeScreen();
            base.add(GUIHome.loadHome(0,apii));
            base.setVisible(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

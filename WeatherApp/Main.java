package WeatherApp;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        try {

            JFrame base = GUIBasic.loadhomeScreen();
            base.add(GUIHome.loadHome(1,"cambridge"));
            base.setVisible(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

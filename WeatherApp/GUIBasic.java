package WeatherApp;

import javax.swing.*;
import java.awt.*;

public class GUIBasic extends JFrame {
    //A Base class that will be the background for out app
    //it has a static frame as the base so when it can be edited throughout the application
    static JFrame frame = new JFrame("My First Weather App");
    public static JFrame loadhomeScreen(){
        //screen is resizeable but set to the reseltion of a tablet/phone
        frame.setSize(300,450);
        //set to exit the program when the frame is closed and gived a background color
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#8bb1ed"));
        //passes frame to class attempting to access it (so it can be added too)
        return frame;
    }


}

package WeatherApp;

import javax.swing.*;
import java.awt.*;

public class GUIsettings {
    public static JPanel loadSettings() {
        JPanel settingpanel = new JPanel();
        settingpanel.setLayout(new GridLayout(3,1));
        return settingpanel;
    }
}

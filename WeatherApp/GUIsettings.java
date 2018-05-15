package WeatherApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIsettings {
    public static JPanel loadSettings() {
        JFrame base =GUIBasic.loadhomeScreen();
        JPanel settingpanel = new JPanel();
        settingpanel.setLayout(new GridLayout(3,1));
        settingpanel.setBackground(Color.cyan);


        //location settings bar goes here
        JPanel locationPan = new JPanel();
        locationPan.setBackground(Color.cyan);
        JLabel locLabel = new JLabel("change the location");

        JPanel backBar = new JPanel();
        backBar.setBackground(Color.cyan);
        backBar.setLayout(new GridLayout(1,5));

        JPanel[] backPanelHolder = new JPanel[5];

        for(int m = 0; m < 5; m++) {
            backPanelHolder[m] = new JPanel();
            backPanelHolder[m].setBackground(Color.cyan);
            backBar.add(backPanelHolder[m]);
        }

        JButton backButton = new JButton();
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                base.remove(settingpanel);
                base.add(GUIHome.loadHome());
                base.invalidate();
                base.revalidate();
            }
        });
        backPanelHolder[0].add(backButton);

        locationPan.add(BorderLayout.NORTH,backBar);
        locationPan.add(BorderLayout.CENTER,locLabel);

        //pin panel
        JPanel pinPanel = new JPanel();
        pinPanel.setBackground(Color.cyan);
        JLabel logoLabel = new JLabel("change your pin");
        pinPanel.add(logoLabel);

        settingpanel.add(locationPan);
        settingpanel.add(pinPanel);

        return settingpanel;
    }
}

package WeatherApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIHome {
    public static JPanel loadHome(){
        JFrame base =GUIBasic.loadhomeScreen();
        JPanel homepanel = new JPanel();
        homepanel.setLayout(new GridLayout(3,1));
<<<<<<< HEAD

        //tempriture bar goes here
=======
        //Temperature bar goes here
>>>>>>> Tudor
        JPanel tempPan = new JPanel();
        tempPan.setBackground(Color.cyan);
        JLabel tempLabel = new JLabel("Wow this is the Temperature", SwingConstants.CENTER);
        JPanel settingsBar = new JPanel();
        settingsBar.setBackground(Color.cyan);
        settingsBar.setLayout(new GridLayout(1,5));
        JPanel[] settingspanelHolder = new JPanel[5];

        for(int m = 0; m < 5; m++) {
                settingspanelHolder[m] = new JPanel();
                settingspanelHolder[m].setBackground(Color.cyan);
                settingsBar.add(settingspanelHolder[m]);
        }

        JButton settings = new JButton();
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                base.remove(homepanel);
                base.add(GUIsettings.loadSettings());
                base.invalidate();
                base.revalidate();
            }
        });
        settingspanelHolder[4].add(settings);

        tempPan.setLayout(new GridLayout(2,1));
        //the top block contains the settingsBar and...
        tempPan.add(settingsBar);
        //the bottom, the label
        tempPan.add(BorderLayout.CENTER, tempLabel);

        //logo pannel
        JPanel logoPan = new JPanel();
        logoPan.setBackground(Color.cyan);
        JLabel logoLabel = new JLabel("Wow this is the sun its hot");
        logoPan.add(logoLabel);

        //this will be the clothes bar
        JPanel clothesPan = new JPanel();
        clothesPan.setBackground(Color.cyan);
        JLabel clothesLabel = new JLabel("wow flipflops");
        clothesPan.add(clothesLabel);


        homepanel.add(tempPan);
        homepanel.add(logoPan);
        homepanel.add(clothesPan);
        return homepanel;

    }

}

package WeatherApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

<<<<<<< HEAD
public class GUIsettings
{
    public static JPanel loadSettings()
    {
        JFrame base = GUIBasic.loadhomeScreen();
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3,1));
        settingsPanel.setBackground(Color.cyan);
=======
public class GUIsettings {
    public static JPanel loadSettings() throws IOException {
        JFrame base =GUIBasic.loadhomeScreen();
        JPanel settingpanel = new JPanel();
        settingpanel.setLayout(new GridLayout(3,1));
        settingpanel.setBackground(Color.cyan);

>>>>>>> AlicesNewBranch

        //location settings bar goes here
        JPanel locationPan = new JPanel();
        locationPan.setBackground(Color.cyan);
        JLabel locLabel = new JLabel("change the location");

        String[] cityStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

        JComboBox cityList = new JComboBox(cityStrings);
//        cityList.setSelectedIndex(4);
        cityList.addActionListener(new CitySelectionListener());

        locationPan.add(cityList, BorderLayout.CENTER);

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
<<<<<<< HEAD
                base.remove(settingsPanel);
                base.add(GUIHome.loadHome());
                base.invalidate();
                base.revalidate();
=======
                try {
                    base.remove(settingpanel);
                    base.add(GUIHome.loadHome());
                    base.invalidate();
                    base.revalidate();
                }catch (IOException r){
                    r.printStackTrace();
                }
>>>>>>> AlicesNewBranch
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

        settingsPanel.add(locationPan);
        settingsPanel.add(pinPanel);

        return settingsPanel;
    }
}

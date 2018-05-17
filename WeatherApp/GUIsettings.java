package WeatherApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUIsettings
{
    public static JPanel loadSettings(int day, String location) throws IOException
    {
        JFrame base = GUIBasic.loadhomeScreen();
        JPanel settingsPanel = new JPanel();
        // TODO: Add one more panel to the layout if we get the PIN working.
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBackground(Color.cyan);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.red);

        JLabel locLabel = new JLabel("Enter your location.");
        mainPanel.add(BorderLayout.NORTH, locLabel);

        JPanel dropdownPanel = new JPanel();
        /* TODO: Get a method working which creates a String[] of all the available
           locations. */
        String[] cityStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

        JComboBox cityList = new JComboBox(cityStrings);
//        cityList.setSelectedIndex(4);
        cityList.addActionListener(e -> {
            /* TODO: Replace this with a call to a method which changes the location
               for which weather is being displayed. */
            System.out.println("Foo foo.");
        });
        dropdownPanel.add(cityList, BorderLayout.SOUTH);

        mainPanel.add(dropdownPanel);
        // TODO: You can't see this JPanel I don't think? What is it?
        JPanel goBackPanel = new JPanel();
        goBackPanel.setBackground(Color.yellow);
        goBackPanel.setLayout(new GridLayout(1,5));

        JPanel[] backPanelHolder = new JPanel[5];

        for(int m = 0; m < 5; m++) {
            backPanelHolder[m] = new JPanel();
            backPanelHolder[m].setBackground(Color.yellow);
            goBackPanel.add(backPanelHolder[m]);
        }

        JButton backButton = new JButton();
        backButton.addActionListener(e -> {
            try {
                base.remove(settingsPanel);
                base.add(GUIHome.loadHome(day,location));
                base.invalidate();
                base.revalidate();
            }catch(IOException r){
                r.printStackTrace();
            }


        });
        backPanelHolder[0].add(backButton);

//        mainPanel.add(BorderLayout.NORTH,goBackPanel);

        // TODO: Put the PIN back in once core functionality is working.
        /*//pin panel
        JPanel pinPanel = new JPanel();
        pinPanel.setBackground(Color.cyan);
        JLabel logoLabel = new JLabel("change your pin");
        pinPanel.add(logoLabel);

        settingsPanel.add(pinPanel);*/

        settingsPanel.add(goBackPanel, BorderLayout.NORTH);
        settingsPanel.add(mainPanel, BorderLayout.CENTER);

//        settingsPanel.add(dropdownPanel, BorderLayout.SOUTH);

        return settingsPanel;
    }
}

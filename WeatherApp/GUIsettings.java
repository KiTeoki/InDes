package WeatherApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

public class GUIsettings
{
    //keep as private so can be updated by listener
    private static JComboBox cityList;
    private static JLabel locationInfo;

    //Called by listener: changes location if possible, otherwise changes available locations
    private static void updateComboBox(ItemEvent e, APIInterface apii){
        //Two will be thrown in sequence: deselect, select. Only want the select listener, which has stateChange==1
        if (1==e.getStateChange()) {
            try {
                //On location click, attempt to set the current location.
                apii.setLocation((String) cityList.getSelectedItem());
                locationInfo.setText("Location set to " + cityList.getSelectedItem() + " successfully.");
            } catch (LocationSearchException cities) {
                //If this fails then update the list of options
                cityList.removeAllItems();
                ArrayList<String> partMatches = cities.getMatchingCities();
                if (0!=partMatches.size()) {
                    for (String partMatch : partMatches) {
                        cityList.addItem(partMatch);
                    }
                    locationInfo.setText(partMatches.size() + " matching locations found. Please select from new list.");
                } else {
                    //No matching cities, reset menu
                    ArrayList<String> allCities = apii.getCityList();
                    for (String city : allCities) {
                        cityList.addItem(city);
                    }
                    locationInfo.setText("No matches found. Please select from list.");
                }
            }
        }
    }

    public static JPanel loadSettings(int day, APIInterface apii) throws IOException
    {
        JFrame base = GUIBasic.loadhomeScreen();

        // This contains goBackPanel and mainPanel.
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBackground(Color.decode("#8bb1ed"));

        JPanel goBackPanel = new JPanel();
        goBackPanel.setBackground(Color.decode("#8bb1ed"));
        goBackPanel.setLayout(new BorderLayout());

        // This contains dropdownPanel and two empty panels to center dropdown
        // panel.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,1));
        mainPanel.setBackground(Color.decode("#8bb1ed"));

        JPanel dropdownPanel = new JPanel();
        //Information/instructional label
        locationInfo = new JLabel("Please select new location from list");
        dropdownPanel.add(locationInfo, BorderLayout.NORTH);
        //Combo box: get all available cities, make editable combo box with them.
        ArrayList<String> cityStrings = apii.getCityList();
        cityList = new JComboBox(cityStrings.toArray());
        cityList.setEditable(true);
        //Update contents of box on update
        cityList.addItemListener(e -> updateComboBox(e,apii));
        //Set sensible width, prevents going off side of window
        cityList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXX");
        dropdownPanel.add(cityList, BorderLayout.CENTER);
        dropdownPanel.setBackground(Color.decode("#8bb1ed"));

        JPanel lowerPadding = new JPanel();
        lowerPadding.setBackground(Color.decode("#8bb1ed"));

        JPanel upperPadding = new JPanel();
        upperPadding.setBackground(Color.decode("#8bb1ed"));

        mainPanel.add(upperPadding);
        mainPanel.add(dropdownPanel);
        mainPanel.add(lowerPadding);

        JButton backButton = new JButton(new ImageIcon(((new ImageIcon("Res/HomeButton.png")).getImage()).getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH)));

        //removes back ground and border of button so its just image
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);

        backButton.addActionListener(e -> {
            try {
                base.remove(settingsPanel);
                base.add(GUIHome.loadHome(day, apii));
                base.invalidate();
                base.revalidate();
            }catch(IOException r){
                r.printStackTrace();
            }
        });

        goBackPanel.add(BorderLayout.WEST, backButton);

        settingsPanel.add(goBackPanel, BorderLayout.NORTH);
        settingsPanel.add(mainPanel, BorderLayout.CENTER);

        return settingsPanel;
    }
}

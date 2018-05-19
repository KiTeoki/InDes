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
        JPanel settingsPanel = new JPanel();
        // TODO: Add one more panel to the layout if we get the PIN working.
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBackground(Color.cyan);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.cyan);

        JLabel locLabel = new JLabel("Enter your location.");
        mainPanel.add(BorderLayout.NORTH, locLabel);

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

        mainPanel.add(dropdownPanel);

        JPanel goBackPanel = new JPanel();
        goBackPanel.setBackground(Color.cyan);
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
                base.add(GUIHome.loadHome(day, apii));
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

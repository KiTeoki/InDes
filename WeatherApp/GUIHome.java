import javax.swing.*;
import java.awt.*;

public class GUIHome {
    public static void loadHome(){
        JFrame base =GUIBasic.loadhomeScreen();

        //tempriture bar goes here
        JPanel tempPan = new JPanel();
        tempPan.setBackground(Color.cyan);
        JLabel tempLable = new JLabel("Wow this is the Temprture");
        JPanel settingsBar = new JPanel();
        settingsBar.setBackground(Color.cyan);
        settingsBar.setLayout(new GridLayout(1,5));
        JPanel[] settingspanelHolder = new JPanel[5];

        for(int m = 0; m < 5; m++) {
                settingspanelHolder[m] = new JPanel();
                settingsBar.add(settingspanelHolder[m]);
        }

        JButton settings = new JButton("*");
        settingspanelHolder[4].add(settings);

        tempPan.add(BorderLayout.NORTH,settingsBar);
        tempPan.add(BorderLayout.CENTER,tempLable);

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


        base.getContentPane().add(tempPan);
        base.getContentPane().add(logoPan);
        base.getContentPane().add(clothesPan);
        base.setVisible(true);

    }

}

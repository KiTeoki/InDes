import javax.swing.*;
import java.awt.*;

public class GUIHome {
    public static void loadHome(){
        JFrame base =GUIBasic.loadhomeScreen();

        //tempriture bar goes here
        JPanel tempPan = new JPanel();
        JLabel tempLable = new JLabel("Wow this is the Temprture");
        tempPan.add(tempLable);

        //logo pannel
        JPanel logoPan = new JPanel();
        JLabel logoLabel = new JLabel("Wow this is the sun its hot");
        logoPan.add(logoLabel);

        //this will be the clothes bar
        JPanel clothesPan = new JPanel();
        JLabel clothesLabel = new JLabel("wow flipflops");
        clothesPan.add(clothesLabel);

        base.getContentPane().add(BorderLayout.SOUTH, clothesPan);
        base.getContentPane().add(BorderLayout.NORTH, tempPan);
        base.getContentPane().add(BorderLayout.CENTER, logoPan);
        base.setVisible(true);

    }

}

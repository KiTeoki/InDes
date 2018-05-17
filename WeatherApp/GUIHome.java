package WeatherApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GUIHome {

    //Method to display an image in the Jpanel jp, following graphics url, with a division scale against window width
    public static void DisplayImage(JPanel jp, String url, int scale) {
        JLabel jl = new JLabel();
        ImageIcon icon = new javax.swing.ImageIcon(url);
        Image temp = icon.getImage();

        //Scale image to the width of window screen divided by scale. Scale height to preserve aspect ratio of image.
        ImageIcon scaledicon = new ImageIcon(temp.getScaledInstance((int) GUIBasic.frame.getWidth() / scale, (int) Math.round((icon.getIconHeight()*GUIBasic.frame.getWidth())/(scale*icon.getIconWidth())), Image.SCALE_SMOOTH));

        jl.setIcon(scaledicon);
        jl.setHorizontalAlignment(JLabel.CENTER);
        jp.add(BorderLayout.CENTER,jl);
    }

    public static void loadfont() {

    }

    //Method to read in font and print file
    public static void printtext(JPanel panel, String n, Font f) {
        JLabel label = new JLabel();
        label.setFont(f);
        label.setText(n);
        panel.add(BorderLayout.CENTER, label);
    }


    public static JPanel loadHome() throws IOException {
        JFrame base =GUIBasic.loadhomeScreen();
        JPanel homepanel = new JPanel();
        homepanel.setLayout(new GridLayout(3,1));

        //To load font
        InputStream is = GUIHome.class.getResourceAsStream("font.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(16f);
        //Example
        printtext(homepanel, "This is a test", font);

        //tempriture bar goes here
        JPanel tempPan = new JPanel();
        tempPan.setBackground(Color.cyan);
        DisplayImage(tempPan, "Res/therm.png", 2);
        JPanel settingsBar = new JPanel();
        settingsBar.setBackground(Color.cyan);
        settingsBar.setLayout(new GridLayout(1,5));
        JPanel[] settingspanelHolder = new JPanel[5];

        for(int m = 0; m < 5; m++) {
            settingspanelHolder[m] = new JPanel();
            settingspanelHolder[m].setBackground(Color.cyan);
            settingsBar.add(settingspanelHolder[m]);
        }


        JButton settings = new JButton(new ImageIcon(((new ImageIcon("Res/SettingsButon.png")).getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));

        //removes back ground and border of button so its just image
        settings.setBorder(BorderFactory.createEmptyBorder());
        settings.setContentAreaFilled(false);

        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    base.remove(homepanel);
                    base.add(GUIsettings.loadSettings());
                    base.invalidate();
                    base.revalidate();
                }catch (IOException r){
                    r.printStackTrace();
                }
            }
        });
        settingspanelHolder[4].add(settings);

        tempPan.setLayout(new GridLayout(2,1));
        //the top block contains the settingsBar and...
        tempPan.add(settingsBar);


        //logo pannel
        JPanel logoPan = new JPanel();
        logoPan.setBackground(Color.cyan);
        DisplayImage(logoPan, "Res/Sunny.png", 2);

        //this will be the clothes bar
        JPanel clothesPan = new JPanel();
        clothesPan.setBackground(Color.cyan);
        DisplayImage(clothesPan, "Res/flipflops.png", 4);
        DisplayImage(clothesPan, "Res/tshirt.png", 4);

        homepanel.add(tempPan);
        homepanel.add(logoPan);
        homepanel.add(clothesPan);
        return homepanel;

    }

}

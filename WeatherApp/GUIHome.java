package WeatherApp;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    public static String weatherEnumToFile(Weather weather){
        switch(weather){
            case SUNNY:
                return "Res/Sunny.png";
        }
        return "Res/Sunny.png";
    }
    public static String tempToFile(double temp){
        if (temp<-5.0){
            return "Res/therm14.png";
        }else if (temp <0){
            return "Res/therm13.png";
        }else if (temp <5){
            return "Res/therm12.png";
        }else if (temp <10){
            return "Res/therm11.png";
        }else if (temp<13){
            return "Res/therm10.png";
        }else if (temp < 15){
            return "Res/therm9.png";
        }else if (temp<18){
            return "Res/therm8.png";
        }else if (temp < 20){
            return "Res/therm7.png";
        }else if(temp<23){
            return "Res/therm6.png";
        }else if (temp<25){
            return "Res/therm5.png";
        }else if(temp<28){
            return "Res/therm4.png";
        }else if (temp<30){
            return "Res/therm3.png";
        }else if (temp <35){
            return "Res/therm2.png";
        }else{
            return "Res/therm1.png";
        }
    }

    public static JPanel loadHome(int day,String Location) throws IOException {

        // stuff for getting current weather
        APIInterface apii = new APIInterface();
        apii.setLocation(Location);
        ArrayList<ArrayList<WeatherElement>> forecast = apii.getWeather();
        //defults to current time
        WeatherElement weatherForSelectedDay =forecast.get(0).get(0);
        if(day!=0) {
            //changes to midday for future days
            weatherForSelectedDay = forecast.get(day).get(3);
        }

        Clothes items[] =WeatherApp.ClothesPickingLogic.whatClothes(weatherForSelectedDay);
        double temp = weatherForSelectedDay.getTemp();

        JFrame base =GUIBasic.loadhomeScreen();
        JPanel homepanel = new JPanel();
        homepanel.setLayout(new GridLayout(3,1));

        //tempriture bar goes here
        JPanel tempPan = new JPanel();
        tempPan.setBackground(Color.cyan);
        DisplayImage(tempPan, tempToFile(temp), 2);
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

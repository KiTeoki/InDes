package WeatherApp;

import java.io.FileInputStream;
import java.io.InputStream;
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
    private static void DisplayImage(JPanel jp, String url, double scale) {
        JLabel jl = new JLabel();
        ImageIcon icon = new javax.swing.ImageIcon(url);
        Image temp = icon.getImage();

        //Scale image to the width of window screen divided by scale. Scale height to preserve aspect ratio of image.
        ImageIcon scaledicon = new ImageIcon(temp.getScaledInstance( (int)Math.round(GUIBasic.frame.getWidth() / scale), (int) Math.round((icon.getIconHeight()*GUIBasic.frame.getWidth())/(scale*icon.getIconWidth())), Image.SCALE_SMOOTH));

        jl.setIcon(scaledicon);
        jl.setHorizontalAlignment(JLabel.CENTER);
        jp.add(BorderLayout.CENTER,jl);
    }
    // method that takes in a clothes Enum and returns the relative address of the image for this clothing
    private static String clothesEnumToFile(Clothes c) {
        switch(c) {
            case TSHIRT:
                return "Res/tshirt.png";
            case RAINCOAT:
                return "Res/raincoat.png";
            case JACKET:
                return "Res/jumper.png";
            case WARMCOAT:
                return "Res/coat.png";
            case SHORTS:
                return "Res/shorts.png";
            case TROUSERS:
                return "Res/jeans.png";
            case TRAINERS:
                return "Res/trainers.png";
            case RAINBOOTS:
                return "Res/wellies.png";
            case FLIPFLOP:
                return "Res/flipflops.png";
        }
        return "Res/tshirt.png";
    }

// method that takes in a weather Enum and returns the relative address of the image for this weather
    private static String weatherEnumToFile(Weather weather){
        switch(weather){
            case SUNNY:
                return "Res/Sunny.png";
            case RAINY:
                return "Res/rainy.png";
            case SNOWY:
                return "Res/Snowy.png";
            case THUNDER:
                return "Res/thunder.png";
            case WINDY:
                return "Res/windy.png";
            case SUNCLOUD:
                return "Res/suncloud.png";
            case CLOUD:
                return "Res/cloudy.png";
        }
        return "Res/Sunny.png";
    }
    // method that takes in a temperature as a double and returns the thermometer image that represents this tempriture
    private static String tempToFile(double temp){
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

    //Method to read in font and print file
    public static void printtext(JPanel panel, String n, Font f) {
        JLabel label = new JLabel();
        label.setFont(f);
        label.setText(n);
        panel.add(BorderLayout.CENTER, label);
    }

    // a method which loads the home page, it takes in the day as an int 0-4 where 0 is current day
    public static JPanel loadHome(int day, APIInterface apii) throws IOException {
        //the weather for the current location is fetched and called forecast
        ArrayList<ArrayList<WeatherElement>> forecast = apii.getWeather();
        //defaults to current time on current day
        WeatherElement weatherForSelectedDay =forecast.get(0).get(0);
        if(day!=0) {
            //changes to midday on a future day if a future days is chosen
            weatherForSelectedDay = forecast.get(day).get(3);
        }
    //The clothes are selected using the Clothes picking algorithm and saved in an array
        Clothes items[] =WeatherApp.ClothesPickingLogic.whatClothes(weatherForSelectedDay);
        // the temperature is saved as a double called temp
        double temp = weatherForSelectedDay.getTemp();

        //font that is used is loaded in and size is set
        InputStream is = new FileInputStream(new File("Res/font.ttf"));
        Font font = null;
        try {
            Font tempfont = Font.createFont(Font.TRUETYPE_FONT, is);
            font = tempfont.deriveFont(28f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        // the base frame from GUIBasic is loaded and named base
        JFrame base =GUIBasic.loadhomeScreen();
        // a home panel to fill with contents for base is created
        JPanel homepanel = new JPanel();
        //it is given a 3/1 grid layout
        homepanel.setLayout(new GridLayout(3,1));

        //a panel called tempan is created this will be the top 1/3 of the screen and will contain temperature information
        JPanel tempPan = new JPanel();
        tempPan.setBackground(Color.decode("#8bb1ed"));
        tempPan.setLayout(new BorderLayout());
        //image panel will go in tempPan and holds the tempture and tempreture bar
        JPanel imaPanel = new JPanel();
        imaPanel.setBackground(Color.decode("#8bb1ed"));
        printtext(imaPanel,(Math.round(temp)+"\u00b0 C"),font);
        DisplayImage(imaPanel, tempToFile(temp), 1.5);
        JPanel settingsBar = new JPanel();
        settingsBar.setBackground(Color.decode("#8bb1ed"));
        settingsBar.setLayout(new BorderLayout());

        JButton settings = new JButton(new ImageIcon(((new ImageIcon("Res/SettingsButon.png")).getImage()).getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH)));

        //removes back ground and border of button so its just image
        settings.setBorder(BorderFactory.createEmptyBorder());
        settings.setContentAreaFilled(false);

        settingsBar.add(BorderLayout.WEST, settings);

        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    base.remove(homepanel);
                    base.add(GUIsettings.loadSettings(day, apii));
                    base.invalidate();
                    base.revalidate();
                }catch (IOException r){
                    r.printStackTrace();
                }
            }
        });

        //the top block contains the settingsBar and...
        tempPan.add(settingsBar, BorderLayout.NORTH);
        tempPan.setBackground(Color.red);
        tempPan.add(imaPanel,BorderLayout.CENTER);

        //logo panel
        JPanel logoPan = new JPanel();
        logoPan.setBackground(Color.decode("#8bb1ed"));
        DisplayImage(logoPan, weatherEnumToFile(weatherForSelectedDay.getWeather()), 2.25);

        //day and clothes panel
        JPanel dayClothes = new JPanel();
        dayClothes.setLayout(new GridLayout(2,1));

        JPanel dayPanel = new JPanel();
        dayPanel.setBackground(Color.decode("#8bb1ed"));

        //make left button
        if(day != 0){
            JButton left = new JButton(new ImageIcon(((new ImageIcon("Res/buttonleft.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));

            //removes back ground and border of button so its just image
            left.setBorder(BorderFactory.createEmptyBorder());
            left.setContentAreaFilled(false);

            left.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        base.remove(homepanel);
                        base.add(GUIHome.loadHome(day-1, apii));
                        base.invalidate();
                        base.revalidate();
                    }catch (IOException r){
                        r.printStackTrace();
                    }
                }
            });
            dayPanel.add(left,BorderLayout.WEST);
        }

        printtext(dayPanel, Day.getDay(day), font);

        //make right button
        if(day != 4){
            JButton right = new JButton(new ImageIcon(((new ImageIcon("Res/rightbutton.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));

            //removes back ground and border of button so its just image
            right.setBorder(BorderFactory.createEmptyBorder());
            right.setContentAreaFilled(false);

            right.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        base.remove(homepanel);
                        base.add(GUIHome.loadHome(day+1, apii));
                        base.invalidate();
                        base.revalidate();
                    }catch (IOException r){
                        r.printStackTrace();
                    }
                }
            });
            dayPanel.add(right,BorderLayout.EAST);
        }
        //this will be the clothes bar
        JPanel clothesPan = new JPanel();
        clothesPan.setBackground(Color.decode("#8bb1ed"));
        DisplayImage(clothesPan, clothesEnumToFile(items[0]), 7);
        DisplayImage(clothesPan, clothesEnumToFile(items[1]), 7);
        DisplayImage(clothesPan, clothesEnumToFile(items[2]), 7);

        // add to panels to dayclothes panel
        dayClothes.add(dayPanel);
        dayClothes.add(clothesPan);

        homepanel.add(tempPan);
        homepanel.add(logoPan);
        homepanel.add(dayClothes);
        return homepanel;

    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIHome {

    //Method to display an image in the Jpanel jp, following graphics url, with a division scale against window width
    public static void DisplayImage(JPanel jp, String url, int scale) {
        JLabel jl = new JLabel();
        ImageIcon icon = new javax.swing.ImageIcon(GUIHome.class.getResource(url));
        Image temp = icon.getImage();

        //Scale image to the width of window screen divided by scale. Scale height to preserve aspect ratio of image.
        ImageIcon scaledicon = new ImageIcon(temp.getScaledInstance((int) GUIBasic.frame.getWidth() / scale, (int) Math.round((icon.getIconHeight()*GUIBasic.frame.getWidth())/(scale*icon.getIconWidth())), Image.SCALE_SMOOTH));

        jl.setIcon(scaledicon);
        jl.setHorizontalAlignment(JLabel.CENTER);
        jp.add(BorderLayout.CENTER,jl);
    }

    public static JPanel loadHome(){
        JFrame base =GUIBasic.loadhomeScreen();
        JPanel homepanel = new JPanel();
        homepanel.setLayout(new GridLayout(3,1));

        JPanel tempPan = new JPanel();
        JPanel settingsBar = new JPanel();
        settingsBar.setBackground(Color.cyan);
        settingsBar.setLayout(new GridLayout(1,5));
        JPanel[] settingspanelHolder = new JPanel[5];

        tempPan.setBackground(Color.cyan);

        DisplayImage(tempPan, "therm.png", 2);


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

        //logo pannel
        JPanel logoPan = new JPanel();
        logoPan.setBackground(Color.cyan);
        DisplayImage(logoPan, "Sunny.png", 2);

        //this will be the clothes bar
        JPanel clothesPan = new JPanel();
        clothesPan.setBackground(Color.cyan);
        DisplayImage(clothesPan, "flipflops.png", 4);
        DisplayImage(clothesPan, "tshirt.png", 4);


        homepanel.add(tempPan);
        homepanel.add(logoPan);
        homepanel.add(clothesPan);
        return homepanel;

    }

}

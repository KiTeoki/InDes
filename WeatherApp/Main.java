import javax.swing.*;

public class Main {
    public static void main(String args[]) {
        JFrame base =GUIBasic.loadhomeScreen();
        base.add(GUIHome.loadHome());
        base.setVisible(true);
    }
}

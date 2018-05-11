import javax.swing.*;
import java.awt.*;

public class GUIBasic extends JFrame {
    static JFrame frame = new JFrame("My First Weather App");
    public static JFrame loadhomeScreen(){
        frame.setSize(300,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.cyan);
        return frame;
    }


}

import javax.swing.*;
import java.awt.*;

public class GUIBasic extends JFrame {
    JFrame frame = new JFrame("New Frame");
    public void loadhomeScreen(){
        frame.setSize(300,400);
        frame.getContentPane().setBackground(Color.cyan);
        frame.setVisible(true);
    }


}

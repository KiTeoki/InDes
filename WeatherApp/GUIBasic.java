import javax.swing.*;
import java.awt.*;

public class GUIBasic extends JFrame {
    public void loadBackground(){
        JFrame frame = new JFrame("New Frame");
        frame.setSize(300,400);
        frame.getContentPane().setBackground(Color.cyan);
        frame.setVisible(true);
    }


}

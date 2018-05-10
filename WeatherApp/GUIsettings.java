import javax.swing.*;
import java.awt.*;

public class GUIsettings {
    JFrame frame = new JFrame("New Frame");
    public void loadSettings() {
        frame.setSize(300, 400);
        frame.getContentPane().setBackground(Color.cyan);
        frame.setVisible(true);
    }
}

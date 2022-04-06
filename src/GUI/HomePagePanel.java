package GUI;

import javax.swing.*;
import java.awt.*;

public class HomePagePanel extends JPanel {


    public HomePagePanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600, 500));
        this.setBackground(new Color(0x344955));

        JLabel label = new JLabel("Welcome");
        label.setForeground(Color.WHITE);
        label.setBounds(300, 250, 100, 10);
        this.add(label);

    }
}

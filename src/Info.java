import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Info extends JFrame implements ActionListener {
    private JFrame frame2 = new JFrame();
    ;
    private JButton OK;
    private JLabel background;

    public Info() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame2.setSize(500, 380);
        frame2.setBackground(Color.white);
        Container container = frame2.getContentPane();
        container.setLayout(null);

        ImageIcon info = new ImageIcon("info.jpg");

        background = new JLabel("", info, JLabel.CENTER);
        background.setBounds(0, 0, 491, 312);
        container.add(background);


        OK = new JButton("Got it!");
        container.add(OK);
        OK.setBounds(200, 311, 100, 30);
        OK.addActionListener(this);

        frame2.setVisible(true);
        frame2.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        if (key == "Got it!") {
            frame2.dispose();
            new UserInterface();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UserInterface extends JFrame implements ActionListener {
    private JLabel title;
    private JButton easy;
    private JButton info;
    private JButton exit;
    static JFrame frame = new JFrame();
    private JButton hard;
    private JButton megahard;
    private JButton leaderboard;
    private File file;
    private boolean kasOlemas;


    public UserInterface() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        File file = new File("punktid.txt");
        kasOlemas = file.exists();

        frame.setSize(500, 325);

        Container container = frame.getContentPane();
        container.setLayout(null);

        title = new JLabel("Snake");
        easy = new JButton("Easy");
        info = new JButton("Info");
        exit = new JButton("Exit");
        hard = new JButton("Hard");
        megahard = new JButton("Cursed");
        leaderboard = new JButton("Leaderboard");

        easy.setFocusPainted(false);
        easy.setContentAreaFilled(false);

        hard.setFocusPainted(false);
        hard.setContentAreaFilled(false);

        megahard.setFocusPainted(false);
        megahard.setContentAreaFilled(false);

        info.setFocusPainted(false);
        info.setContentAreaFilled(false);

        leaderboard.setFocusPainted(false);
        leaderboard.setContentAreaFilled(false);
        if (!kasOlemas) {
            leaderboard.setEnabled(false);
        }

        exit.setFocusPainted(false);
        exit.setContentAreaFilled(false);

        container.add(title);
        title.setFont(new Font("Chiller", Font.BOLD, 50));
        title.setBounds(200, 20, 250, 75);

        container.add(easy);
        easy.setBounds(75, 100, 110, 30);
        easy.addActionListener(this);

        container.add(hard);
        hard.setBounds(195, 100, 110, 30);
        hard.addActionListener(this);

        container.add(megahard);
        megahard.setBounds(315, 100, 110, 30);
        megahard.addActionListener(this);

        container.add(info);
        info.setBounds(175, 140, 150, 30);
        info.addActionListener(this);

        container.add(leaderboard);
        leaderboard.setBounds(175, 180, 150, 30);
        leaderboard.addActionListener(this);

        container.add(exit);
        exit.setBounds(175, 220, 150, 30);
        exit.addActionListener(this);


        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        if (key == "Easy") {
            frame.dispose();
            new MainAken().add(new Mäng(200));
        } else if (key == "Hard") {
            frame.dispose();
            new MainAken().add(new Mäng(110));
        } else if (key == "Cursed") {
            frame.dispose();
            new MainAken().add(new Mäng(50));
        } else if (key == "Info") {
            frame.dispose();
            new Info();
        } else if (key == "Leaderboard") {
            frame.dispose();
            new Leaderboard();
        } else {
            frame.dispose();
        }
    }
}

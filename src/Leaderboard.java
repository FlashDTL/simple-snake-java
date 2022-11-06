import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Leaderboard extends JFrame implements ActionListener {
    private List<Integer> top;
    private static JFrame frame3 = new JFrame();
    private JButton OK;
    private JLabel results;
    private JLabel title;


    public static List<Integer> loeFail(String failinimi) throws Exception {
        List<Integer> top = new ArrayList<>();
        File file = new File(failinimi);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String rida = scanner.nextLine();
                String[] osad = rida.split(";");
                for (String s : osad) {
                    int tükid = Integer.parseInt(s);
                    top.add(tükid);
                }
            }
        }
        return top;


    }

    public Leaderboard() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame3.setSize(500, 380);
        frame3.setBackground(Color.white);
        Container container = frame3.getContentPane();
        container.setLayout(null);

        OK = new JButton("Return");
        container.add(OK);
        OK.setBounds(200, 311, 100, 30);
        OK.addActionListener(this);

        title = new JLabel("Best results");
        title.setFont(new Font("Chiller", Font.BOLD, 50));
        title.setBounds(150, 20, 250, 75);
        container.add(title);


        try {
            top = loeFail("punktid.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(top, Collections.reverseOrder());
        int abimuutuja = 30;
        if (top.size() <= 5) {
            for (Integer integer : top) {
                String sõne = Integer.toString(integer);
                results = new JLabel(sõne + " points");
                results.setBounds(220, 20 + abimuutuja, 250, 75);
                abimuutuja += 30;
                container.add(results);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                String sõne = Integer.toString(top.get(i));
                results = new JLabel(sõne + " points");
                results.setBounds(220, 50 + i * 30, 250, 75);
                container.add(results);

            }
        }
        frame3.setVisible(true);
        frame3.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        if (key == "Return") {
            frame3.dispose();
            new UserInterface();
        }

    }
}

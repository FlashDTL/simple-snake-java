import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Random;


public class Mäng extends JPanel implements ActionListener {
    int algsuurus = 3; //Snake'i pikkus
    int pixlid = 32; //ekraan on jaotatud ruudustikuks, kus iga ruut on 32*32 pixlit
    int õun_x; //õuna koordinaadid
    int õun_y;
    int[] x = new int[300]; //massiivid, mis määravad Snake'i koordinaate (maksimaalne Snake'i pikkus on 300)
    int[] y = new int[300];
    boolean Mängus = true;
    Timer timer;
    Image keha;
    Image õun;
    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = true; //alguses liikumine on suunatud paremale

    public Mäng(int raskus) {
        try {
            File file = new File("punktid.txt");
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Tekkis viga");
            e.printStackTrace();
        }

        keyboard keyboard = new keyboard();
        addKeyListener(keyboard);
        timer = new Timer(raskus, this);//taimer, mis reguleerib mängu kiirust
        algus();
        loadImages();
        setFocusable(true);
        Leaderboard();
    }

    // Õuna generaator, mis annab väärtuse õuna x ja y koordinaatidele
    public void õunGen() {
        while (true) {
            int abi = 0;
            int random_a = new Random().nextInt(20); //juhuslikud arvud (ekraani laius ja kõrgus)
            int random_b = new Random().nextInt(15);
            õun_x = random_a * pixlid;
            õun_y = random_b * pixlid;
            if (õun_x > 32 && õun_x < 608 && õun_y > 32 && õun_y < 446) { //kontrollib, kas õuna koordinaadid asuvad ekraani sees
                for (int i = 0; i < algsuurus; i++) {
                    if (x[i] == õun_x && y[i] == õun_y) { //kontrollib, kas õun ei ole genereeritud Snake'i keha sees
                        abi += 1;
                    }

                }
                if (abi == 0) { //kui koordinaadid sobivad, siis peatab programm tsükli tööd. Vastasel juhul genereerib see uusi koordinaate
                    break;
                }
            }
        }
    }

    public void loadImages() {
        ImageIcon a = new ImageIcon("punkt.png");
        keha = a.getImage();
        ImageIcon b = new ImageIcon("õun.png");
        õun = b.getImage();
    }

    public void algus() {
        for (int i = 0; i < algsuurus; i++) { //mängija algpositsioon
            x[i] = 320 - i * pixlid;
            y[i] = 320;
        }
        õunGen(); //mängu alguses ilmub õun
    }

    public void liikumine() {
        for (int i = algsuurus; i > 0; i--) {
            x[i] = x[i - 1]; //alumine keha osa võtab enda koordinaatideks temast järgmise keha osa koordinaate
            y[i] = y[i - 1];
        }
        if (left) { //kui Snake läheb vasakule, siis tema x koordinaat läheb väiksemaks 32 võrra (üks Snake'i samm mistahes suunda = 32 pixlit)
            x[0] = x[0] - pixlid;
        }
        if (right) { //paremale minnes, x koordinaat suureneb
            x[0] = x[0] + pixlid;
        }
        if (up) { //mida kõrgem ekraanil, seda väiksem on y koordinaat
            y[0] = y[0] - pixlid;
        }
        if (down) { //mida madalam ekraanil, seda suurem on y koordinaat
            y[0] = y[0] + pixlid;
        } //x[0] ja y[0] on Snake'i "pea" koordinaadid, kõik muu on saba

    }

    public void kontrolliÕun() { //kontrollib kas õun on söödud
        if (x[0] == õun_x && y[0] == õun_y) { //kui Snake'i "pea" koordinaadid on võrdsed õuna koordinaatidega
            algsuurus = algsuurus + 1; // liidetakse juurde uue keha osa
            õunGen(); //genereeritakse uue õuna
        }
    }

    public void gameOver() {
        if (x[0] < 0 || x[0] > 608 || y[0] < 0 || y[0] > 416) { //kui mängija liigub ekraanist välja, siis mäng on läbi
            Mängus = false;
            Leaderboard();
        }
        for (int i = algsuurus; i > 0; i--) { //kui mängija sõidab vastu oma keha, siis mäng on läbi
            if (x[0] == x[i] && y[0] == y[i]) {
                Mängus = false;
                Leaderboard();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) { //graafika, mis ilmub ekraanile
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < 640; i += 32) { //ruudustiku joonistamine
            g.drawLine(i, 0, i, 480);
        }
        for (int i = 0; i < 480; i += 32) {
            g.drawLine(0, i, 640, i);

        }
        if (Mängus) { //õuna ja Snake'i keha joonistamine
            g.drawImage(õun, õun_x, õun_y, this);
            for (int i = 0; i < algsuurus; i++) {
                g.drawImage(keha, x[i], y[i], this);
            }
        } else { //kui mäng on läbi, siis ekraanile ilmub Game over
            g.setColor(Color.RED);
            g.setFont(new Font("SansSerif", Font.BOLD, 60));
            g.drawString("Game over", 175, 225);
            g.setFont(new Font("SansSerif", Font.ITALIC, 40));
            g.setColor(Color.BLACK);
            g.drawString("Score: ", 150, 300);
            String punktid = Integer.toString(algsuurus - 3);
            g.drawString(punktid, 280, 305);
        }
    }

    public void Leaderboard() {
        if (!Mängus) {
            String punktid = Integer.toString(algsuurus - 3);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("punktid.txt", true));
                bw.append(punktid + ";");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Mängus) {
            kontrolliÕun();
            gameOver();
            liikumine();
        }
        repaint();

    }

    class keyboard extends KeyAdapter { //klaviatuuri kasutamine mängus liikumiseks
        @Override
        public void keyPressed(KeyEvent a) {
            super.keyPressed(a);
            timer.start();
            int nupp = a.getKeyCode();
            // kui õige nupp on vajutatud, muudab Snake oma liikumissuunda
            if (nupp == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (nupp == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (nupp == KeyEvent.VK_UP && !down) {
                left = false;
                up = true;
                right = false;
            }
            if (nupp == KeyEvent.VK_DOWN && !up) {
                left = false;
                down = true;
                right = false;
            }
        }
    }
}


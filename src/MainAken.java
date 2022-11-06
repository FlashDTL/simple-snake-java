import javax.swing.*;

public class MainAken extends JFrame {
    public MainAken() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(640, 480);
        setVisible(true);
    }

    public static void main(String[] args) {
        new UserInterface();
    }
}


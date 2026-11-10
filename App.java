package pacman;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import p.Model2;

public class App extends JFrame {

    public App() {
        setTitle("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Model2());
        setSize(380, 420);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App pac = new App();
            pac.setVisible(true);
        });
    }
}

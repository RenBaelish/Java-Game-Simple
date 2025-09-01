package PongGame;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game Klasik");
        Arena gameArena = new Arena();

        frame.add(gameArena);
        frame.pack(); // Mengatur ukuran frame sesuai preferensi panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Menempatkan jendela di tengah layar
        frame.setResizable(false); // Ukuran jendela tidak bisa diubah
        frame.setVisible(true);
    }
}

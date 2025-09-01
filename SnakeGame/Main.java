import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Snake Game");
        GameBoard gameBoard = new GameBoard();

        frame.add(gameBoard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack(); // Menyesuaikan ukuran frame dengan panel
        frame.setLocationRelativeTo(null); // Menempatkan frame di tengah layar
        frame.setVisible(true);
    }
}

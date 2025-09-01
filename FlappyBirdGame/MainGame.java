import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainGame extends JPanel implements ActionListener, KeyListener {

    // Ukuran layar
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    // Objek GameManager
    private GameManager gameManager;

    // Timer untuk game loop
    private Timer gameLoop;

    public MainGame() {
        // Inisialisasi GameManager
        gameManager = new GameManager(SCREEN_WIDTH, SCREEN_HEIGHT);

        // Mengatur properti panel
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.CYAN); // Warna langit
        setFocusable(true);
        addKeyListener(this);

        // Membuat dan memulai timer sebagai game loop (sekitar 60 FPS)
        gameLoop = new Timer(16, this);
        gameLoop.start();
    }

    /**
     * Metode utama untuk menggambar semua elemen game.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Menggambar Burung (sebagai kotak oranye)
        Bird bird = gameManager.burung;
        g.setColor(Color.ORANGE);
        g.fillRect(bird.posisiX, bird.posisiY, bird.lebar, bird.tinggi);

        // Menggambar Pipa (sebagai kotak hijau)
        g.setColor(Color.GREEN);
        for (Pipe pipe : gameManager.daftarPipa) {
            int celahAtas = pipe.posisiYCelāh - (pipe.tinggiCelāh / 2);
            int celahBawah = pipe.posisiYCelāh + (pipe.tinggiCelāh / 2);
            // Pipa atas
            g.fillRect(pipe.posisiX, 0, pipe.lebar, celahAtas);
            // Pipa bawah
            g.fillRect(pipe.posisiX, celahBawah, pipe.lebar, SCREEN_HEIGHT - celahBawah);
        }

        // Menggambar Skor dan Status Game
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        if (gameManager.statusGame.equals("Siap")) {
            g.drawString("Tekan SPASI untuk Mulai", 100, SCREEN_HEIGHT / 2 - 50);
        } else if (gameManager.statusGame.equals("Game Over")) {
            g.drawString("Game Over!", 250, SCREEN_HEIGHT / 2 - 50);
            g.drawString("Skor: " + gameManager.skor, 300, SCREEN_HEIGHT / 2 + 20);
            g.drawString("Tekan SPASI", 250, SCREEN_HEIGHT / 2 + 90);
        } else {
            g.drawString(String.valueOf(gameManager.skor), SCREEN_WIDTH / 2 - 20, 70);
        }
    }

    /**
     * Metode ini dipanggil oleh Timer setiap 16 milidetik.
     * Ini adalah inti dari game loop.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Update logika game
        gameManager.update();
        // Minta Swing untuk menggambar ulang layar
        repaint();
    }

    /**
     * Metode untuk menangani input keyboard.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Jika menekan spasi
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameManager.statusGame.equals("Main")) {
                gameManager.burung.lompat();
            } else if (gameManager.statusGame.equals("Siap")) {
                gameManager.startGame();
            } else if (gameManager.statusGame.equals("Game Over")) {
                gameManager.resetGame();
            }
        }
    }

    // Metode KeyListener lain yang tidak kita gunakan
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}


    /**
     * Metode main() adalah titik masuk program.
     */
    public static void main(String[] args) {
        // Membuat jendela utama (JFrame)
        JFrame window = new JFrame("Flappy Bird Sederhana");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Menambahkan panel game kita ke dalam jendela
        MainGame gamePanel = new MainGame();
        window.add(gamePanel);

        // Mengatur ukuran jendela agar pas dengan panel
        window.pack();

        // Menampilkan jendela di tengah layar
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {
    // Atribut Papan Permainan
    private final int lebar = 600;
    private final int tinggi = 600;
    private final int TILE_SIZE = 20; // Ukuran setiap kotak grid
    private int skorSaatIni;
    private boolean isGameOver = false;

    // Objek Game
    private Snake snake;
    private Food food;
    private Timer timer;

    public GameBoard() {
        setPreferredSize(new Dimension(lebar, tinggi));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new GameKeyAdapter());
        mulaiPermainan();
    }

    private void mulaiPermainan() {
        skorSaatIni = 0;
        isGameOver = false;
        snake = new Snake(5, 5); // Ular mulai di koordinat (5,5)
        food = new Food(lebar, tinggi, TILE_SIZE);
        timer = new Timer(140, this); // Timer untuk loop game (kecepatan)
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isGameOver) {
            gambarPermainan(g);
        } else {
            tampilkanGameOver(g);
        }
    }

    private void gambarPermainan(Graphics g) {
        // Gambar Makanan
        g.setColor(Color.RED);
        g.fillRect(food.getPosisiX() * TILE_SIZE, food.getPosisiY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Gambar Ular
        for (Point segment : snake.getSegmenTubuh()) {
            if (segment == snake.getPosisiKepala()) {
                 g.setColor(Color.GREEN); // Kepala ular
            } else {
                 g.setColor(new Color(45, 180, 0)); // Tubuh ular
            }
            g.fillRect(segment.x * TILE_SIZE, segment.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Gambar Skor
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Skor: " + skorSaatIni, 10, 20);
    }

    private void tampilkanGameOver(Graphics g) {
        String msg = "Game Over";
        Font font = new Font("Arial", Font.BOLD, 50);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(msg, (lebar - metrics.stringWidth(msg)) / 2, tinggi / 2);

        String scoreMsg = "Skor Akhir: " + skorSaatIni;
        Font scoreFont = new Font("Arial", Font.BOLD, 25);
        metrics = getFontMetrics(scoreFont);
        g.setColor(Color.WHITE);
        g.setFont(scoreFont);
        g.drawString(scoreMsg, (lebar - metrics.stringWidth(scoreMsg)) / 2, tinggi / 2 + 50);
    }


    private void cekTabrakan() {
        Point kepala = snake.getPosisiKepala();

        // Cek tabrakan dengan dinding
        if (kepala.x < 0 || kepala.x >= (lebar / TILE_SIZE) || kepala.y < 0 || kepala.y >= (tinggi / TILE_SIZE)) {
            isGameOver = true;
        }

        // Cek tabrakan dengan diri sendiri
        if (snake.cekTabrakanDiri()) {
            isGameOver = true;
        }

        if (isGameOver) {
            timer.stop();
        }
    }

    private void cekMakanMakanan() {
        if (snake.getPosisiKepala().x == food.getPosisiX() && snake.getPosisiKepala().y == food.getPosisiY()) {
            snake.tumbuh();
            skorSaatIni += food.getNilaiSkor();
            food.generatePosisi(lebar, tinggi, TILE_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            snake.gerak();
            cekMakanMakanan();
            cekTabrakan();
        }
        repaint(); // Panggil paintComponent untuk menggambar ulang
    }

    // Kelas internal untuk menangani input keyboard
    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                snake.setArahGerak("Kiri");
            } else if (key == KeyEvent.VK_RIGHT) {
                snake.setArahGerak("Kanan");
            } else if (key == KeyEvent.VK_UP) {
                snake.setArahGerak("Atas");
            } else if (key == KeyEvent.VK_DOWN) {
                snake.setArahGerak("Bawah");
            }
        }
    }
}

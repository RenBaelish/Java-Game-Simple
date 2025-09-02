import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

class GamePanel extends JPanel implements ActionListener {

    // Ukuran Jendela Game
    private final int PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 600;

    // Properti Peta dan Tile
    private final int TILE_SIZE = 40;
    private int[][] tileMap; // 2D array untuk menyimpan peta
    private final int MAP_WIDTH_IN_TILES = 50;
    private final int MAP_HEIGHT_IN_TILES = 40;

    // Konstanta untuk jenis tile
    private final int TILE_GRASS = 0;
    private final int TILE_WATER = 1;

    // Properti Player
    private final int PLAYER_SIZE = 32;
    private final int PLAYER_SPEED = 4;
    private int playerWorldX, playerWorldY; // Posisi player di koordinat dunia

    // Properti Musuh
    private List<Rectangle> enemies;

    // Properti Kamera
    private int cameraX, cameraY;

    // Status Game
    private boolean isGameOver;
    private Timer timer;

    public GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new KeyInputAdapter());
        this.setFocusable(true);
        startGame();
    }

    private void createMap() {
        tileMap = new int[MAP_HEIGHT_IN_TILES][MAP_WIDTH_IN_TILES];
        // Membuat peta dasar (semua rumput)
        for (int row = 0; row < MAP_HEIGHT_IN_TILES; row++) {
            for (int col = 0; col < MAP_WIDTH_IN_TILES; col++) {
                tileMap[row][col] = TILE_GRASS;
            }
        }
        // Menambahkan danau/sungai (tile air yang tidak bisa dilewati)
        for (int i = 0; i < 15; i++) {
            tileMap[10][5 + i] = TILE_WATER;
            tileMap[11][5 + i] = TILE_WATER;
        }
        for (int i = 0; i < 20; i++) {
            tileMap[20 + i][25] = TILE_WATER;
            tileMap[20 + i][26] = TILE_WATER;
        }
    }

    private void startGame() {
        createMap();
        playerWorldX = 100;
        playerWorldY = 100;

        enemies = new ArrayList<>();
        // Menempatkan beberapa musuh (kotak merah) di peta
        enemies.add(new Rectangle(500, 200, PLAYER_SIZE, PLAYER_SIZE));
        enemies.add(new Rectangle(800, 900, PLAYER_SIZE, PLAYER_SIZE));
        enemies.add(new Rectangle(1200, 600, PLAYER_SIZE, PLAYER_SIZE));

        isGameOver = false;

        if (timer == null) {
            timer = new Timer(16, this);
            timer.start();
        } else {
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // --- MENGGAMBAR BERDASARKAN POSISI KAMERA ---

        // 1. Gambar Peta (Tile)
        for (int row = 0; row < MAP_HEIGHT_IN_TILES; row++) {
            for (int col = 0; col < MAP_WIDTH_IN_TILES; col++) {
                int tileType = tileMap[row][col];
                if (tileType == TILE_GRASS) {
                    g.setColor(new Color(34, 139, 34)); // Hijau tua
                } else if (tileType == TILE_WATER) {
                    g.setColor(new Color(0, 119, 190)); // Biru
                }

                // Hitung posisi tile di layar relatif terhadap kamera
                int screenX = col * TILE_SIZE - cameraX;
                int screenY = row * TILE_SIZE - cameraY;
                g.fillRect(screenX, screenY, TILE_SIZE, TILE_SIZE);
            }
        }

        // 2. Gambar Musuh
        g.setColor(Color.RED);
        for (Rectangle enemy : enemies) {
            int screenX = enemy.x - cameraX;
            int screenY = enemy.y - cameraY;
            g.fillRect(screenX, screenY, enemy.width, enemy.height);
        }

        // 3. Gambar Player
        g.setColor(Color.ORANGE);
        int playerScreenX = playerWorldX - cameraX;
        int playerScreenY = playerWorldY - cameraY;
        g.fillRect(playerScreenX, playerScreenY, PLAYER_SIZE, PLAYER_SIZE);

        // 4. Gambar UI (jika game over)
        if (isGameOver) {
            g.setColor(new Color(0, 0, 0, 150)); // Latar belakang semi-transparan
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("YOU DIED", (PANEL_WIDTH - metrics.stringWidth("YOU DIED")) / 2, PANEL_HEIGHT / 2);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            metrics = getFontMetrics(g.getFont());
            g.drawString("Press ENTER to Restart", (PANEL_WIDTH - metrics.stringWidth("Press ENTER to Restart")) / 2, (PANEL_HEIGHT / 2) + 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            movePlayer();
            updateCamera();
            checkCollisions();
        }
        repaint();
    }

    // Variabel untuk mengontrol gerakan
    private boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false;

    private void movePlayer() {
        int nextX = playerWorldX;
        int nextY = playerWorldY;

        if (moveUp) nextY -= PLAYER_SPEED;
        if (moveDown) nextY += PLAYER_SPEED;
        if (moveLeft) nextX -= PLAYER_SPEED;
        if (moveRight) nextX += PLAYER_SPEED;

        // Cek tabrakan dengan batas peta
        if (nextX < 0) nextX = 0;
        if (nextY < 0) nextY = 0;
        if (nextX > MAP_WIDTH_IN_TILES * TILE_SIZE - PLAYER_SIZE) nextX = MAP_WIDTH_IN_TILES * TILE_SIZE - PLAYER_SIZE;
        if (nextY > MAP_HEIGHT_IN_TILES * TILE_SIZE - PLAYER_SIZE) nextY = MAP_HEIGHT_IN_TILES * TILE_SIZE - PLAYER_SIZE;

        // Cek tabrakan dengan tile solid (air)
        int tileCol = nextX / TILE_SIZE;
        int tileRow = nextY / TILE_SIZE;
        if (tileMap[tileRow][tileCol] != TILE_WATER) {
             playerWorldX = nextX;
             playerWorldY = nextY;
        }
    }

    private void updateCamera() {
        // Kamera berpusat pada player
        cameraX = playerWorldX - (PANEL_WIDTH / 2);
        cameraY = playerWorldY - (PANEL_HEIGHT / 2);

        // Batasi kamera agar tidak keluar dari peta
        int mapPixelWidth = MAP_WIDTH_IN_TILES * TILE_SIZE;
        int mapPixelHeight = MAP_HEIGHT_IN_TILES * TILE_SIZE;
        cameraX = Math.max(0, Math.min(cameraX, mapPixelWidth - PANEL_WIDTH));
        cameraY = Math.max(0, Math.min(cameraY, mapPixelHeight - PANEL_HEIGHT));
    }

    private void checkCollisions() {
        Rectangle playerBounds = new Rectangle(playerWorldX, playerWorldY, PLAYER_SIZE, PLAYER_SIZE);
        for (Rectangle enemy : enemies) {
            if (playerBounds.intersects(enemy)) {
                isGameOver = true;
                timer.stop();
                break;
            }
        }
    }

    // Kelas internal untuk mengelola input keyboard
    private class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isGameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
                startGame();
                return;
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W: moveUp = true; break;
                case KeyEvent.VK_S: moveDown = true; break;
                case KeyEvent.VK_A: moveLeft = true; break;
                case KeyEvent.VK_D: moveRight = true; break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W: moveUp = false; break;
                case KeyEvent.VK_S: moveDown = false; break;
                case KeyEvent.VK_A: moveLeft = false; break;
                case KeyEvent.VK_D: moveRight = false; break;
            }
        }
    }
}

public class GameSederhana {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple 2D RPG World");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

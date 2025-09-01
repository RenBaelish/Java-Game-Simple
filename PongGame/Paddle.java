package PongGame;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
    // Atribut
    public int posisiX;
    public int posisiY;
    public int lebar;
    public int tinggi;
    public int skor;

    // Konstruktor
    public Paddle(int x, int y, int lebar, int tinggi) {
        this.posisiX = x;
        this.posisiY = y;
        this.lebar = lebar;
        this.tinggi = tinggi;
        this.skor = 0;
    }

    // Menggambar paddle ke layar
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(posisiX, posisiY, lebar, tinggi);
    }

    // Menggerakkan paddle ke atas
    public void moveUp(int batasAtas) {
        if (posisiY > batasAtas) {
            posisiY -= 15; // Kecepatan gerak paddle
        }
    }

    // Menggerakkan paddle ke bawah
    public void moveDown(int batasBawah) {
        if (posisiY + tinggi < batasBawah) {
            posisiY += 15; // Kecepatan gerak paddle
        }
    }
}

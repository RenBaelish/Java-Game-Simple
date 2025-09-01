package PongGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {
    // Atribut
    public int posisiX;
    public int posisiY;
    public int radius;
    public float kecepatanX;
    public float kecepatanY;
    public Color warna;

    // Konstruktor
    public Ball(int x, int y, int radius) {
        this.posisiX = x;
        this.posisiY = y;
        this.radius = radius;
        this.warna = Color.YELLOW;

        // Kecepatan awal acak
        Random random = new Random();
        this.kecepatanX = random.nextBoolean() ? 5 : -5;
        this.kecepatanY = random.nextBoolean() ? 5 : -5;
    }

    // Menggambar bola
    public void draw(Graphics g) {
        g.setColor(warna);
        g.fillOval(posisiX - radius, posisiY - radius, radius * 2, radius * 2);
    }

    // Mengupdate posisi bola
    public void move() {
        posisiX += kecepatanX;
        posisiY += kecepatanY;
    }

    // Membalik arah horizontal (pantulan dari paddle)
    public void balikArahX() {
        kecepatanX *= -1;
    }

    // Membalik arah vertikal (pantulan dari dinding atas/bawah)
    public void balikArahY() {
        kecepatanY *= -1;
    }

    // Mengembalikan bola ke tengah setelah skor
    public void reset(int x, int y) {
        this.posisiX = x;
        this.posisiY = y;
        // Memberi arah acak baru
        Random random = new Random();
        this.kecepatanX = random.nextBoolean() ? 5 : -5;
        this.kecepatanY = random.nextBoolean() ? 5 : -5;
    }
}

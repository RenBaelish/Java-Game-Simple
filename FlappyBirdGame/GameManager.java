import java.util.ArrayList;
import java.util.List;

public class GameManager {
    // Atribut untuk status permainan
    public int skor;
    public String statusGame; // "Siap", "Main", "Game Over"
    public float kecepatanLevel;

    // Objek-objek dalam game
    public Bird burung;
    public List<Pipe> daftarPipa;

    // Atribut layar (untuk contoh)
    private int gameWidth;
    private int gameHeight;
    private int jarakAntarPipa = 300; // Jarak horizontal antar pipa

    /**
     * Konstruktor untuk GameManager.
     */
    public GameManager(int width, int height) {
        this.gameWidth = width;
        this.gameHeight = height;
        resetGame();
    }

    /**
     * Metode untuk memulai atau mereset permainan.
     */
    public void resetGame() {
        this.skor = 0;
        this.statusGame = "Siap";
        this.kecepatanLevel = 1.0f;
        this.burung = new Bird(50, gameHeight / 2);
        this.daftarPipa = new ArrayList<>();
        // Menambahkan pipa pertama di luar layar
        munculkanPipaBaru(gameWidth + 100);
    }

    /**
     * Memulai permainan.
     */
    public void startGame() {
        if (statusGame.equals("Siap")) {
            statusGame = "Main";
            burung.lompat(); // Lompatan pertama untuk memulai
        }
    }

    /**
     * Metode utama untuk memperbarui logika game setiap frame.
     */
    public void update() {
        if (!statusGame.equals("Main")) {
            return; // Jangan lakukan apa-apa jika game tidak sedang berjalan
        }

        // 1. Update posisi burung
        burung.update();

        // 2. Cek tabrakan dengan tanah atau langit-langit
        if (burung.posisiY + burung.tinggi > gameHeight || burung.posisiY < 0) {
            gameOver();
        }

        // 3. Gerakkan semua pipa dan cek tabrakan
        Pipe pipaBerikutnya = null;
        for (Pipe pipa : daftarPipa) {
            pipa.gerak();

            // Cari pipa terdekat yang belum dilewati
            if (!pipa.sudahDilewati && pipa.posisiX + pipa.lebar > burung.posisiX) {
                pipaBerikutnya = pipa;
            }

            // Cek tabrakan dengan burung
            if (cekTabrakan(burung, pipa)) {
                gameOver();
                return;
            }
        }

        // 4. Cek skor
        if (pipaBerikutnya != null && burung.posisiX > pipaBerikutnya.posisiX + pipaBerikutnya.lebar) {
            tambahSkor();
            pipaBerikutnya.sudahDilewati = true;
        }

        // 5. Hapus pipa yang sudah di luar layar dan tambahkan yang baru
        Pipe pipaTerakhir = daftarPipa.get(daftarPipa.size() - 1);
        if (pipaTerakhir.posisiX < gameWidth - jarakAntarPipa) {
            munculkanPipaBaru(gameWidth);
        }

        // Hapus pipa yang sudah keluar dari layar sebelah kiri
        daftarPipa.removeIf(Pipe::diLuarLayar);
    }

    private void munculkanPipaBaru(int x) {
        daftarPipa.add(new Pipe(x, gameHeight));
    }

    private void tambahSkor() {
        skor++;
        System.out.println("Skor: " + skor); // Output konsol untuk debugging
    }

    private void gameOver() {
        statusGame = "Game Over";
        burung.isHidup = false;
        System.out.println("Game Over! Skor Akhir: " + skor);
    }

    /**
     * Logika sederhana untuk mengecek tabrakan antara burung dan pipa.
     * @param b Objek Burung
     * @param p Objek Pipa
     * @return true jika terjadi tabrakan.
     */
    private boolean cekTabrakan(Bird b, Pipe p) {
        // Cek tabrakan horizontal
        boolean tabrakanX = b.posisiX < p.posisiX + p.lebar && b.posisiX + b.lebar > p.posisiX;

        // Cek tabrakan vertikal (dengan pipa atas dan bawah)
        int celahAtas = p.posisiYCelāh - (p.tinggiCelāh / 2);
        int celahBawah = p.posisiYCelāh + (p.tinggiCelāh / 2);
        boolean tabrakanY = b.posisiY < celahAtas || b.posisiY + b.tinggi > celahBawah;

        return tabrakanX && tabrakanY;
    }
}

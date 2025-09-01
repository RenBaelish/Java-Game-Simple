public class Pipe {
    // Atribut untuk posisi dan ukuran
    public int posisiX;
    public int posisiYCelāh; // Posisi Y dari pusat celah
    public int lebar;
    public int tinggiCelāh;

    // Atribut untuk pergerakan dan status
    public float kecepatanGerak;
    public boolean sudahDilewati;

    /**
     * Konstruktor untuk menciptakan objek Pipa.
     * @param x Posisi awal horizontal (X).
     * @param gameHeight Tinggi layar permainan untuk menentukan posisi celah.
     */
    public Pipe(int x, int gameHeight) {
        this.posisiX = x;
        this.lebar = 80; // Lebar standar pipa
        this.tinggiCelāh = 150; // Ukuran celah vertikal
        this.kecepatanGerak = -3.0f; // Bergerak dari kanan ke kiri
        this.sudahDilewati = false;

        // Menentukan posisi Y celah secara acak
        // Celah bisa muncul antara 1/4 dan 3/4 tinggi layar
        int minPosisiY = gameHeight / 4;
        int maxPosisiY = (gameHeight * 3) / 4;
        this.posisiYCelāh = (int) (Math.random() * (maxPosisiY - minPosisiY)) + minPosisiY;
    }

    /**
     * Metode untuk menggerakkan pipa ke kiri.
     */
    public void gerak() {
        this.posisiX += this.kecepatanGerak;
    }

    /**
     * Metode untuk memeriksa apakah pipa sudah keluar dari layar.
     * @return true jika pipa sudah di luar layar, false jika sebaliknya.
     */
    public boolean diLuarLayar() {
        return this.posisiX + this.lebar < 0;
    }
}

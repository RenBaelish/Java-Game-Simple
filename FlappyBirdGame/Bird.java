public class Bird {
    // Atribut untuk posisi dan ukuran
    public int posisiX;
    public int posisiY;
    public int lebar;
    public int tinggi;

    // Atribut untuk fisika dan status
    public float kecepatanVertikal;
    public float gravitasi;
    public float kekuatanLompat;
    public boolean isHidup;

    /**
     * Konstruktor untuk menciptakan objek Burung.
     * @param x Posisi awal horizontal (X).
     * @param y Posisi awal vertikal (Y).
     */
    public Bird(int x, int y) {
        this.posisiX = x;
        this.posisiY = y;
        this.lebar = 34; // Lebar standar gambar burung
        this.tinggi = 24; // Tinggi standar gambar burung
        this.gravitasi = 0.6f;
        this.kekuatanLompat = -10.0f; // Nilai negatif karena sumbu Y di Swing/Java2D mengarah ke bawah
        this.kecepatanVertikal = 0;
        this.isHidup = true;
    }

    /**
     * Metode untuk membuat burung melompat.
     */
    public void lompat() {
        // Mengatur kecepatan vertikal menjadi kekuatan lompat
        this.kecepatanVertikal = this.kekuatanLompat;
    }

    /**
     * Metode untuk memperbarui posisi burung berdasarkan gravitasi.
     */
    public void update() {
        // Menambahkan gravitasi ke kecepatan vertikal
        this.kecepatanVertikal += this.gravitasi;
        // Mengubah posisi Y berdasarkan kecepatan vertikal
        this.posisiY += this.kecepatanVertikal;
    }

    // Getter dan Setter bisa ditambahkan sesuai kebutuhan
}

import java.util.ArrayList;
import java.util.List;

public class Snake {
    // Atribut
    private int panjang;
    private String arahGerak; // "Atas", "Bawah", "Kiri", "Kanan"
    private Point posisiKepala;
    private List<Point> segmenTubuh;

    public Snake(int startX, int startY) {
        this.panjang = 1;
        this.arahGerak = "Kanan"; // Mulai dengan bergerak ke kanan
        this.posisiKepala = new Point(startX, startY);
        this.segmenTubuh = new ArrayList<>();
        this.segmenTubuh.add(new Point(posisiKepala.x, posisiKepala.y));
    }

    // Metode untuk menggerakkan ular
    public void gerak() {
        // Pindahkan kepala sesuai arah
        switch (arahGerak) {
            case "Atas":
                posisiKepala.y--;
                break;
            case "Bawah":
                posisiKepala.y++;
                break;
            case "Kiri":
                posisiKepala.x--;
                break;
            case "Kanan":
                posisiKepala.x++;
                break;
        }

        // Tambahkan kepala baru ke tubuh
        segmenTubuh.add(0, new Point(posisiKepala.x, posisiKepala.y));

        // Jika ular belum tumbuh, hapus ekornya
        if (segmenTubuh.size() > panjang) {
            segmenTubuh.remove(segmenTubuh.size() - 1);
        }
    }

    // Metode saat ular makan makanan
    public void tumbuh() {
        this.panjang++;
    }

    // Metode untuk cek tabrakan dengan diri sendiri
    public boolean cekTabrakanDiri() {
        for (int i = 1; i < segmenTubuh.size(); i++) {
            if (posisiKepala.x == segmenTubuh.get(i).x && posisiKepala.y == segmenTubuh.get(i).y) {
                return true;
            }
        }
        return false;
    }

    // Getter dan Setter
    public String getArahGerak() {
        return arahGerak;
    }

    public void setArahGerak(String arahGerak) {
        // Mencegah ular berbalik arah secara langsung
        if ((arahGerak.equals("Atas") && !this.arahGerak.equals("Bawah")) ||
            (arahGerak.equals("Bawah") && !this.arahGerak.equals("Atas")) ||
            (arahGerak.equals("Kiri") && !this.arahGerak.equals("Kanan")) ||
            (arahGerak.equals("Kanan") && !this.arahGerak.equals("Kiri"))) {
            this.arahGerak = arahGerak;
        }
    }

    public Point getPosisiKepala() {
        return posisiKepala;
    }

    public List<Point> getSegmenTubuh() {
        return segmenTubuh;
    }
}

import java.util.Random;

public class Food {
    // Atribut
    private int posisiX;
    private int posisiY;
    private int nilaiSkor;

    public Food(int boardWidth, int boardHeight, int TILE_SIZE) {
        this.nilaiSkor = 10;
        generatePosisi(boardWidth, boardHeight, TILE_SIZE);
    }

    // Metode untuk menghasilkan posisi makanan secara acak
    public void generatePosisi(int boardWidth, int boardHeight, int TILE_SIZE) {
        Random rand = new Random();
        this.posisiX = rand.nextInt(boardWidth / TILE_SIZE);
        this.posisiY = rand.nextInt(boardHeight / TILE_SIZE);
    }

    // Getter
    public int getPosisiX() {
        return posisiX;
    }

    public int getPosisiY() {
        return posisiY;
    }

    public int getNilaiSkor() {
        return nilaiSkor;
    }
}

import java.awt.*;

/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    // Ukuran dan padding gambar
    public static final int SIZE = 120;
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;

    // Isi sel dan koordinat
    Seed content;
    int row, col;

    // Apakah sel ini bagian dari kombinasi kemenangan
    public boolean isWinningCell = false;

    // Konstruktor
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    // Reset isi dan status pemenang
    public void newGame() {
        content = Seed.NO_SEED;
        isWinningCell = false;
    }

    // Gambar sel
    public void paint(Graphics g) {
        int x1 = col * SIZE + PADDING;
        int y1 = row * SIZE + PADDING;

        // Jika bagian dari sel pemenang, gambar outline coklat
        if (isWinningCell) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(150, 75, 0)); // coklat tua
            g2.setStroke(new BasicStroke(4));  // ketebalan garis
            g2.drawRect(col * SIZE, row * SIZE, SIZE, SIZE);
        }

        // Gambar simbol jika ada (X atau O)
        if (content == Seed.CROSS || content == Seed.NOUGHT) {
            g.drawImage(content.getImage(), x1, y1, SEED_SIZE, SEED_SIZE, null);
        }
    }
}

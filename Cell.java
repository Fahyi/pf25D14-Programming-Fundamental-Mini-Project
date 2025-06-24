import java.awt.*;

/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    public static final int SIZE = 120;
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;
    public static final int SEED_STROKE_WIDTH = 8;

    Seed content;
    int row, col;

    private boolean isHighlighted = false;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    public void newGame() {
        content = Seed.NO_SEED;
        isHighlighted = false;
    }

    public void setHighlighted(boolean val) {
        isHighlighted = val;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(SEED_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int x1 = col * SIZE + PADDING;
        int y1 = row * SIZE + PADDING;

        boolean gameOver = false;
        try {
            gameOver = GameManager.isGameOver(); // pastikan kamu sudah buat fungsi ini di GameManager
        } catch (Exception e) {
            // abaikan error di awal startup
        }

        // Warna latar untuk highlight pemenang
        if (isHighlighted) {
            g.setColor(new Color(210, 180, 140)); // coklat muda
            g.fillRect(col * SIZE, row * SIZE, SIZE, SIZE);
        }

        // Blur jika bukan pemenang dan game sudah selesai
        if (!isHighlighted && content != Seed.NO_SEED && gameOver) {
            g.setColor(new Color(200, 200, 200, 150)); // abu-abu transparan
            g.fillRect(col * SIZE, row * SIZE, SIZE, SIZE);
        }

        // Gambar simbol
        if (content == Seed.CROSS || content == Seed.NOUGHT) {
            g.drawImage(content.getImage(), x1, y1, SEED_SIZE, SEED_SIZE, null);
        }

        // Border untuk pemenang
        if (isHighlighted) {
            g2d.setColor(new Color(102, 51, 0)); // coklat tua
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(col * SIZE + 2, row * SIZE + 2, SIZE - 4, SIZE - 4);
        }
    }
}

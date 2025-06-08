import java.awt.*;

/**
 * The Cell4 class models each individual cell of the game board.
 */
public class Cell4 {
    // Constants for drawing
    public static final int SIZE = 120; // cell width/height
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;
    public static final int SEED_STROKE_WIDTH = 8;

    // Properties
    public Seed content;
    public int row, col;

    /** Constructor to initialize this cell */
    public Cell4(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    /** Reset this cell's content for new game */
    public void newGame4() {
        content = Seed.NO_SEED;
    }

    /** Custom paint method using updated method name */
    public void paint4(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(SEED_STROKE_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int x1 = col * SIZE + PADDING;
        int y1 = row * SIZE + PADDING;

        if (content == Seed.CROSS) {
            g2d.setColor(GameMain4.COLOR_CROSS);
            int x2 = (col + 1) * SIZE - PADDING;
            int y2 = (row + 1) * SIZE - PADDING;
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        } else if (content == Seed.NOUGHT) {
            g2d.setColor(GameMain4.COLOR_NOUGHT);
            g2d.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
        }
    }
}

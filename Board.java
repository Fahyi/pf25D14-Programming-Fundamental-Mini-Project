import java.awt.*;

/**
 * The Board class models the ROWS-by-COLS game board.
 */
public class Board {

    public static final int ROWS = 3;
    public static final int COLS = 3;

    public static final int CANVAS_WIDTH  = Cell.SIZE * COLS;
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;

    public static final int GRID_WIDTH       = 2;
    public static final int GRID_WIDTH_HALF  = GRID_WIDTH / 2;
    public static final int Y_OFFSET         = 1;
    public static final Color COLOR_GRID     = Color.LIGHT_GRAY;

    Cell[][] cells;

    public Board() {
        initGame();
    }

    public void initGame() {
        cells = new Cell[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                cells[r][c] = new Cell(r, c);
            }
        }
    }

    public void newGame() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                cells[r][c].newGame();
            }
        }
    }

    public State stepGame(Seed player, int row, int col) {
        cells[row][col].content = player;

        // Cek baris
        if (cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player) {
            cells[row][0].isWinningCell = true;
            cells[row][1].isWinningCell = true;
            cells[row][2].isWinningCell = true;
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        // Cek kolom
        if (cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player) {
            cells[0][col].isWinningCell = true;
            cells[1][col].isWinningCell = true;
            cells[2][col].isWinningCell = true;
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        // Cek diagonal utama
        if (row == col &&
                cells[0][0].content == player &&
                cells[1][1].content == player &&
                cells[2][2].content == player) {
            cells[0][0].isWinningCell = true;
            cells[1][1].isWinningCell = true;
            cells[2][2].isWinningCell = true;
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        // Cek diagonal anti
        if (row + col == 2 &&
                cells[0][2].content == player &&
                cells[1][1].content == player &&
                cells[2][0].content == player) {
            cells[0][2].isWinningCell = true;
            cells[1][1].isWinningCell = true;
            cells[2][0].isWinningCell = true;
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        // Cek apakah masih ada sel kosong (jika iya, lanjut main)
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (cells[r][c].content == Seed.NO_SEED) {
                    return State.PLAYING;
                }
            }
        }

        // Jika tidak ada pemenang dan tidak ada sel kosong, maka seri
        return State.DRAW;
    }

    public Seed[][] getCellContents() {
        Seed[][] contents = new Seed[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                contents[r][c] = cells[r][c].content;
            }
        }
        return contents;
    }

    public State checkGameState(Seed player, int row, int col) {
        if (cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player ||
                cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player ||
                row == col &&
                        cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player ||
                row + col == 2 &&
                        cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (cells[r][c].content == Seed.NO_SEED) return State.PLAYING;
            }
        }
        return State.DRAW;
    }

    public void paint(Graphics g) {
        g.setColor(COLOR_GRID);
        for (int r = 1; r < ROWS; r++) {
            g.fillRoundRect(0, Cell.SIZE * r - GRID_WIDTH_HALF,
                    CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int c = 1; c < COLS; c++) {
            g.fillRoundRect(Cell.SIZE * c - GRID_WIDTH_HALF, Y_OFFSET,
                    GRID_WIDTH, CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                cells[r][c].paint(g);
            }
        }
    }
}

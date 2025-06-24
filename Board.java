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
    private Cell[] winLine = new Cell[3]; // Tambahan: menyimpan garis kemenangan

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
        winLine = new Cell[3]; // Reset highlight
    }

    public State stepGame(Seed player, int row, int col) {
        cells[row][col].content = player;
        winLine = new Cell[3];

        // Cek horizontal
        if (cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player) {
            winLine[0] = cells[row][0]; winLine[1] = cells[row][1]; winLine[2] = cells[row][2];
        }
        // Cek vertikal
        else if (cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player) {
            winLine[0] = cells[0][col]; winLine[1] = cells[1][col]; winLine[2] = cells[2][col];
        }
        // Diagonal utama
        else if (row == col && cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player) {
            winLine[0] = cells[0][0]; winLine[1] = cells[1][1]; winLine[2] = cells[2][2];
        }
        // Diagonal anti
        else if (row + col == 2 && cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player) {
            winLine[0] = cells[0][2]; winLine[1] = cells[1][1]; winLine[2] = cells[2][0];
        }

        if (winLine[0] != null) {
            for (Cell[] r : cells) {
                for (Cell c : r) {
                    c.setHighlighted(false);
                }
            }
            for (Cell c : winLine) {
                if (c != null) c.setHighlighted(true);
            }
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (cells[r][c].content == Seed.NO_SEED) return State.PLAYING;
            }
        }
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
                row == col && cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player ||
                row + col == 2 && cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (cells[r][c].content == Seed.NO_SEED) return State.PLAYING;
            }
        }
        return State.DRAW;
    }

    public Cell[] getWinLine() {
        return winLine;
    }

    public void paint(Graphics g) {
        g.setColor(COLOR_GRID);
        for (int r = 1; r < ROWS; r++) {
            g.fillRoundRect(0, Cell.SIZE * r - GRID_WIDTH_HALF,
                    CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
        }
        for (int c = 1; c < COLS; c++) {
            g.fillRoundRect(Cell.SIZE * c - GRID_WIDTH_HALF, Y_OFFSET,
                    GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
        }
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                cells[r][c].paint(g);
            }
        }
    }
}

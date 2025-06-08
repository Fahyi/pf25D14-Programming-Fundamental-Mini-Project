import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphic version using Board4 and Cell4.
 */
public class GameMain4 extends JPanel {
    private static final long serialVersionUID = 1L;

    // Define constants
    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);  // Red
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // Game components
    private Board4 board;         // the game board
    private State currentState;  // current game state
    private Seed currentPlayer;  // current player
    private JLabel statusBar;    // status message bar

    /** Constructor to setup the UI and game components */
    public GameMain4() {
        // Mouse click listener
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell4.SIZE;
                int col = mouseX / Cell4.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board4.ROWS && col >= 0 && col < Board4.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame4(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    }
                } else {
                    newGame4();  // restart if game over
                }
                repaint(); // redraw UI
            }
        });

        // Setup status bar
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        // Panel layout
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(Board4.CANVAS_WIDTH, Board4.CANVAS_HEIGHT + 30));
        setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Initialize game
        initGame4();
        newGame4();
    }

    /** Initialize the game (run once) */
    public void initGame4() {
        board = new Board4();  // initialize game board
    }

    /** Start a new game */
    public void newGame4() {
        board.newGame4();
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    /** Paint board and status */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint4(g);

        // Update status bar
        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /** Program entry point */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new GameMain4());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // center window
            frame.setVisible(true);
        });
    }
}
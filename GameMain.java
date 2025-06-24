import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMain extends GameBase {

    private AIPlayer aiPlayer; // AI logic jika mode vs AI
    private Timer turnTimer;
    private int countdownSeconds = 5;

    public GameMain(String playerXName, String playerOName) {
        super(playerXName, playerOName);

        if (GameManager.isVsAI) {
            aiPlayer = new AIPlayer(Seed.NOUGHT); // AI selalu main sebagai O
        }

        initGame();
        setupUI();
        newGame();
        updateScoreLabel();
        updateStatusBar();
        startTurnTimer();
    }

    @Override
    public void handleClick(int x, int y) {
        int row = y / Cell.SIZE;
        int col = x / Cell.SIZE;

        if (outOfBounds(row, col)) return;

        if (currentState != State.PLAYING) {
            newGame();
            updateStatusBar();
            boardPanel.repaint();
            return;
        }

        if (board.cells[row][col].content == Seed.NO_SEED && currentPlayer == Seed.CROSS) {
            processMove(row, col);
        } else if (!GameManager.isVsAI && board.cells[row][col].content == Seed.NO_SEED && currentPlayer == Seed.NOUGHT) {
            processMove(row, col);
        }
    }

    private void processMove(int row, int col) {
        currentState = board.stepGame(currentPlayer, row, col);

        if (currentState == State.CROSS_WON) playerXScore++;
        else if (currentState == State.NOUGHT_WON) playerOScore++;

        SoundEffect.MOUSE_CLICK.play(5);
        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

        updateScoreLabel();
        updateStatusBar();
        boardPanel.repaint();

        if (turnTimer != null) turnTimer.stop();

        if (GameManager.isVsAI && currentPlayer == Seed.NOUGHT && currentState == State.PLAYING) {
            aiMove();
        } else if (currentState == State.PLAYING) {
            startTurnTimer();
        }
    }

    private void aiMove() {
        int[] move = aiPlayer.getNextMove(board.getCellContents());
        if (move != null) {
            int row = move[0];
            int col = move[1];

            currentState = board.stepGame(Seed.NOUGHT, row, col);

            if (currentState == State.NOUGHT_WON) playerOScore++;

            currentPlayer = Seed.CROSS;
            SoundEffect.MOUSE_CLICK.play(5);
            updateScoreLabel();
            updateStatusBar();
            boardPanel.repaint();

            if (currentState == State.PLAYING) {
                startTurnTimer();
            }
        }
    }

    private boolean outOfBounds(int row, int col) {
        return row < 0 || row >= Board.ROWS || col < 0 || col >= Board.COLS;
    }

    private void startTurnTimer() {
        countdownSeconds = 5;
        updateStatusBar();

        turnTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusBar.setText("Waktu tersisa: " + countdownSeconds + " detik");
                countdownSeconds--;

                if (countdownSeconds < 0) {
                    ((Timer) e.getSource()).stop();
                    skipTurn();
                }
            }
        });
        turnTimer.start();
    }

    private void skipTurn() {
        statusBar.setText("Waktu habis! Giliran dilewati.");
        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

        updateStatusBar();
        boardPanel.repaint();

        if (GameManager.isVsAI && currentPlayer == Seed.NOUGHT) {
            aiMove();
        } else if (currentState == State.PLAYING) {
            startTurnTimer();
        }
    }
}

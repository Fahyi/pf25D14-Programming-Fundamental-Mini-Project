import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBase extends JPanel {
    public Board board;
    public State currentState;
    public Seed currentPlayer;

    public JLabel statusBar;
    public JLabel scoreLabel;

    public String playerXName;
    public String playerOName;
    public int playerXScore = 0;
    public int playerOScore = 0;

    public BoardPanel boardPanel;
    private JPanel pauseOverlay;

    public GameBase(String playerXName, String playerOName) {
        this.playerXName = playerXName;
        this.playerOName = playerOName;
    }

    /** Initialize the game (run once) */
    public void initGame() {
        this.board = new Board();
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row)
            for (int col = 0; col < Board.COLS; ++col)
                board.cells[row][col].content = Seed.NO_SEED;

        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    public void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 70));
        setBackground(new Color(255, 250, 240)); // background pastel cerah
        setBorder(BorderFactory.createLineBorder(new Color(255, 204, 153), 4)); // garis krem

        // Label skor
        scoreLabel = new JLabel();
        scoreLabel.setFont(GameConstants.FONT_SCORE);
        scoreLabel.setForeground(new Color(70, 40, 40));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Tombol Pause
        JButton pauseButton = new JButton("Pause");
        pauseButton.setMargin(new Insets(4, 14, 4, 14));
        pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pauseButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        pauseButton.setBackground(new Color(255, 204, 153));
        pauseButton.setForeground(Color.DARK_GRAY);
        pauseButton.setFocusPainted(false);
        pauseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pauseButton.setBorder(BorderFactory.createLineBorder(new Color(255, 170, 100), 2));

        pauseButton.addActionListener(e -> showPauseMenu());

        // Panel atas untuk skor
        JPanel topPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, new Color(255, 223, 186),
                        getWidth(), getHeight(), new Color(255, 239, 213)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topPanel.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 45));
        topPanel.add(scoreLabel, BorderLayout.CENTER);

        // Panel status di bawah
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(255, 250, 230));
        statusPanel.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 40));

        statusBar = new JLabel();
        statusBar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        statusBar.setForeground(new Color(70, 50, 50));
        statusBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        statusBar.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusBar, BorderLayout.CENTER);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(pauseButton);
        statusPanel.add(buttonPanel, BorderLayout.EAST);

        // Panel papan permainan
        boardPanel = new BoardPanel(board);
        boardPanel.setBackground(new Color(255, 245, 235));
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        // Tambahkan semuanya
        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }


    public void updateScoreLabel() {
        scoreLabel.setText(playerXName + ": " + playerXScore + "   |   " + playerOName + ": " + playerOScore);
    }

    public void updateStatusBar() {
        if (statusBar == null) return;
        switch (currentState) {
            case PLAYING -> statusBar.setText((currentPlayer == Seed.CROSS ? playerXName : playerOName) + "'s Turn");
            case DRAW -> statusBar.setText("It's a Draw! Click anywhere to play again.");
            case CROSS_WON -> statusBar.setText(playerXName + " Won! Click anywhere to play again.");
            case NOUGHT_WON -> statusBar.setText(playerOName + " Won! Click anywhere to play again.");
        }
    }

    public void handleClick(int x, int y){
        //buat nanti di override sama childrennya
    };


    public void showPauseMenu() {
        this.pauseOverlay = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 100)); // lebih lembut agar tetap ceria
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        pauseOverlay.setLayout(new BoxLayout(pauseOverlay, BoxLayout.Y_AXIS));
        pauseOverlay.setOpaque(false);
        pauseOverlay.setFocusable(true);
        pauseOverlay.requestFocusInWindow();

        // Disable interaksi ke board saat pause
        pauseOverlay.addMouseListener(new MouseAdapter() {});
        pauseOverlay.addMouseMotionListener(new MouseMotionAdapter() {});
        pauseOverlay.addMouseWheelListener(e -> {});

        // Tombol box
        JPanel buttonBox = new JPanel();
        buttonBox.setOpaque(false);
        buttonBox.setLayout(new BoxLayout(buttonBox, BoxLayout.Y_AXIS));

        // Tombol Continue
        JButton continueButton = new JButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        continueButton.setMaximumSize(new Dimension(240, 50));
        continueButton.setBackground(new Color(204, 255, 204));
        continueButton.setForeground(Color.DARK_GRAY);
        continueButton.setBorder(BorderFactory.createLineBorder(new Color(102, 204, 102), 2));
        continueButton.setFocusPainted(false);
        continueButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        continueButton.addActionListener(e -> hidePauseMenu());

        // Tombol Exit
        JButton exitButton = new JButton("Exit to Menu");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        exitButton.setMaximumSize(new Dimension(240, 50));
        exitButton.setBackground(new Color(255, 204, 204));
        exitButton.setForeground(Color.DARK_GRAY);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(255, 102, 102), 2));
        exitButton.setFocusPainted(false);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        exitButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.setContentPane(new StartMenu(topFrame));
            topFrame.setSize(720, 800);
            topFrame.revalidate();
            topFrame.repaint();
        });

        buttonBox.add(Box.createVerticalGlue());
        buttonBox.add(continueButton);
        buttonBox.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonBox.add(exitButton);
        buttonBox.add(Box.createVerticalGlue());

        pauseOverlay.add(Box.createVerticalGlue());
        pauseOverlay.add(buttonBox);
        pauseOverlay.add(Box.createVerticalGlue());

        setLayout(null);
        add(pauseOverlay);

        SwingUtilities.invokeLater(() -> {
            pauseOverlay.setBounds(0, 0, getWidth(), getHeight());
            setComponentZOrder(pauseOverlay, 0);
            revalidate();
            repaint();
        });
    }



    public void hidePauseMenu() {
        if (pauseOverlay != null) {
            remove(pauseOverlay);
            pauseOverlay = null;
            setLayout(new BorderLayout());
            revalidate();
            repaint();
        }
    }
}


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Layar awal (main menu) Tic-Tac-Toe.
 * - Menampilkan logo
 * - Memilih mode permainan  (Player vs Player / Player vs AI)
 * - Tombol Play, Settings, dan Exit
 */
public class StartMenu extends JPanel {

    private JComboBox<String> difficultyCombo; // Tambahan: dropdown untuk memilih level AI

    // ---------- Konstruktor ----------
    public StartMenu(JFrame frame) {
        /* === Layout dasar panel === */
        setLayout(new BorderLayout());
        setBackground(GameConstants.COLOR_BG);

        /* === Bagian tengah : Logo + RadioButton + Tombol Play === */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        addLogo(centerPanel);
        centerPanel.add(Box.createVerticalStrut(30));

        /* ---------- Radio-button Mode Permainan ---------- */
        JRadioButton vsPlayer = createModeRadio("Player vs Player");
        JRadioButton vsAI     = createModeRadio("Player vs AI");
        vsPlayer.setSelected(true);

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(vsPlayer);
        modeGroup.add(vsAI);

        centerPanel.add(vsPlayer);
        centerPanel.add(Box.createVerticalStrut(6));
        centerPanel.add(vsAI);
        centerPanel.add(Box.createVerticalStrut(12));

        /* ---------- Dropdown Tingkat Kesulitan ---------- */
        difficultyCombo = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        difficultyCombo.setMaximumSize(new Dimension(180, 30));
        difficultyCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyCombo.setVisible(false);
        centerPanel.add(difficultyCombo);
        centerPanel.add(Box.createVerticalStrut(20));

        // Toggle visibility dropdown jika pilih vs AI
        vsAI.addActionListener(e -> difficultyCombo.setVisible(true));
        vsPlayer.addActionListener(e -> difficultyCombo.setVisible(false));

        /* ---------- Tombol Play ---------- */
        JButton playButton = createButton("Play Now!", () -> {
            GameManager.isVsAI = vsAI.isSelected();
            startInputName(frame);
        });
        centerPanel.add(playButton);

        add(centerPanel, BorderLayout.CENTER);
        add(createBottomBar(frame), BorderLayout.SOUTH);
    }

    private void addLogo(JPanel parent) {
        URL logoURL = getClass().getClassLoader().getResource("images/tictactoe_logo.png");
        if (logoURL != null) {
            ImageIcon original = new ImageIcon(logoURL);
            Image img = original.getImage().getScaledInstance(360, 360, Image.SCALE_SMOOTH);
            JLabel logo = new JLabel(new ImageIcon(img));
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            parent.add(Box.createVerticalStrut(40));
            parent.add(logo);
        } else {
            JLabel fallback = new JLabel("Tic Tac Toe");
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 36));
            fallback.setForeground(new Color(40, 40, 40));
            fallback.setAlignmentX(Component.CENTER_ALIGNMENT);
            parent.add(Box.createVerticalStrut(40));
            parent.add(fallback);
        }
    }

    private JRadioButton createModeRadio(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setOpaque(false);
        rb.setForeground(new Color(45, 45, 45));
        rb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rb.setAlignmentX(Component.CENTER_ALIGNMENT);
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return rb;
    }

    private JButton createButton(String text, Runnable onClick) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        btn.setBackground(new Color(255, 223, 100));
        btn.setForeground(new Color(50, 50, 50));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 0), 2));
        btn.setMaximumSize(new Dimension(240, 55));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { SoundEffect.HOVER.play(); }
        });
        btn.addActionListener(e -> { SoundEffect.HOVER.play(); onClick.run(); });
        return btn;
    }

    private JPanel createBottomBar(JFrame frame) {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JButton gearBtn;
        URL gearURL = getClass().getClassLoader().getResource("images/gear_icon.png");
        if (gearURL != null) {
            ImageIcon gearIcon = new ImageIcon(new ImageIcon(gearURL).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            gearBtn = new JButton(gearIcon);
        } else {
            gearBtn = new JButton("Options");
        }
        gearBtn.setPreferredSize(new Dimension(60, 40));
        gearBtn.setContentAreaFilled(false);
        gearBtn.setBorderPainted(false);
        gearBtn.setFocusPainted(false);
        gearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gearBtn.addActionListener(e -> {
            frame.setContentPane(new SettingsMenu(frame));
            frame.revalidate(); frame.repaint();
        });

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        left.setOpaque(false);
        left.add(gearBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(new Dimension(100, 40));
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(e -> System.exit(0));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        right.setOpaque(false);
        right.add(exitBtn);

        bottom.add(left,  BorderLayout.WEST);
        bottom.add(right, BorderLayout.EAST);
        return bottom;
    }

    private void startInputName(JFrame frame) {
        if (GameManager.isVsAI) {
            JRadioButton asX = new JRadioButton("Play as X");
            JRadioButton asO = new JRadioButton("Play as O");
            ButtonGroup symbolGroup = new ButtonGroup();
            symbolGroup.add(asX);
            symbolGroup.add(asO);
            asX.setSelected(true);

            JPanel symbolPanel = new JPanel(new GridLayout(3, 1, 5, 5));
            symbolPanel.add(new JLabel("Choose your symbol:"));
            symbolPanel.add(asX);
            symbolPanel.add(asO);

            int symbolChoice = JOptionPane.showConfirmDialog(
                    frame, symbolPanel, "Choose symbol", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (symbolChoice != JOptionPane.OK_OPTION) return;

            boolean playerAsX = asX.isSelected();

            JTextField nameField = new JTextField(15);
            JPanel namePanel = new JPanel(new GridLayout(2, 1, 10, 10));
            namePanel.add(new JLabel("Input your name:"));
            namePanel.add(nameField);

            int nameResult = JOptionPane.showConfirmDialog(
                    frame, namePanel, "Player Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (nameResult != JOptionPane.OK_OPTION) return;

            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) playerName = playerAsX ? "Player X" : "Player O";

            String selected = (String) difficultyCombo.getSelectedItem();
            Difficulty difficulty = switch (selected) {
                case "Easy" -> Difficulty.EASY;
                case "Medium" -> Difficulty.MEDIUM;
                case "Hard" -> Difficulty.HARD;
                default -> Difficulty.MEDIUM;
            };

            frame.setContentPane(new GameMain(playerName, playerAsX, difficulty));
            frame.setSize(400, 400);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } else {
            JTextField pXField = new JTextField("Player X", 15);
            JTextField pOField = new JTextField("Player O", 15);

            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.add(new JLabel("Enter name for Player X:"));
            panel.add(pXField);
            panel.add(new JLabel("Enter name for Player O:"));
            panel.add(pOField);

            int result = JOptionPane.showConfirmDialog(
                    frame, panel, "Enter Player Names",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;

            String pX = pXField.getText().trim();
            String pO = pOField.getText().trim();
            if (pX.isEmpty()) pX = "Player X";
            if (pO.isEmpty()) pO = "Player O";

            frame.setContentPane(new GameMain(pX, pO));
            frame.setSize(400, 400);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}

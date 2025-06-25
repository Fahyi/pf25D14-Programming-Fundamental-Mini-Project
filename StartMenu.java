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

    // ---------- Konstruktor ----------
    public StartMenu(JFrame frame) {
        /* === Layout dasar panel === */
        setLayout(new BorderLayout());
        setBackground(GameConstants.COLOR_BG);     // warna krem milikmu

        /* === Bagian tengah : Logo + RadioButton + Tombol Play === */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        addLogo(centerPanel);                     // Tambahkan logo
        centerPanel.add(Box.createVerticalStrut(30));

        /* ---------- Radio-button Mode Permainan ---------- */
        // gunakan helper agar tulisan pasti terlihat
        JRadioButton vsPlayer = createModeRadio("Player vs Player");
        JRadioButton vsAI     = createModeRadio("Player vs AI");
        vsPlayer.setSelected(true);               // default

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(vsPlayer);
        modeGroup.add(vsAI);

        centerPanel.add(vsPlayer);
        centerPanel.add(Box.createVerticalStrut(6));
        centerPanel.add(vsAI);
        centerPanel.add(Box.createVerticalStrut(26));

        /* ---------- Tombol Play ---------- */
        JButton playButton = createButton("Play Now!", () -> {
            GameManager.isVsAI = vsAI.isSelected();   // simpan preferensi
            startInputName(frame);                    // lanjut ke dialog nama
        });
        centerPanel.add(playButton);

        add(centerPanel, BorderLayout.CENTER);

        /* === Bagian bawah : Settings & Exit === */
        add(createBottomBar(frame), BorderLayout.SOUTH);
    }

    // ==========================================================
    //  Helper – Logo
    // ==========================================================
    private void addLogo(JPanel parent) {
        URL logoURL = getClass().getClassLoader().getResource("images/tictactoe_logo.png");
        if (logoURL != null) {
            ImageIcon original = new ImageIcon(logoURL);
            Image img = original.getImage().getScaledInstance(360, 360, Image.SCALE_SMOOTH);
            JLabel logo = new JLabel(new ImageIcon(img));
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            parent.add(Box.createVerticalStrut(40));
            parent.add(logo);
        } else {                                   // fallback jika logo tak ketemu
            JLabel fallback = new JLabel("Tic Tac Toe");
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 36));
            fallback.setForeground(new Color(40, 40, 40));
            fallback.setAlignmentX(Component.CENTER_ALIGNMENT);
            parent.add(Box.createVerticalStrut(40));
            parent.add(fallback);
        }
    }

    // ==========================================================
    //  Helper – Radio-button mode
    // ==========================================================
    private JRadioButton createModeRadio(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setOpaque(false);                        // transparan agar warna bg tetap krem
        rb.setForeground(new Color(45, 45, 45));   // <–– teks gelap, kontras
        rb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rb.setAlignmentX(Component.CENTER_ALIGNMENT);
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return rb;
    }

    // ==========================================================
    //  Helper – Tombol generik
    // ==========================================================
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

    // ==========================================================
    //  Helper – Bottom bar (gear & exit)
    // ==========================================================
    private JPanel createBottomBar(JFrame frame) {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        // ---- tombol gear (Settings) ----
        JButton gearBtn;
        URL gearURL = getClass().getClassLoader().getResource("images/gear_icon.png");
        if (gearURL != null) {
            ImageIcon gearIcon = new ImageIcon(
                    new ImageIcon(gearURL).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
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

        // ---- tombol Exit ----
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

    // ==========================================================
    //  Dialog input nama pemain & start game
    // ==========================================================
    private void startInputName(JFrame frame) {
        if (GameManager.isVsAI) {
            // === MODE: PLAYER vs AI ===
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

            // Input nama pemain
            JTextField nameField = new JTextField(15);
            JPanel namePanel = new JPanel(new GridLayout(2, 1, 10, 10));
            namePanel.add(new JLabel("Input your name:"));
            namePanel.add(nameField);

            int nameResult = JOptionPane.showConfirmDialog(
                    frame, namePanel, "Player Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (nameResult != JOptionPane.OK_OPTION) return;

            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) playerName = playerAsX ? "Player X" : "Player O";

            frame.setContentPane(new GameMain(playerName, playerAsX));
            frame.setSize(400, 400);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } else {
            // === MODE: PLAYER vs PLAYER ===
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
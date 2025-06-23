import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class StartMenu extends JPanel {
    public StartMenu(JFrame frame) {

        setLayout(new BorderLayout());
        setBackground(GameConstants.COLOR_BG);

        // tombol dan logo
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // logo tic tac toe

        URL logoURL = getClass().getClassLoader().getResource("images/tictactoe_logo.png");
        if (logoURL != null) {
            ImageIcon originalLogo = new ImageIcon(logoURL);
            Image scaledImage = originalLogo.getImage().getScaledInstance(360, 360, Image.SCALE_SMOOTH);
            ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
            JLabel logoLabel = new JLabel(scaledLogoIcon);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(Box.createVerticalStrut(30));
            centerPanel.add(logoLabel);

        } else {
            System.err.println("Logo image not found!");
            JLabel fallback = new JLabel("Tic Tac Toe");
            fallback.setFont(new Font("SegoeUI", Font.BOLD, 36));
            fallback.setForeground(Color.WHITE);
            fallback.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(Box.createVerticalStrut(30));
            centerPanel.add(fallback);
        }

        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(createButton("Play Now!", new Color(2,21,38), () -> startDuoGame(frame)));
        centerPanel.add(Box.createVerticalStrut(15));


        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        leftPanel.setOpaque(false);

        URL gearURL = getClass().getClassLoader().getResource("images/gear_icon.png");
        JButton btnOptions;
        if (gearURL != null) {
            ImageIcon originalIcon = new ImageIcon(gearURL);
            Image scaledImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            btnOptions = new JButton(scaledIcon);
        } else {
            System.err.println("Icon not found!!");
            btnOptions = new JButton("Options");
        }
        btnOptions.setPreferredSize(new Dimension(60, 40));
        btnOptions.setContentAreaFilled(false);
        btnOptions.setBorderPainted(false);
        btnOptions.setFocusPainted(false);
        btnOptions.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnOptions.addActionListener(e -> startSettingsMenu(frame));
        leftPanel.add(btnOptions);

        // Panel kanan untuk tombol exit
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setOpaque(false);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("SegoeUI", Font.BOLD, 16));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(100, 40));
        exitButton.addActionListener(e -> System.exit(0));
        rightPanel.add(exitButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

    }

    private void startSettingsMenu(JFrame frame) {
        frame.setContentPane(new SettingsMenu(frame));
        frame.revalidate();
        frame.repaint();

    }

    private JButton createButton(String text, Color color, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        button.setMaximumSize(new Dimension(240, 55));

        button.setBackground(new Color(255, 223, 100));
        button.setForeground(new Color(50, 50, 50));

        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 0), 2));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundEffect.HOVER.play();
            }
        });

        button.addActionListener(e -> {
            SoundEffect.HOVER.play();
            action.run();
        });

        return button;
    }


    private void startDuoGame(JFrame frame) {
        JTextField playerXField = new JTextField("Player X", 15);
        JTextField playerOField = new JTextField("Player O", 15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Enter name for Player X:"));
        panel.add(playerXField);
        panel.add(new JLabel("Enter name for Player O:"));
        panel.add(playerOField);

        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Enter Player Names",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        String playerX = "Player X";
        String playerO = "Player O";

        if (result == JOptionPane.OK_OPTION) {
            String inputX = playerXField.getText().trim();
            String inputO = playerOField.getText().trim();

            if (!inputX.isEmpty()) playerX = inputX;
            if (!inputO.isEmpty()) playerO = inputO;

            frame.setContentPane(new GameMain(playerX, playerO));
            frame.setSize(400,400);

            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }else{
            frame.setContentPane(new StartMenu(frame));
            frame.revalidate();
            frame.repaint();
        }


    }




}
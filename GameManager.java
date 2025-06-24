import javax.swing.*;

public class GameManager {
    public static final String TITLE = "Tic Tac Toe";
    public static boolean isVsAI = false; // Menyimpan mode permainan

    public static void main(String[] args) {
        SoundEffect.playBGMusic(); // Musik latar

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new StartMenu(frame)); // Start menu UI
            frame.pack();
            frame.setSize(720, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Tambahan penting untuk efek blur dan highlight
    public static boolean isGameOver() {
        State state = GameBase.getCurrentState();  // Dapatkan state terbaru dari GameBase
        return state == State.CROSS_WON || state == State.NOUGHT_WON || state == State.DRAW;
    }
}

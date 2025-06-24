import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.util.Hashtable;

public class SettingsMenu extends JPanel {
    public class VolumesMenu extends JPanel {
        JLabel SFXVolumeLabel;
        JSlider SFXVolume;

        JLabel musicVolumeLabel;
        JSlider musicVolume;

        public VolumesMenu() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(GameConstants.COLOR_BG);
            add(Box.createRigidArea(new Dimension(0, 20)));

            // SFX Label
            SFXVolumeLabel = new JLabel("SFX Volume");
            SFXVolumeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            SFXVolumeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            SFXVolumeLabel.setForeground(Color.DARK_GRAY);
            SFXVolumeLabel.setOpaque(true);
            SFXVolumeLabel.setBackground(new Color(255, 255, 204));
            SFXVolumeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // SFX Slider
            SFXVolume = new JSlider(0, 100);
            SFXVolume.setValue((int) SoundEffect.sfxVolume);
            SFXVolume.setBackground(new Color(255, 250, 230));
            SFXVolume.setPaintTicks(true);
            SFXVolume.setPaintLabels(true);
            SFXVolume.setMajorTickSpacing(100);
            SFXVolume.setMinorTickSpacing(0);
            Hashtable<Integer, JLabel> sfxLabels = new Hashtable<>();
            sfxLabels.put(0, new JLabel("0"));
            sfxLabels.put(100, new JLabel("100"));
            SFXVolume.setLabelTable(sfxLabels);
            SFXVolume.addChangeListener(e -> {
                int value = SFXVolume.getValue();
                SFXVolume.setToolTipText(value + " %");
                SoundEffect.updateAllSFXVolume(value);
            });

            // Music Label
            musicVolumeLabel = new JLabel("Music Volume");
            musicVolumeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            musicVolumeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            musicVolumeLabel.setForeground(Color.DARK_GRAY);
            musicVolumeLabel.setOpaque(true);
            musicVolumeLabel.setBackground(new Color(204, 255, 229));
            musicVolumeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // Music Slider
            musicVolume = new JSlider(0, 100);
            musicVolume.setValue((int) SoundEffect.musicVolume);
            musicVolume.setBackground(new Color(240, 255, 250));
            musicVolume.setPaintTicks(true);
            musicVolume.setPaintLabels(true);
            musicVolume.setMajorTickSpacing(100);
            musicVolume.setMinorTickSpacing(0);
            Hashtable<Integer, JLabel> musicLabels = new Hashtable<>();
            musicLabels.put(0, new JLabel("0"));
            musicLabels.put(100, new JLabel("100"));
            musicVolume.setLabelTable(musicLabels);
            musicVolume.setToolTipText(musicVolume.getValue() + "%");
            musicVolume.addChangeListener(e -> {
                int value = musicVolume.getValue();
                musicVolume.setToolTipText(value + "%");
                SoundEffect.updateBGMusicVolume(value);
            });
            musicVolume.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    musicVolume.setToolTipText(musicVolume.getValue() + "%");
                    ToolTipManager.sharedInstance().mouseMoved(
                            new MouseEvent(musicVolume, 0, 0, 0, e.getX(), e.getY(), 0, false));
                }
            });

            add(SFXVolumeLabel);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(SFXVolume);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(musicVolumeLabel);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(musicVolume);


        }
    }

    public class IconMenu extends JPanel {
        JLabel XIconLabel;
        JButton XIconBtn;
        JLabel OIconLabel;
        JButton OIconBtn;

        public IconMenu() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);

            XIconLabel = new JLabel("None", SwingConstants.CENTER);
            OIconLabel = new JLabel("None", SwingConstants.CENTER);

            XIconBtn = new JButton("Set X Icon");
            OIconBtn = new JButton("Set O Icon");

            XIconBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String path = getSelectedFilePath();
                    if (!path.isEmpty()) {
                        Seed.CROSS.changeIcon(path);
                        XIconLabel.setText(path);
                    }
                }
            });

            OIconBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String path = getSelectedFilePath();
                    if (!path.isEmpty()) {
                        Seed.NOUGHT.changeIcon(path);
                        OIconLabel.setText(path);
                    }
                }
            });

            XIconBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            XIconBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            XIconBtn.setMaximumSize(new Dimension(240, 50));
            XIconBtn.setBackground(new Color(173, 216, 230));
            XIconBtn.setForeground(Color.BLACK);
            XIconBtn.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 235), 2));

            XIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            XIconLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
            XIconLabel.setForeground(Color.WHITE);

            OIconBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            OIconBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            OIconBtn.setMaximumSize(new Dimension(240, 50));
            OIconBtn.setBackground(new Color(255, 182, 193));
            OIconBtn.setForeground(Color.BLACK);
            OIconBtn.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2));

            OIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            OIconLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
            OIconLabel.setForeground(Color.WHITE);

            add(Box.createVerticalGlue());
            add(XIconBtn);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(XIconLabel);
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(OIconBtn);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(OIconLabel);
            add(Box.createVerticalGlue());
        }

        private String getSelectedFilePath() {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Choose Icon");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile().getPath();
            }
            return "";
        }
    }

    JLabel settingsTitle;
    JPanel menus;
    JButton backBtn;

    public SettingsMenu(JFrame frame) {
        setLayout(new BorderLayout());
        setBackground(GameConstants.COLOR_BG);

        settingsTitle = new JLabel("Option Menu", SwingConstants.CENTER);
        settingsTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        settingsTitle.setForeground(new Color(40, 40, 40));
        settingsTitle.setOpaque(true);
        settingsTitle.setBackground(new Color(255, 223, 186));
        settingsTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.ORANGE));
        settingsTitle.setPreferredSize(new Dimension(0, 60));

        backBtn = new JButton("Back to Main Menu");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        backBtn.setMaximumSize(new Dimension(240, 50));
        backBtn.setBackground(new Color(255, 200, 100));
        backBtn.setForeground(Color.DARK_GRAY);
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(255, 170, 60), 2));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backBtn.addActionListener(e -> {
            frame.setContentPane(new StartMenu(frame));
            frame.revalidate();
            frame.repaint();
        });

        VolumesMenu volumes = new VolumesMenu();
        IconMenu iconmenu = new IconMenu();
        iconmenu.setBackground(GameConstants.COLOR_BG);

        menus = new JPanel();
        menus.setLayout(new BoxLayout(menus, BoxLayout.Y_AXIS));
        menus.setBackground(GameConstants.COLOR_BG);
        menus.add(volumes);
        menus.add(Box.createRigidArea(new Dimension(0, 20)));
        menus.add(iconmenu);

        add(settingsTitle, BorderLayout.NORTH);
        add(menus, BorderLayout.CENTER);
        add(backBtn, BorderLayout.SOUTH);
    }
}
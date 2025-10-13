package edu.hitsz.Frame;

import edu.hitsz.application.Main;
import edu.hitsz.application.Game;
import edu.hitsz.application.MusicManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DifficultyPanel extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JCheckBox musicCheckBox;

    public DifficultyPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("选择难度");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 80, 0));

        add(titleLabel);

        // 音乐开关
        musicCheckBox = new JCheckBox("开启音乐", MusicManager.getInstance().isSoundEnabled());
        musicCheckBox.setForeground(Color.WHITE);
        musicCheckBox.setBackground(Color.BLACK);
        musicCheckBox.setFont(new Font("宋体", Font.PLAIN, 20));
        musicCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        musicCheckBox.addActionListener(e -> {
            MusicManager.getInstance().setSoundEnabled(musicCheckBox.isSelected());
        });
        add(musicCheckBox);
        add(Box.createVerticalStrut(30));

        // 难度按钮
        JButton easyBtn = createDifficultyButton("简单", "easy");
        JButton normalBtn = createDifficultyButton("普通", "normal");
        JButton hardBtn = createDifficultyButton("困难", "hard");

        add(Box.createVerticalStrut(30));
        add(easyBtn);
        add(Box.createVerticalStrut(30));
        add(normalBtn);
        add(Box.createVerticalStrut(30));
        add(hardBtn);
    }

    private JButton createDifficultyButton(String text, String difficulty) {
        JButton button = new JButton(text);
        button.setFont(new Font("宋体", Font.PLAIN, 24));
        button.setPreferredSize(new Dimension(200, 60));
        button.setMaximumSize(new Dimension(200, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener((ActionEvent e) -> {
            // 保存音乐设置
            MusicManager.getInstance().setSoundEnabled(musicCheckBox.isSelected());

            // 获取游戏面板并设置难度
            Game game = (Game) mainPanel.getComponent(1);
            game.setDifficulty(difficulty);
            // 切换到游戏界面
            cardLayout.show(mainPanel, "game");
            // 启动游戏
            game.action();
        });
        return button;
    }
}
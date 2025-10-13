package edu.hitsz.application;

import edu.hitsz.Frame.DifficultyPanel;
import edu.hitsz.Frame.ScoreboardPanel;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final String DIFFICULTY_EASY = "easy";
    public static final String DIFFICULTY_NORMAL = "normal";
    public static final String DIFFICULTY_HARD = "hard";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("游戏");
            frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 创建CardLayout和主面板
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);

            // 初始化三个核心面板
            DifficultyPanel difficultyPanel = new DifficultyPanel(cardLayout, mainPanel);
            Game gamePanel = new Game(cardLayout, mainPanel);
            ScoreboardPanel scoreboardPanel = new ScoreboardPanel(cardLayout, mainPanel);

            // 加入布局管理器（命名与跳转对应）
            mainPanel.add(difficultyPanel, "difficulty"); // 难度选择面板
            mainPanel.add(gamePanel, "game"); // 游戏面板
            mainPanel.add(scoreboardPanel, "scoreboard"); // 计分板面板

            frame.add(mainPanel);
            frame.setVisible(true);

            // 初始显示难度选择面板
            cardLayout.show(mainPanel, "difficulty");
        });
    }
}
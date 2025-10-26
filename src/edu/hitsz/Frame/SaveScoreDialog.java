package edu.hitsz.Frame;

import edu.hitsz.application.Score;
import edu.hitsz.application.ScoreDaoImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class SaveScoreDialog extends JDialog {
    private final int score;
    private final String difficulty;
    private JTextField playerIdField;

    // 构造方法：接收分数和难度
    public SaveScoreDialog(JFrame parent, int score, String difficulty) {
        super(parent, "游戏结束", true); // 模态对话框（阻塞父窗口）
        this.score = score;
        this.difficulty = difficulty;
        initUI();
        setLocationRelativeTo(parent); // 相对于游戏窗口居中
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 显示当前分数
        panel.add(new JLabel("你的得分: " + score));

        // 玩家ID输入
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.add(new JLabel("输入玩家ID: "));
        playerIdField = new JTextField(15);
        idPanel.add(playerIdField);
        panel.add(idPanel);

        // 按钮面板
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("保存分数");
        JButton cancelBtn = new JButton("不保存");

        // 保存按钮逻辑
        saveBtn.addActionListener((ActionEvent e) -> {
            String playerId = playerIdField.getText().trim();
            if (playerId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入玩家ID");
                return;
            }
            // 保存分数（包含难度信息）
            new ScoreDaoImpl().saveScore(new Score(
                    playerId,
                    score,
                    new Date(),
                    difficulty
            ));
            dispose(); // 关闭对话框
        });

        // 不保存按钮逻辑
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        panel.add(btnPanel);

        add(panel);
        pack(); // 自适应大小
    }
}
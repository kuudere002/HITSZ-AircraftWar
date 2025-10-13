package edu.hitsz.Frame;

import edu.hitsz.application.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class SaveScoreDialog extends JDialog {
    private final int currentScore;
    private JTextField nameField;

    public SaveScoreDialog(Frame parent, int score) {
        super(parent, "保存分数", true);
        this.currentScore = score;
        initUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("你的得分: " + currentScore));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("请输入姓名: "));
        nameField = new JTextField(15);
        namePanel.add(nameField);
        panel.add(namePanel);

        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("保存");
        JButton cancelBtn = new JButton("取消");

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入姓名");
                return;
            }

            // 保存分数
            new ScoreDaoImpl().saveScore(new Score(name, currentScore, new Date()));
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        panel.add(btnPanel);

        add(panel);
    }
}
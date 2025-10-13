package edu.hitsz.Frame;

import edu.hitsz.application.Score;
import edu.hitsz.application.ScoreDaoImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScoreboardPanel extends JPanel {
    private final ScoreDaoImpl scoreDao = new ScoreDaoImpl();
    private DefaultTableModel tableModel;
    private String currentDifficulty;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JTable table; // 添加表格引用

    public ScoreboardPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 标题
        JLabel titleLabel = new JLabel("得分排行榜", SwingConstants.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // 表格
        String[] columns = {"名次", "玩家ID", "得分", "记录时间"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel); // 初始化表格并保存引用
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel();

        // 删除按钮
        JButton deleteBtn = new JButton("删除选中记录");
        deleteBtn.addActionListener(e -> deleteSelectedScore());
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // 删除选中的分数记录
    private void deleteSelectedScore() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的记录");
            return;
        }

        // 获取选中行的分数信息
        String playerName = (String) tableModel.getValueAt(selectedRow, 1);
        int scoreValue = (int) tableModel.getValueAt(selectedRow, 2);
        String timeStr = (String) tableModel.getValueAt(selectedRow, 3);

        // 查找对应的Score对象
        List<Score> scores = scoreDao.getScoresByDifficulty(currentDifficulty);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Score score : scores) {
            try {
                if (score.getPlayerName().equals(playerName) &&
                        score.getScore() == scoreValue &&
                        sdf.format(score.getRecordTime()).equals(timeStr)) {

                    // 执行删除
                    scoreDao.deleteScore(score);
                    refreshScores(); // 刷新表格
                    JOptionPane.showMessageDialog(this, "记录已删除");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setDifficulty(String difficulty) {
        this.currentDifficulty = difficulty;
        ((JLabel) getComponent(0)).setText(difficulty + " 难度 得分排行榜");
    }

    public void refreshScores() {
        tableModel.setRowCount(0);
        List<Score> scores = scoreDao.getScoresByDifficulty(currentDifficulty);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    score.getPlayerName(),
                    score.getScore(),
                    sdf.format(score.getRecordTime())
            });
        }
    }
}
package edu.hitsz.Frame;

import edu.hitsz.application.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScoreboardFrame extends JFrame {
    private final ScoreDao scoreDao;
    private JTable scoreTable;
    private DefaultTableModel tableModel;

    public ScoreboardFrame() {
        scoreDao = new ScoreDaoImpl();
        initUI();
        loadScores();
    }

    private void initUI() {
        setTitle("得分排行榜");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 表格模型
        String[] columns = {"名次", "玩家名", "得分", "记录时间"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 创建表格
        scoreTable = new JTable(tableModel);
        add(new JScrollPane(scoreTable), BorderLayout.CENTER);

        // 底部按钮
        JPanel bottomPanel = new JPanel();
        JButton deleteBtn = new JButton("删除选中记录");
        deleteBtn.addActionListener(this::deleteSelectedScore);
        bottomPanel.add(deleteBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadScores() {
        tableModel.setRowCount(0); // 清空表格
        List<Score> scores = scoreDao.getAllScores();
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

    private void deleteSelectedScore(ActionEvent e) {
        int selectedRow = scoreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的记录");
            return;
        }

        // 获取选中的分数信息
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        int scoreValue = (int) tableModel.getValueAt(selectedRow, 2);
        String timeStr = (String) tableModel.getValueAt(selectedRow, 3);

        try {
            Score score = new Score(name, scoreValue, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeStr));
            scoreDao.deleteScore(score);
            loadScores(); // 重新加载数据
            JOptionPane.showMessageDialog(this, "删除成功");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "删除失败");
            ex.printStackTrace();
        }
    }
}
package edu.hitsz.application;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScoreDaoImpl implements ScoreDao {
    private static final String SCORE_FILE = "scores.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void saveScore(Score score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            String line = String.format("%s,%d,%s",
                    score.getPlayerName(),
                    score.getScore(),
                    sdf.format(score.getRecordTime()));
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();
        File file = new File(SCORE_FILE);

        if (!file.exists()) return scores;

        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        scores.add(new Score(
                                parts[0],
                                Integer.parseInt(parts[1]),
                                sdf.parse(parts[2])
                        ));
                    } catch (ParseException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 按分数降序排序
        scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
        return scores;
    }

    @Override
    public void deleteScore(Score score) {
        List<Score> allScores = getAllScores();
        allScores.removeIf(s ->
                s.getPlayerName().equals(score.getPlayerName()) &&
                        s.getScore() == score.getScore() &&
                        s.getRecordTime().equals(score.getRecordTime())
        );

        // 重新写入所有数据
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            for (Score s : allScores) {
                writer.write(String.format("%s,%d,%s",
                        s.getPlayerName(),
                        s.getScore(),
                        sdf.format(s.getRecordTime())));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
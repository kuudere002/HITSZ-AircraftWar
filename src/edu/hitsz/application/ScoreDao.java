package edu.hitsz.application;

import java.util.List;

public interface ScoreDao {
    void saveScore(Score score);
    List<Score> getAllScores();
    List<Score> getScoresByDifficulty(String difficulty); // 新增按难度查询
    void deleteScore(Score score);
}
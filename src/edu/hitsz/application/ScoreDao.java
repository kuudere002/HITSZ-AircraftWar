package edu.hitsz.application;

import java.util.List;

public interface ScoreDao {
    void saveScore(Score score);
    List<Score> getAllScores();
    void deleteScore(Score score);
}
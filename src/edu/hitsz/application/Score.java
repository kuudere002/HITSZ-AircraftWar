package edu.hitsz.application;

import java.util.Date;

public class Score {
    private String playerName;
    private int score;
    private Date recordTime;
    private String difficulty;

    public Score(String playerName, int score, Date recordTime, String difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.recordTime = recordTime;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public Date getRecordTime() { return recordTime; }
    public String getDifficulty() { return difficulty; }
}
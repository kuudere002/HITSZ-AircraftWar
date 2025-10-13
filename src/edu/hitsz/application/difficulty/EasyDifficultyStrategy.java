package edu.hitsz.application.difficulty;

public class EasyDifficultyStrategy implements DifficultyStrategy {

    @Override
    public boolean shouldSpawnBoss(int score, int bossCount) {
        return false; // 简单难度无BOSS
    }

    @Override
    public int getMaxEnemyNumber(int time) {
        return 4; // 简单难度敌机数量较少
    }

    @Override
    public EnemyProperties getMobEnemyProperties(int time) {
        return new EnemyProperties(30, 0, 5, 10);
    }

    @Override
    public EnemyProperties getEliteEnemyProperties(int time) {
        return new EnemyProperties(60, 2, 4, 15);
    }

    @Override
    public EnemyProperties getElitePlusEnemyProperties(int time) {
        return new EnemyProperties(80, 3, 4, 20);
    }

    @Override
    public BossProperties getBossEnemyProperties(int bossCount, int time) {
        return null; // 简单难度无BOSS
    }

    @Override
    public int getHeroInitialHp() {
        return 5000; // 简单难度英雄机生命值适中
    }

    @Override
    public int getHeroShootInterval(int time) {
        return 1000; // 射击间隔固定
    }

    @Override
    public int getEnemyShootInterval(int time) {
        return 1500; // 敌机射击频率较低
    }

    @Override
    public double getEliteSpawnProbability(int time) {
        return 0.3; // 精英敌机出现概率较低
    }

    @Override
    public int getEnemySpawnInterval(int time) {
        return 800; // 敌机生成速度固定
    }

    @Override
    public int getBossSpawnScoreThreshold(int bossCount) {
        return Integer.MAX_VALUE; // 简单难度不会出现BOSS
    }
}
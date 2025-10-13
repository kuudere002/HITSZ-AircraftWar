package edu.hitsz.application.difficulty;

public class HardDifficultyStrategy implements DifficultyStrategy {

    @Override
    public boolean shouldSpawnBoss(int score, int bossCount) {
        return score >= getBossSpawnScoreThreshold(bossCount);
    }

    @Override
    public int getMaxEnemyNumber(int time) {
        // 随时间增加，最大敌机数量增加更多
        return Math.min(8, 5 + time / 60000);
    }

    @Override
    public EnemyProperties getMobEnemyProperties(int time) {
        int hp = 35 + Math.min(45, time / 90000 * 15);
        int speedY = 6 + Math.min(5, time / 90000 * 2);
        return new EnemyProperties(hp, 0, speedY, 15);
    }

    @Override
    public EnemyProperties getEliteEnemyProperties(int time) {
        int hp = 70 + Math.min(100, time / 90000 * 30);
        int speedX = 3 + Math.min(3, time / 90000 * 1);
        int speedY = 5 + Math.min(4, time / 90000 * 2);
        return new EnemyProperties(hp, speedX, speedY, 20);
    }

    @Override
    public EnemyProperties getElitePlusEnemyProperties(int time) {
        int hp = 90 + Math.min(120, time / 90000 * 40);
        int speedX = 4 + Math.min(4, time / 90000 * 2);
        int speedY = 5 + Math.min(4, time / 90000 * 2);
        return new EnemyProperties(hp, speedX, speedY, 25);
    }

    @Override
    public BossProperties getBossEnemyProperties(int bossCount, int time) {
        // 困难模式BOSS血量增长更快
        int baseHp = 10000 + time / 120000 * 5000;
        int hp = baseHp + bossCount * 5000;
        return new BossProperties(hp, 3, 0, 20, 20, hp);
    }

    @Override
    public int getHeroInitialHp() {
        return 2000; // 困难难度英雄机生命值较低
    }

    @Override
    public int getHeroShootInterval(int time) {
        return Math.max(500, 500 - time / 90000 * 60);
    }

    @Override
    public int getEnemyShootInterval(int time) {
        return Math.max(1000, 1300 - time / 120000 * 100);
    }

    @Override
    public double getEliteSpawnProbability(int time) {
        return Math.min(0.7, 0.4 + time / 90000 * 0.15);
    }

    @Override
    public int getEnemySpawnInterval(int time) {
        return Math.max(400, 700 - time / 90000 * 150);
    }

    @Override
    public int getBossSpawnScoreThreshold(int bossCount) {
        return 200 + bossCount * 8000; // BOSS出现更频繁
    }
}
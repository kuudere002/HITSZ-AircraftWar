package edu.hitsz.application.difficulty;

public class NormalDifficultyStrategy implements DifficultyStrategy {

    @Override
    public boolean shouldSpawnBoss(int score, int bossCount) {
        return score >= getBossSpawnScoreThreshold(bossCount);
    }

    @Override
    public int getMaxEnemyNumber(int time) {
        // 随时间增加，最大敌机数量逐渐增加
        return Math.min(6, 4 + time / 60000);
    }

    @Override
    public EnemyProperties getMobEnemyProperties(int time) {
        int hp = 30 + Math.min(30, time / 120000 * 10);
        int speedY = 5 + Math.min(3, time / 120000 * 1);
        return new EnemyProperties(hp, 0, speedY, 10);
    }

    @Override
    public EnemyProperties getEliteEnemyProperties(int time) {
        int hp = 60 + Math.min(60, time / 120000 * 20);
        int speedX = 2 + Math.min(2, time / 120000 * 1);
        int speedY = 4 + Math.min(3, time / 120000 * 1);
        return new EnemyProperties(hp, speedX, speedY, 15);
    }

    @Override
    public EnemyProperties getElitePlusEnemyProperties(int time) {
        int hp = 80 + Math.min(80, time / 120000 * 25);
        int speedX = 3 + Math.min(3, time / 120000 * 1);
        int speedY = 4 + Math.min(3, time / 120000 * 1);
        return new EnemyProperties(hp, speedX, speedY, 20);
    }

    @Override
    public BossProperties getBossEnemyProperties(int bossCount, int time) {
        int hp = 10000;
        return new BossProperties(hp, 2, 0, 15, 15, hp);
    }

    @Override
    public int getHeroInitialHp() {
        return 3500; // 普通难度英雄机生命值稍低
    }

    @Override
    public int getHeroShootInterval(int time) {
        // 随时间减少射击间隔（提高射速）
        return Math.max(500, 700 - time / 120000 * 50);
    }

    @Override
    public int getEnemyShootInterval(int time) {
        // 随时间减少射击间隔（提高敌机射速）
        return Math.max(1000, 1300 - time / 120000 * 100);
    }

    @Override
    public double getEliteSpawnProbability(int time) {
        // 随时间增加精英敌机出现概率
        return Math.min(0.5, 0.3 + time / 120000 * 0.1);
    }

    @Override
    public int getEnemySpawnInterval(int time) {
        // 随时间减少生成间隔（加快敌机生成）
        return Math.max(500, 800 - time / 120000 * 100);
    }

    @Override
    public int getBossSpawnScoreThreshold(int bossCount) {
        return 300 + bossCount * 10000; // BOSS出现的分数阈值逐渐提高
    }
}
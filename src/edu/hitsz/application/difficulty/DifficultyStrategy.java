package edu.hitsz.application.difficulty;

import edu.hitsz.aircraft.EnemyAircraft;

public interface DifficultyStrategy {
    // 是否应该生成BOSS机
    boolean shouldSpawnBoss(int score, int bossCount);

    // 获取当前难度下的敌机最大数量
    int getMaxEnemyNumber(int time);

    // 获取普通敌机的属性
    EnemyProperties getMobEnemyProperties(int time);

    // 获取精英敌机的属性
    EnemyProperties getEliteEnemyProperties(int time);

    // 获取精英加强敌机的属性
    EnemyProperties getElitePlusEnemyProperties(int time);

    // 获取BOSS敌机的属性
    BossProperties getBossEnemyProperties(int bossCount, int time);

    // 获取英雄机的初始生命值
    int getHeroInitialHp();

    // 获取英雄机的射击周期
    int getHeroShootInterval(int time);

    // 获取敌机的射击周期
    int getEnemyShootInterval(int time);

    // 获取精英敌机的产生概率
    double getEliteSpawnProbability(int time);

    // 获取敌机的产生周期
    int getEnemySpawnInterval(int time);

    // 获取BOSS敌机产生的得分阈值
    int getBossSpawnScoreThreshold(int bossCount);
}
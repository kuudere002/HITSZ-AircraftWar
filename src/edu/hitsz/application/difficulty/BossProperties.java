package edu.hitsz.application.difficulty;

import edu.hitsz.application.difficulty.EnemyProperties;

public class BossProperties extends EnemyProperties {
    private final int shootNum;
    private final int maxHp;

    public BossProperties(int hp, int speedX, int speedY, int power, int shootNum, int maxHp) {
        super(hp, speedX, speedY, power);
        this.shootNum = shootNum;
        this.maxHp = maxHp;
    }

    // getters
    public int getShootNum() { return shootNum; }
    public int getMaxHp() { return maxHp; }
}
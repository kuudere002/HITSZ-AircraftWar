package edu.hitsz.application.difficulty;

public class EnemyProperties {
    private final int hp;
    private final int speedX;
    private final int speedY;
    private final int power;

    public EnemyProperties(int hp, int speedX, int speedY, int power) {
        this.hp = hp;
        this.speedX = speedX;
        this.speedY = speedY;
        this.power = power;
    }

    // getters
    public int getHp() { return hp; }
    public int getSpeedX() { return speedX; }
    public int getSpeedY() { return speedY; }
    public int getPower() { return power; }
}


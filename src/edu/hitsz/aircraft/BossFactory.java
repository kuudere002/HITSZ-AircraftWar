package edu.hitsz.aircraft;

public class BossFactory implements EnemyFactory{
    @Override
    public EnemyAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}

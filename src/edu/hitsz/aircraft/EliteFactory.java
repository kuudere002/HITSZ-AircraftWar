package edu.hitsz.aircraft;

public class EliteFactory implements EnemyFactory{
    @Override
    public EnemyAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp);
    }
}

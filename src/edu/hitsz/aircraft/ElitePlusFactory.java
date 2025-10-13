package edu.hitsz.aircraft;

public class ElitePlusFactory implements EnemyFactory{
    @Override
    public EnemyAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new ElitePlusEnemy(locationX, locationY, speedX, speedY, hp);
    }
}

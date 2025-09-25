package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

public class MobFactory implements EnemyFactory{
    @Override
    public EnemyAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new MobEnemy(locationX,locationY,speedX,speedY,hp);
    }
}

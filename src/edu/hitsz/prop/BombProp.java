package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class BombProp extends AbstractProp{
    public BombProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    //观察者模式实现敌机爆炸和子弹消失
    private List<EnemyAircraft> enemyAircrafts;
    private List<BaseBullet> enemyBullets;

    public void loadTarget(List<EnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets){
        this.enemyAircrafts = enemyAircrafts;
        this.enemyBullets = enemyBullets;
    }

    /*
    * 清除场上所有普通敌机和子弹，同时获得分数
    */
    @Override
    public void effect(HeroAircraft HeroAircraft) {
        for(EnemyAircraft enemyAircraft : enemyAircrafts){
            enemyAircraft.getBombed();
        }
        for(BaseBullet enemyBullet : enemyBullets){
            enemyBullet.vanish();
        }

        MusicManager.getInstance().playSoundEffect(MusicManager.BOMB_EXPLODE);
    }


}

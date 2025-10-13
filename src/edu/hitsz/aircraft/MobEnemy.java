package edu.hitsz.aircraft;

import edu.hitsz.ShootModule.NoShoot;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends EnemyAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 0;
        this.power = 1;
        this.direction = 1;
        this.strategy = new NoShoot();
    }




    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    // 无道具
    @Override
    public AbstractProp dropProp() {
        return null;
    }

    @Override
    public void getBombed() {
        // 直接销毁
        this.decreaseHp(this.hp);
    }

}

package edu.hitsz.aircraft;

import edu.hitsz.ShootModule.CircleShootStrategy;
import edu.hitsz.ShootModule.ShootStrategy;
import edu.hitsz.bullet.*;
import edu.hitsz.prop.*;
import edu.hitsz.application.*;

import java.util.*;

public class BossEnemy extends EnemyAircraft{

    // 子弹单次发射数量，20枚
    private int shootNum = 20;
    // 子弹伤害
    private int power = 10;

    private double HpPropFactor = 0.4;
    private double BulletPropFactor = 0.7;
    private double BombPropFactor = 1.0;

    public int getShootNum() {
        return shootNum;
    }

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public ShootStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ShootStrategy strategy) {
        this.strategy = strategy;
    }


    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        maxHp = hp; // 使用传入的hp作为maxHp
        strategy = new CircleShootStrategy();
    }


    @Override
    public List<BaseBullet> shoot() {
        return strategy.shoot(this);
    }

    @Override
    public AbstractProp dropProp(){
        PropFactory propFactory;
        AbstractProp prop;
        double PropSeed = Math.random();

        if(PropSeed < HpPropFactor){
            //敌机坠毁处掉落血包道具
            propFactory = new HpPropFactory();
        }else if(PropSeed < BulletPropFactor){
            //敌机坠毁处掉落子弹道具(Boss机掉落超级火力道具)
            propFactory = new BulletPlusPropFactory();
        }else{
            //敌机坠毁处掉落炸弹道具
            propFactory = new BombPropFactory();
        }

        prop = propFactory.createProp(this.getLocationX(),this.getLocationY(), 0, 4);
        return prop;
    }
    //Boss机无法被炸弹道具影响
    @Override
    public void getBombed() {
    }
}

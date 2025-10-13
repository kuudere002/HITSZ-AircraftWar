package edu.hitsz.aircraft;

import edu.hitsz.ShootModule.FanShootStrategy;
import edu.hitsz.ShootModule.ShootStrategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class ElitePlusEnemy extends EnemyAircraft{
    //子弹单次发射数量
    private int shootNum = 3;
    //子弹伤害
    private int power = 5;
    //子弹射击方向 1向下 -1向下
    private int direction = 1; // 子弹向下发射

    private int maxHp = 80;

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }


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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public ShootStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ShootStrategy strategy) {
        this.strategy = strategy;
    }

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        strategy = new FanShootStrategy();
    }


    @Override
    public List<BaseBullet> shoot() {
       return strategy.shoot(this);
    }

    @Override
    public void getBombed() {
        // 掉一半血量
        this.decreaseHp(this.hp / 2);
    }

}

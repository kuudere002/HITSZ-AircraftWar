package edu.hitsz.aircraft;

import edu.hitsz.ShootModule.DirectShootStrategy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.ShootModule.ShootStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private volatile static HeroAircraft instance;

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 100;

    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */
    private int direction = -1;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */



    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        strategy = new DirectShootStrategy();
    }


    public static HeroAircraft getHeroAircraft() {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 100000);
                }
            }
        }
        return instance;
    }

    // 用于计时恢复弹道的定时器
    private Timer shootStrategyTimer;

    // 新增方法：限时设置射击策略
    public void setStrategyForTime(ShootStrategy newStrategy, int durationMillis) {
        // 取消之前的定时器（如果存在）
        if (shootStrategyTimer != null) {
            shootStrategyTimer.cancel();
        }

        // 设置新策略
        this.strategy = newStrategy;

        // 启动新的定时器，指定时间后恢复原策略
        shootStrategyTimer = new Timer();
        shootStrategyTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 恢复原始策略或默认直射策略
                strategy = new DirectShootStrategy();
                shootStrategyTimer.cancel();
                shootStrategyTimer = null;
            }
        }, durationMillis);
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return strategy.shoot(this);
    }

    /*
    增加生命，且低于生命上限
     */
    public void increaseHp(int addHp) {
        this.hp = Math.min(hp + addHp, maxHp);
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public int getShootNum() {
        return shootNum;
    }

    @Override
    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }
}

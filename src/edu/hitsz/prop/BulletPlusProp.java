package edu.hitsz.prop;

import edu.hitsz.ShootModule.CircleShootStrategy;
import edu.hitsz.ShootModule.FanShootStrategy;
import edu.hitsz.aircraft.HeroAircraft;

public class BulletPlusProp extends AbstractProp{
    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    /*
    使得英雄级弹道变化，变得更强并持续一段时间
     */
    @Override
    public void effect(HeroAircraft hero) {
        hero.setStrategyForTime(new CircleShootStrategy(), 5000);//5000毫秒内使用圆形弹幕
    }
}

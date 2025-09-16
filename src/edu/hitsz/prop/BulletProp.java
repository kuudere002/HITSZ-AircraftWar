package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletProp extends AbstractProp{
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    /*
    使得英雄级弹道变化，变得更强并持续一段时间
     */
    @Override
    public void effect(HeroAircraft hero) {
        System.out.println("FireSupply active!");
    }
}

package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class HpProp extends AbstractProp {
    private static final int addHp = 1000;

    public HpProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        //不超过血量上限的情况下添加血量
        if (hero.getHp() + addHp <= 5000) {
            hero.increaseHp(addHp);
        }else{
            hero.increaseHp(5000 - hero.getHp());
        }
    }
}
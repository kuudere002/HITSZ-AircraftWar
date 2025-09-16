package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class HpProp extends AbstractProp {
    private static final int addHp = 30;

    public HpProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        hero.increaseHp(addHp);
    }
}
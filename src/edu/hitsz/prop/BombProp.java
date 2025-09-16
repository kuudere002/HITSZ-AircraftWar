package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

public class BombProp extends AbstractProp{
    public BombProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    /*
    * 清除场上所有普通敌机和子弹，同时获得分数
    */
    @Override
    public void effect(HeroAircraft enemyAircraft) {
        System.out.println("BombSupply active!");
    }


}

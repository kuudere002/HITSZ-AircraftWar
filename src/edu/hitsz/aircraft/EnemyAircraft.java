package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.*;

public abstract class EnemyAircraft extends AbstractAircraft{

    //道具掉落概率
    private double HpPropFactor = 0.4;
    private double BulletPropFactor = 0.7;
    private double BombPropFactor = 1.0;


    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    public AbstractProp dropProp(){
        PropFactory propFactory;
        AbstractProp prop;
        double PropSeed = Math.random();

        if(PropSeed < HpPropFactor){
            //敌机坠毁处掉落血包道具
            propFactory = new HpPropFactory();
        }else if(PropSeed < BulletPropFactor){
            //敌机坠毁处掉落子弹道具
            propFactory = new BulletPropFactory();
        }else{
            //敌机坠毁处掉落炸弹道具
            propFactory = new BombPropFactory();
        }

        prop = propFactory.createProp(this.getLocationX(),this.getLocationY(), 0, 4);
        return prop;
    }

    public abstract void getBombed();
}

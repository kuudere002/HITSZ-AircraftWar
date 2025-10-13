package edu.hitsz.prop;

public class BulletPlusPropFactory implements PropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BulletPlusProp(locationX,locationY,speedX,speedY);
    }
}

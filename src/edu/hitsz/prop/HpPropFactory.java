package edu.hitsz.prop;

public class HpPropFactory implements PropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new HpProp(locationX,locationY,speedX,speedY);
    }
}

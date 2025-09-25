package edu.hitsz.prop;

public interface PropFactory {
    AbstractProp createProp(int locationX, int locationY, int speedX, int speedY);
}

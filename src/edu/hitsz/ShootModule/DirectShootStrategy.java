package edu.hitsz.ShootModule;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import java.util.LinkedList;
import java.util.List;

/**
 * 直射策略（直线发射子弹）
 */
public class DirectShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();

        // 统一获取通用参数
        int x = aircraft.getLocationX();
        int speedX = 0;
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        int direction = aircraft.getDirection();
        int speedY = aircraft.getSpeedY() + direction * 5;
        int y = aircraft.getLocationY() + direction * 2;;

        // 仅在创建子弹时区分类型
        for (int i = 0; i < shootNum; i++) {
            int bulletX = x + (i * 2 - shootNum + 1) * 10;
            BaseBullet bullet;

            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(bulletX, y, speedX, speedY, power);
            } else if (aircraft instanceof EnemyAircraft) {
                bullet = new EnemyBullet(bulletX, y, speedX, speedY, power);
            } else {
                continue; // 不支持的飞行器类型
            }

            res.add(bullet);
        }

        return res;
    }
}
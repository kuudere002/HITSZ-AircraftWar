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
 * 环形射击策略
 * 持有子弹速度等弹道参数，支持英雄机和敌机使用
 */
public class CircleShootStrategy implements ShootStrategy {
    // 弹道参数：子弹速度
    private static final int bulletSpeed = 5;


    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        // 统一获取核心参数（从飞行器）
        int shootNum;
        if(aircraft instanceof HeroAircraft) shootNum = 10;
        else shootNum = 18;
        int power = aircraft.getPower();

        // 计算子弹基础位置（与飞行器类型无关）
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();

        // 环形分布计算（通用逻辑）
        for (int i = 0; i < shootNum; i++) {
            double angle = 2 * Math.PI * i / shootNum;
            int speedX = (int) (bulletSpeed * Math.sin(angle));
            int speedY = (int) (bulletSpeed * Math.cos(angle));

            // 仅在创建子弹时区分英雄机和敌机
            BaseBullet bullet;
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x, y, speedX, speedY, power);
            } else if (aircraft instanceof EnemyAircraft) {
                bullet = new EnemyBullet(x, y, speedX, speedY, power);
            } else {
                continue; // 不支持的飞行器类型
            }
            res.add(bullet);
        }
        return res;
    }
}
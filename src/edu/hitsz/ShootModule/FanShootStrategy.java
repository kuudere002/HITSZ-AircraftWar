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
 * 扇形射击策略（基于线性偏移实现，与图中逻辑一致）
 * 子弹横向分散呈扇形，通过索引计算偏移量
 */
public class FanShootStrategy implements ShootStrategy {
    // 子弹横向位置偏移系数（控制x方向分散范围）
    private final int xOffsetFactor = 10;
    // 子弹横向速度偏移系数（控制speedX分散范围）
    private final int speedXOffsetFactor = 1;
    // 子弹纵向速度增量（在飞行器速度基础上增加）
    private final int speedYIncrement = 5;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();

        // 从飞行器获取核心参数（与图中this引用逻辑一致）
        int shootNum = 3; // 子弹数量（图中循环变量上限）
        int power = aircraft.getPower();
        int direction = aircraft.getDirection();
        int x = aircraft.getLocationX(); // 飞行器x坐标
        int y = aircraft.getLocationY() + direction * 2; // 纵向偏移（与图中一致）
        int baseSpeedX = aircraft.getSpeedX(); // 飞行器基础横向速度
        int baseSpeedY = aircraft.getSpeedY() + direction * speedYIncrement; // 纵向速度（与图中一致）

        // 循环生成子弹，横向分散逻辑与图中保持一致
        for (int i = 0; i < shootNum; i++) {
            // 计算横向偏移量：(i*2 - shootNum + 1) 是图中核心分散公式
            int offset = i * 2 - shootNum + 1;

            // 子弹x坐标 = 飞行器x + 偏移量 * 位置系数（图中x方向分散）
            int bulletX = x + offset * xOffsetFactor;
            // 子弹横向速度 = 飞行器横向速度 + 偏移量 * 速度系数（图中speedX分散）
            int bulletSpeedX = baseSpeedX + offset * speedXOffsetFactor;
            // 纵向速度统一（与图中一致）
            int bulletSpeedY = baseSpeedY;

            // 区分英雄机和敌机子弹（保持原有类型判断）
            BaseBullet bullet;
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(bulletX, y, bulletSpeedX, bulletSpeedY, power);
            } else if (aircraft instanceof EnemyAircraft) {
                bullet = new EnemyBullet(bulletX, y, bulletSpeedX, bulletSpeedY, power);
            } else {
                continue; // 不支持的飞行器类型
            }

            res.add(bullet);
        }

        return res;
    }
}

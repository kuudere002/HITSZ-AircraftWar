package edu.hitsz.ShootModule;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import java.util.List;

/**
 * 射击策略接口
 */
public interface ShootStrategy {
    /**
     * 执行射击
     *
     * @return 子弹列表
     */
    List<BaseBullet> shoot(AbstractAircraft  aircraft);
}
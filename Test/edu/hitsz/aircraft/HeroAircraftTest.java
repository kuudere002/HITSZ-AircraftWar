package edu.hitsz.aircraft;

import edu.hitsz.basic.AbstractFlyingObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeroAircraftTest {
    private HeroAircraft hero;
    private EnemyAircraft testEnemy;

    @Before
    public void setUp() {
        // 初始化英雄机（单例）
        hero = HeroAircraft.getHeroAircraft();
        // 重置英雄机状态（避免单例状态影响）
        hero.increaseHp(100000); // 恢复到最大生命值
        // 创建测试用敌机（位置可调整）
        testEnemy = new MobEnemy(0, 0, 0, 0, 30);
    }

    /**
     * 测试单例模式：多次获取实例是否为同一对象
     */
    @Test
    public void testGetHeroAircraft() {
        HeroAircraft hero1 = HeroAircraft.getHeroAircraft();
        HeroAircraft hero2 = HeroAircraft.getHeroAircraft();
        Assert.assertSame("单例模式失败：两次获取的实例不同", hero1, hero2);
    }

    /**
     * 测试生命值减少逻辑
     */
    @Test
    public void testDecreaseHp() {
        // 测试正常减少
        hero.decreaseHp(50000);
        Assert.assertEquals("生命值减少错误", 50000, hero.getHp());

        // 测试减少至0并标记消失
        hero.decreaseHp(60000);
        Assert.assertEquals("生命值应减至0", 0, hero.getHp());
        Assert.assertTrue("生命值为0时应标记为无效", hero.notValid());
    }


    /**
     * 测试射击逻辑（子弹数量和方向）
     */
    @Test
    public void testShoot() {
        var bullets = hero.shoot();
        // 测试子弹数量（默认shootNum=1）
        Assert.assertEquals("子弹数量错误", 1, bullets.size());
        // 测试子弹方向（向上，speedY应为负值）
        Assert.assertTrue("子弹应向上飞行（speedY为负）", bullets.get(0).getSpeedY() < 0);
    }
}
package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import edu.hitsz.Frame.*;
import edu.hitsz.application.difficulty.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     *精英敌机生成概率
     */
    private double EliteFactor = 0.5;
    private double ElitePlusFactor = 0.3;

    /**
     * 道具生成概率
     */
    private double PropFactor = 0.4;

    /**
     * boss敌机生成初始分数和数量
     */
    private int BossScore = 200;
    private int BossCount = 0;


    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<EnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    //难度属性
    private DifficultyStrategy difficultyStrategy;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private int enemyCycleTime = 0;
    private int enemyCurrentCycleDuration;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;


    private String difficulty = Main.DIFFICULTY_EASY; // 默认简单难度
    private BufferedImage currentBackground;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        // 根据难度设置背景和游戏参数
        switch (difficulty) {
            case Main.DIFFICULTY_EASY:
                currentBackground = ImageManager.BACKGROUND_IMAGE_EASY;
                difficultyStrategy = new EasyDifficultyStrategy();
                break;
            case Main.DIFFICULTY_NORMAL:
                currentBackground = ImageManager.BACKGROUND_IMAGE_NORMAL;
                difficultyStrategy = new NormalDifficultyStrategy();
                break;
            case Main.DIFFICULTY_HARD:
                currentBackground = ImageManager.BACKGROUND_IMAGE_HARD;
                difficultyStrategy = new HardDifficultyStrategy();
                break;
            default:
                currentBackground = ImageManager.BACKGROUND_IMAGE_EASY;
                difficultyStrategy = new EasyDifficultyStrategy();
        }

        // 初始化英雄机生命值
        HeroAircraft.getHeroAircraft().decreaseHp(HeroAircraft.getHeroAircraft().getHp());
        HeroAircraft.getHeroAircraft().increaseHp(difficultyStrategy.getHeroInitialHp());
    }

    public Game(CardLayout cardLayout, JPanel mainPanel) {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        props = new LinkedList<>();
        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    //提供score
    public int getScore() {
        return score;
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        MusicManager.getInstance().playBackgroundMusic();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
            enemyCurrentCycleDuration = difficultyStrategy.getEnemyShootInterval(time);
            enemyCycleTime += timeInterval;

            // 原周期逻辑（控制英雄机射击和敌机生成）
            if (timeCountAndNewCycleJudge()) {
                generateEnemies();
                // 英雄射击
                heroBullets.addAll(heroAircraft.shoot());
                MusicManager.getInstance().playSoundEffect(MusicManager.BULLET);
            }

            // 敌机射击
            EnemyShootAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // boss机相关bgm切换
            BgmSwitch();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            checkGameOver();

        };



        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }



    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        int currentCycleDuration = Math.min(
                difficultyStrategy.getEnemyShootInterval(time),
                difficultyStrategy.getHeroShootInterval(time)
        );
        if (cycleTime >= currentCycleDuration) {
            // 跨越到新的周期
            cycleTime %= currentCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void EnemyShootAction() {
        // TODO 敌机射击
        // 单独判断敌机射击时机
        if (enemyCycleTime >= enemyCurrentCycleDuration) {
            enemyCycleTime %= enemyCurrentCycleDuration;
            // 执行敌机射击
            for(AbstractAircraft enemy : enemyAircrafts){
                if(!(enemy instanceof MobEnemy)){ // 排除不能射击的敌机
                    enemyBullets.addAll(enemy.shoot());
                }
            }
        }

    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }

    // 检测场上是否有存活的Boss机(用于开关boss机音乐)
    private boolean hasAliveBoss() {
        for (EnemyAircraft enemy : enemyAircrafts) {
            if (enemy instanceof BossEnemy && !enemy.notValid()) {
                return true;
            }
        }
        return false;
    }
    /**
     * 生成敌机逻辑
     */
    private void generateEnemies() {
        if (enemyAircrafts.size() < difficultyStrategy.getMaxEnemyNumber(time)) {
            EnemyFactory enemyFactory;
            EnemyAircraft enemy;
            double random = Math.random();

            // 判断是否生成BOSS
            if (difficultyStrategy.shouldSpawnBoss(score, BossCount)) {
                BossProperties bossProps = difficultyStrategy.getBossEnemyProperties(BossCount, time);
                enemyFactory = new BossFactory();
                enemy = enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                        bossProps.getSpeedX(),
                        bossProps.getSpeedY(),
                        bossProps.getHp()
                );
                ((BossEnemy)enemy).setPower(bossProps.getPower());
                ((BossEnemy)enemy).setShootNum(bossProps.getShootNum());
                BossCount++;
            }
            // 生成其他敌机
            else if (random > difficultyStrategy.getEliteSpawnProbability(time) * 3) {
                EnemyProperties props = difficultyStrategy.getElitePlusEnemyProperties(time);
                enemyFactory = new ElitePlusFactory();
                enemy = enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PLUS_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1),
                        props.getSpeedX(),
                        props.getSpeedY(),
                        props.getHp()
                );
                ((ElitePlusEnemy)enemy).setPower(props.getPower());
            }
            else if (random > difficultyStrategy.getEliteSpawnProbability(time) * 2) {
                EnemyProperties props = difficultyStrategy.getEliteEnemyProperties(time);
                enemyFactory = new EliteFactory();
                enemy = enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1),
                        props.getSpeedX(),
                        props.getSpeedY(),
                        props.getHp()
                );
                ((EliteEnemy)enemy).setPower(props.getPower());
            }
            else {
                EnemyProperties props = difficultyStrategy.getMobEnemyProperties(time);
                enemyFactory = new MobFactory();
                enemy = enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1),
                        props.getSpeedX(),
                        props.getSpeedY(),
                        props.getHp()
                );
            }

            enemyAircrafts.add(enemy);
        }
    }

    private void BgmSwitch() {
        // 检测Boss状态并切换音乐
        boolean bossExists = hasAliveBoss();
        MusicManager musicManager = MusicManager.getInstance();

        if (bossExists) {
            // 有Boss时播放Boss音乐，停止普通BGM
            musicManager.stopBackgroundMusic();
            musicManager.playBossMusic();
        } else {
            // 无Boss时播放普通BGM，停止Boss音乐
            musicManager.stopBossMusic();
            musicManager.playBackgroundMusic();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets){
            if (bullet.notValid()) {
                continue;
            }
            if (bullet.crash(heroAircraft)) {
                heroAircraft.decreaseHp(bullet.getPower());
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    // 子弹击中音效
                    MusicManager.getInstance().playSoundEffect(MusicManager.BULLET_HIT);
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft instanceof MobEnemy){
                            score += 20;
                        }else if(enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof ElitePlusEnemy){
                            score += 50;
                            if(Math.random() > PropFactor){
                                props.add(enemyAircraft.dropProp());
                            }
                        }else if(enemyAircraft instanceof BossEnemy){
                            score += 100;
                            if(Math.random() > PropFactor / 2 ){
                                //随机生成0~3的整数
                                int PropSeed = (int) (Math.random() * 4);
                                for(int i = 0; i <= PropSeed; i++) {
                                    props.add(enemyAircraft.dropProp());
                                }
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                if(prop instanceof BombProp){
                    BombProp bombProp = (BombProp) prop;
                    bombProp.loadTarget(enemyAircrafts, enemyBullets);
                    bombProp.effect(heroAircraft);
                }
                else prop.effect(heroAircraft);
                // 道具生效音效
                MusicManager.getInstance().playSoundEffect(MusicManager.PROP_EFFECT);
                prop.vanish();
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //根据难度绘制背景
        switch (difficulty){
            case Main.DIFFICULTY_EASY:
                currentBackground = ImageManager.BACKGROUND_IMAGE_EASY;
                break;
            case Main.DIFFICULTY_NORMAL:
                currentBackground = ImageManager.BACKGROUND_IMAGE_NORMAL;
                break;
            case Main.DIFFICULTY_HARD:
                currentBackground = ImageManager.BACKGROUND_IMAGE_HARD;
        }
        g.drawImage(currentBackground, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(currentBackground, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    // 游戏主循环中的HP归零检测（核心跳转逻辑）
    private void checkGameOver() {
        if (heroAircraft.getHp() <= 0) {
            // 停止游戏逻辑
            executorService.shutdown();
            gameOverFlag = true;
            // 停止所有音乐
            MusicManager.getInstance().stopBackgroundMusic();
            MusicManager.getInstance().stopBossMusic();
            // 播放游戏结束音效
            MusicManager.getInstance().playSoundEffect(MusicManager.GAME_OVER);
            System.out.println("Game Over!");

            SwingUtilities.invokeLater(() -> {
                ScoreboardPanel scoreboardPanel = (ScoreboardPanel) mainPanel.getComponent(2);
                scoreboardPanel.setDifficulty(difficulty); // 传递难度
                scoreboardPanel.refreshScores(); // 刷新对应难度的分数
                cardLayout.show(mainPanel, "scoreboard"); // 切换面板
                // 1. 弹出保存分数对话框（模态，阻塞后续操作）
                SaveScoreDialog dialog = new SaveScoreDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        score,
                        difficulty
                );
                dialog.setVisible(true); // 显示对话框
                scoreboardPanel.refreshScores(); // 刷新对应难度的分数
            });
        }
    }


}

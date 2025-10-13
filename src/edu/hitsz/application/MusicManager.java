package edu.hitsz.application;

import edu.hitsz.Threads.MusicThread;

public class MusicManager {
    private static MusicManager instance;
    private MusicThread backgroundMusic;
    private MusicThread bossMusic;
    private boolean soundEnabled;

    // 新增音效类型
    public static final String BULLET = "src/videos/bullet.wav";
    public static final String BULLET_HIT = "src/videos/bullet_hit.wav";
    public static final String BOMB_EXPLODE = "src/videos/bomb_explosion.wav";
    public static final String PROP_EFFECT = "src/videos/get_supply.wav";
    public static final String GAME_OVER = "src/videos/game_over.wav";

    private MusicManager() {
        soundEnabled = true; // 默认开启
    }

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    private enum MusicType { BACKGROUND, BOSS, NONE }
    private MusicType currentMusic = MusicType.NONE;

    // 播放背景音乐时更新状态
    public void playBackgroundMusic() {
        if (currentMusic == MusicType.BACKGROUND) {
            return; // 已在播放，直接返回
        }
        stopBackgroundMusic();
        if (soundEnabled) {
            backgroundMusic = new MusicThread("src/videos/bgm.wav");
            backgroundMusic.setDaemon(true);
            backgroundMusic.start();
            currentMusic = MusicType.BACKGROUND; // 更新状态
        }
    }

    // 播放Boss音乐时更新状态
    public void playBossMusic() {
        if (currentMusic == MusicType.BOSS) {
            return; // 已在播放，直接返回
        }
        stopBossMusic();
        if (soundEnabled) {
            bossMusic = new MusicThread("src/videos/bgm_boss_1.wav");
            bossMusic.setDaemon(true);
            bossMusic.start();
            currentMusic = MusicType.BOSS; // 更新状态
        }
    }

    // 停止音乐时重置状态
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isAlive()) {
            backgroundMusic.stopMusic();
            backgroundMusic = null;
            currentMusic = MusicType.NONE; // 重置状态
        }
    }

    public void stopBossMusic() {
        if (bossMusic != null && bossMusic.isAlive()) {
            bossMusic.stopMusic();
            bossMusic = null;
            currentMusic = MusicType.NONE; // 重置状态
        }
    }

    // 播放音效
    public void playSoundEffect(String filename) {
        if (soundEnabled) {
            Thread soundThread = new Thread(() -> {
                MusicThread sound = new MusicThread(filename);
                sound.start();
            });
            soundThread.setDaemon(true);
            soundThread.start();
        }
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled) {
            stopBackgroundMusic();
            stopBossMusic();
        }
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }
}
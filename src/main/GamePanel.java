package main;

import ai.PathFinder;
import entity.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import javax.swing.JPanel;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = 48;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = 768;
    public final int screenHeight = 576;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    int FPS = 60;
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public PathFinder pFinder = new PathFinder(this);
    public UI ui = new UI(this);
    Thread gameThread;
    // entities and objects
    public Player player = new Player(this, this.keyH);
    public Entity[] obj = new Entity[15];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    // game state
    public int gameState;
    public final int TITLE = 0;
    public final int PLAY = 1;
    public final int PAUSE = 2;
    public final int DIALOGUE = 3;
    public final int CHARACTER = 4;
    public final int GAMEOVER = 5;

    public GamePanel() {
        setPreferredSize(new Dimension(768, 576));
        setBackground(Color.black);
        setDoubleBuffered(true);
        addKeyListener(this.keyH);
        setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = 0;
    }

    public void reset() {
        player.setDefaultValues();
        setupGame();
        ui.playTime = 0;
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = (1000000000 / this.FPS);
        double delta = 0.0D;
        long lastTime = System.nanoTime();
        long timer = 0L;
        int drawCount = 0;
        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1.0D) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000L) {
                drawCount = 0;
                timer = 0L;
            }
        }
    }

    public void update() {
        if (gameState == PLAY) {
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive && !monster[i].dying) monster[i].update();
                    if (!monster[i].alive) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) projectileList.get(i).update();
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameState == TITLE) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);
            entityList.add(player);
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) entityList.add(npc[i]);
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) entityList.add(obj[i]);
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) entityList.add(monster[i]);
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) entityList.add(projectileList.get(i));
            }
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldX, e2.worldY);
                    return result;
                }
            });
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();
            ui.draw(g2);
        }
        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}



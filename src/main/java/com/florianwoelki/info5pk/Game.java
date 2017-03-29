package com.florianwoelki.info5pk;

import com.florianwoelki.info5pk.input.Keyboard;
import com.florianwoelki.info5pk.input.Mouse;
import com.florianwoelki.info5pk.level.Level;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Florian Woelki on 16.11.16.
 */
public class Game extends Canvas implements Runnable {

    private Window window;
    private Keyboard keyboard;
    private Mouse mouse;

    private Thread thread;
    private boolean isRunning;

    private Level level;

    public Game() {
        keyboard = new Keyboard();
        mouse = new Mouse();

        requestFocus();
        setFocusable(true);
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);

        level = new Level(128, 128);

        window = new Window(this);
        window.setVisible(true);
    }

    private synchronized void start() {
        if(isRunning) return;

        isRunning = true;
        thread = new Thread(this, "Game Window");
        thread.start();
    }

    private synchronized void stop() {
        if(!isRunning) return;

        isRunning = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        double ns = 1000000000d / 60d;
        long lastTimer = System.currentTimeMillis();

        int fps = 0, ups = 0;

        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                delta--;
                update();
                ups++;
            }

            try {
                Thread.sleep(1);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            render();
            fps++;

            if(System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                System.out.println("FPS: " + fps + ", UPS: " + ups);
                ups = fps = 0;
            }
        }

        stop();
    }

    private void update() {
        level.update();
        keyboard.update();

        if(keyboard.right) {
            x--;
        } else if(keyboard.left) {
            x++;
        } else if(keyboard.up) {
            y++;
        } else if(keyboard.down) {
            y--;
        }
    }

    int x = 0;
    int y = 0;

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        level.render(g, x, y, mouse.getMouseWheelScale(), keyboard.f3);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

}

package com.florianwoelki.info5pk.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Florian Woelki on 16.11.16.
 */
public class Keyboard implements KeyListener {

    private boolean[] keys = new boolean[ 120 ];
    public boolean up, down, left, right;

    public void update() {
        up = keys[ KeyEvent.VK_UP ] || keys[ KeyEvent.VK_W ];
        down = keys[ KeyEvent.VK_DOWN ] || keys[ KeyEvent.VK_S ];
        left = keys[ KeyEvent.VK_LEFT ] || keys[ KeyEvent.VK_A ];
        right = keys[ KeyEvent.VK_RIGHT ] || keys[ KeyEvent.VK_D ];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() < keys.length ) {
            keys[ e.getKeyCode() ] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ( e.getKeyCode() < keys.length ) {
            keys[ e.getKeyCode() ] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}

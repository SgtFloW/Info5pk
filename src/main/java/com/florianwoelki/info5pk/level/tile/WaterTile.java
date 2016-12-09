package com.florianwoelki.info5pk.level.tile;

import com.florianwoelki.info5pk.level.Level;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Florian Woelki on 19.11.16.
 */
public class WaterTile extends Tile {

    public WaterTile( int id ) {
        super( id, 0f );
    }

    @Override
    public void render( Graphics g, Level level, int x, int y ) {
        // int color = level.waterColor;

        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform();
        at.scale( this.mouseWheelScale, this.mouseWheelScale );
        g2d.setTransform( at );

        g2d.setColor( Color.BLUE );
        g2d.fillRect( x * 16, y * 16, 16, 16 );

        g2d.dispose();
    }

}

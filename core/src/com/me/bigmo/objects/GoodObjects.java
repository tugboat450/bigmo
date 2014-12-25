package com.me.bigmo.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Eric on 12/20/2014.
 */
public class GoodObjects  {

    public Rectangle spawnGood() {
        Rectangle b = new Rectangle();
        b.x = MathUtils.random(0, 480 - 64);
        b.y = 800;
        b.width = 64;
        b.height = 64;
        return b;
    }


  

}

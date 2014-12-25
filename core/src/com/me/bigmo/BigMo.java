package com.me.bigmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.me.bigmo.screens.MainMenuScreen;


import java.util.Iterator;

//import sun.plugin.dom.css.Rectangle;

//import com.me.screens.GameScreen;

public  class BigMo extends Game {





    public SpriteBatch batch;
    public BitmapFont font;
    private Preferences prefs;

    @Override
    public void create() {
        //batch = new SpriteBatch();
       // font = new BitmapFont();
        prefs = Gdx.app.getPreferences("Score");
        if(!prefs.contains("highscoregame")) {
            prefs.putInteger("highscore game",0);
        }
        if(!prefs.contains("highscore challenge")) {
            prefs.putInteger("highscore challenge",0);
        }
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }



    public enum GameState {
        GAMESCREEN,CHALLENGESCREEN
    }
    public void dispose() {
       // batch.dispose();
        //font.dispose();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}

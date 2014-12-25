package com.me.bigmo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.bigmo.BigMo;
import com.me.bigmo.objects.GoodObjects;

import java.util.Iterator;

/**
 * Created by Eric on 12/13/2014.
 */
public class GameScreen implements Screen {

    final BigMo game;

    private Sound effect;

    private Texture player;
    private Texture gummy;
    private OrthographicCamera camera;
    private Rectangle mo;
    private Array<Rectangle> bears;
    private long lastDropTime;
    private int score;
    int speed = 400;
    long time = 830000000;
    private BitmapFont white;
    private int mode = 0;
    private BigMo.GameState currentState = BigMo.GameState.GAMESCREEN;
    private GoodObjects bear = new GoodObjects();
    private Array<Rectangle> objArray;
    public Texture background;
    private Viewport viewport;
    private Preferences prefs = Gdx.app.getPreferences("Score");


    public GameScreen(final BigMo game) {
        this.game = game;


        /*player = new Texture(Gdx.files.internal("objects/sprite1.png"));
        gummy = new Texture(Gdx.files.internal("objects/gb.png"));

        effect = Gdx.audio.newSound(Gdx.files.internal("sound/sound.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);

        game.batch = new SpriteBatch();
        mo = new Rectangle();
        mo.x = 480 / 2 - 64 / 2;
        mo.y = 20;
        mo.width = 64;
        mo.height = 164;

       bears = new Array<Rectangle>();
       spawnBear();*/


    }

    private void spawnBear() {
        Rectangle b = bear.spawnGood();
        //b.x = MathUtils.random(0, 480 - 64);
       // b.y = 800;
      //  b.width = 64;
      //  b.height = 64;
        bears.add(b);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
      //  game.batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        white.setScale(.5f);
        white.draw(game.batch, "Score: " + score, 0,800);
        game.batch.draw(player,mo.x,mo.y);
        for(Rectangle b: bears) {
            game.batch.draw(gummy,b.x,b.y);
        }
        game.batch.end();


        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            mo.x = (int) (touchPos.x - 64/2);
        }

        if(TimeUtils.nanoTime() - lastDropTime > time)
            spawnBear();

        Iterator<Rectangle> iter = bears.iterator();
        while(iter.hasNext()) {
            Rectangle b = iter.next();
            b.y -= speed * Gdx.graphics.getDeltaTime();
            if(b.y + 64 < 0) {
                if(score > prefs.getInteger("highscore game")) {
                    prefs.putInteger("highscore game",score);
                    prefs.flush();
                }
                game.setScreen(new EndScreen(game,score,currentState));
            }
            if(b.overlaps(mo)) {
                score++;
                if(score%7 == 0 && speed <= 1100 ) {
                    speed += 50;
                    time -= 45000000;
                }
                iter.remove();
                effect.play();
            }


        }


    }

    @Override
    public void show() {




        player = new Texture(Gdx.files.internal("objects/sprite1.png"));
        gummy = new Texture(Gdx.files.internal("objects/gb.png"));
        background = new Texture(Gdx.files.internal("objects/back.png"));

        effect = Gdx.audio.newSound(Gdx.files.internal("sound/sound.wav"));

        camera = new OrthographicCamera();
       // viewport = new FillViewport(480,800,camera);
       // viewport.apply();
        camera.setToOrtho(false,480,800);
       // camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        game.batch = new SpriteBatch();
        mo = new Rectangle();
        mo.x = 480 / 2 - 64 / 2;
        mo.y = 20;
        mo.width = 64;
        mo.height = 164;

        bears = new Array<Rectangle>();
        spawnBear();
        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);

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

    @Override
    public void hide() {

    }

    public void dispose() {
        player.dispose();
        gummy.dispose();
        game.batch.dispose();
        effect.dispose();
    }

}

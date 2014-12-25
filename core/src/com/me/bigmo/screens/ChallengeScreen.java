package com.me.bigmo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
import com.me.bigmo.BigMo;
import com.me.bigmo.objects.BadObjects;
import com.me.bigmo.objects.GoodObjects;
import java.util.Iterator;


/**
 * Created by Eric on 12/19/2014.
 */
public class ChallengeScreen  implements Screen{

    final BigMo game;

    private Sound effect;

    private Texture player;
    private Texture burger;
    private Texture gummy;
    private OrthographicCamera camera;
    private Rectangle mo;
    private Array<Rectangle> objects;
    private long lastDropTime;
    private int score;
    int speed = 400;
    long time = 830000000;
    private BitmapFont white;
    private int mode = 1;
    private GoodObjects good = new GoodObjects();
    private BadObjects bad = new BadObjects();
    private BigMo.GameState currentState = BigMo.GameState.CHALLENGESCREEN;
    private Preferences prefs=Gdx.app.getPreferences("Score");


    public ChallengeScreen(final BigMo game) {
        this.game = game;

       /* player = new Texture(Gdx.files.internal("objects/sprite1.png"));
        gummy = new Texture(Gdx.files.internal("objects/gb.png"));
        burger = new Texture(Gdx.files.internal("objects/burger.png"));

        effect = Gdx.audio.newSound(Gdx.files.internal("sound/sound.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);

        game.batch = new SpriteBatch();
        mo = new Rectangle();
        mo.x = 480 / 2 - 64 / 2;
        mo.y = 20;
        mo.width = 64;
        mo.height = 164;

        objects = new Array<Rectangle>();
        spawnObject();*/
    }

    private void spawnObject() {

        int rand = MathUtils.random(0,5);
        if(rand == 0 || rand == 1) {
            Rectangle b = bad.spawnBad();
            objects.add(b);
        }
        else {
            Rectangle b = good.spawnGood();
            objects.add(b);
        }
        //b.x = MathUtils.random(0, 480 - 64);
       // b.y = 800;
       // b.width = 64;
       // b.height = MathUtils.random(59,64);
       // objects.add(b);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        white.setScale(.5f);
        white.draw(game.batch, "Score: " + score, 0,800);
        game.batch.draw(player,mo.x,mo.y);
        for(Rectangle b: objects) {

            if(b.height==63)
                game.batch.draw(burger,b.x,b.y);
            else
                game.batch.draw(gummy, b.x, b.y);

        }
        game.batch.end();


        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            mo.x = (int) (touchPos.x - 64/2);
        }

        if(TimeUtils.nanoTime() - lastDropTime > time)
            spawnObject();

        Iterator<Rectangle> iter = objects.iterator();
        while(iter.hasNext()) {
            Rectangle b = iter.next();
            b.y -= speed * Gdx.graphics.getDeltaTime();
            if(b.y + 64 < 0 && b.height == 64) {
                if(score > prefs.getInteger("highscore challenge")) {
                    prefs.putInteger("highscore challenge",score);
                    prefs.flush();
                }
                game.setScreen(new EndScreen(game,score,currentState));
            }
            if(b.overlaps(mo)&& b.height == 64) {
                score++;
                if(score%7 == 0 && speed <= 1050 ) {
                    speed += 50;
                    time -= 45000000;
                }
                iter.remove();
                effect.play();
            }
           if(b.overlaps(mo)&& b.height == 63) {
               if (score > prefs.getInteger("highscore challenge")) {
                   prefs.putInteger("highscore challenge", score);
                   prefs.flush();
               }
               game.setScreen(new EndScreen(game, score, currentState));
           }



        }


    }

    @Override
    public void show() {





        player = new Texture(Gdx.files.internal("objects/sprite1.png"));
        gummy = new Texture(Gdx.files.internal("objects/gb.png"));
        burger = new Texture(Gdx.files.internal("objects/burger.png"));

        effect = Gdx.audio.newSound(Gdx.files.internal("sound/sound.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);

        game.batch = new SpriteBatch();
        mo = new Rectangle();
        mo.x = 480 / 2 - 64 / 2;
        mo.y = 20;
        mo.width = 64;
        mo.height = 164;

        objects = new Array<Rectangle>();
        spawnObject();
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
        burger.dispose();
        game.batch.dispose();
        effect.dispose();
    }
}

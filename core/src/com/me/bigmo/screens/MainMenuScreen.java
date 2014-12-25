package com.me.bigmo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.bigmo.BigMo;


/**
 * Created by Eric on 12/13/2014.
 */
public class MainMenuScreen implements Screen {

    private Music hi;
    final BigMo game;

    float scrw = Gdx.graphics.getWidth();
    float scrh = Gdx.graphics.getHeight();

    OrthographicCamera camera;

    public MainMenuScreen(final BigMo game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,scrw,scrh);

       hi = Gdx.audio.newMusic(Gdx.files.internal("sound/hibigmo.ogg"));
    }


    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonExit, buttonPlay,buttonChallenge;
    private BitmapFont white;
    private Label heading;


    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();


    }

    public void resize(int width, int height) {
      //  stage.setViewport(width, height, true);
        table.invalidateHierarchy();
        table.setSize(width, height);
    }

    public void show() {

        stage = new Stage();


        Gdx.input.setInputProcessor(stage);

        atlas  = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //create font
        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);

        //create buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;
        textButtonStyle.fontColor = Color.BLACK;

       /* buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

        });
        buttonExit.pad(15); */

        buttonPlay = new TextButton("PLAY GAME", textButtonStyle);
        buttonPlay.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
            }

        });
        buttonPlay.pad(25);

        buttonChallenge = new TextButton("CHALLENGE", textButtonStyle);
        buttonChallenge.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ChallengeScreen(game));
            }

        });
        buttonChallenge.pad(25);


        //create heading
        heading = new Label("Big Mo",  new Label.LabelStyle(white, Color.BLUE));
        heading.setFontScale(2);

        //putting stuff together
        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(80);
        table.row();
        table.add(buttonChallenge).spaceBottom(15);
       // table.row();
       // table.add(buttonExit);
        stage.addActor(table);
        hi.play();

    }

    public void hide() {
        dispose();
    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        hi.dispose();
    }

}

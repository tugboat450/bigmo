package com.me.bigmo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.bigmo.BigMo;

/**
 * Created by Eric on 12/13/2014.
 */
public class EndScreen implements Screen {

    private Music end;
    final BigMo game;
    private int points;
    private int mode;
    private BigMo.GameState currentState;

    float scrw = Gdx.graphics.getWidth();
    float scrh = Gdx.graphics.getHeight();

    public OrthographicCamera camera;
    private Label highscore;

    public EndScreen(final BigMo game, int points, BigMo.GameState mode) {
        this.game = game;
        this.points = points;
        currentState = mode;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,scrw,scrh);

        end = Gdx.audio.newMusic(Gdx.files.internal("sound/music.wav"));
    }


    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonExit, buttonPlay,buttonMenu;
    private BitmapFont white;
    private Label heading;
    private Label score;
    public static Preferences prefs = Gdx.app.getPreferences("Score");;


    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    public void resize(int width, int height) {
     //  stage.setViewport()
        table.invalidateHierarchy();
        table.setSize(width, height);
    }

    public void show() {

        end.play();

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

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

        });
        buttonExit.pad(15);

        buttonPlay = new TextButton("PLAY AGAIN", textButtonStyle);
        buttonPlay.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if(currentState == BigMo.GameState.GAMESCREEN)
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
                if(currentState== BigMo.GameState.CHALLENGESCREEN)
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ChallengeScreen(game));
            }

        });
        buttonPlay.pad(15);

        buttonMenu = new TextButton("MAIN MENU", textButtonStyle);
        buttonMenu.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }

        });
        buttonMenu.pad(15);

        //high score

        if(currentState== BigMo.GameState.GAMESCREEN)
            highscore = new Label("High Score: " + prefs.getInteger("highscore game"), new Label.LabelStyle(white, Color.WHITE));
        if(currentState== BigMo.GameState.CHALLENGESCREEN)
            highscore = new Label("High Score: " + prefs.getInteger("highscore challenge"), new Label.LabelStyle(white, Color.WHITE));

        highscore.setFontScale(.6f);
        //score
        score = new Label("Your Score: " + points,new Label.LabelStyle(white, Color.WHITE));
        score.setFontScale(.6f);
        //create heading
        heading = new Label("Big Mo Starved",  new Label.LabelStyle(white, Color.RED));
        heading.setFontScale(.9f);

        //putting stuff together
        table.add(heading);
        table.getCell(heading).spaceBottom(60);
        table.row();
        table.add(score);
        table.getCell(score).spaceBottom(50);
        table.row();
        table.add(highscore).spaceBottom(50);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(80);
        table.row();
        table.add(buttonMenu);
        table.getCell(buttonMenu).spaceBottom(80);
        table.row();
        table.add(buttonExit);
        stage.addActor(table);



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
        end.dispose();
    }

}

package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ExitButton;
import ru.geekbrains.sprite.PlayButton;
import ru.geekbrains.sprite.SpriteRegistry;
import ru.geekbrains.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private Game game;

    private TextureAtlas atlas;

    private Texture bg;

    private ExitButton exitButton;
    private PlayButton playButton;

    private SpriteRegistry spriteRegistry;

    public MenuScreen(Game game) {
        this.game = game;
        spriteRegistry = new SpriteRegistry();
    }

    @Override
    public void show() {
        super.show();

        bg = new Texture("textures/bg.png");
        new Background(bg, spriteRegistry);

        this.atlas = new TextureAtlas("textures/menuAtlas.tpack");

        for (int i = 0; i < STAR_COUNT; i++) {
            new Star(atlas, spriteRegistry);
        }

        exitButton = new ExitButton(atlas, spriteRegistry);
        playButton = new PlayButton(atlas, game, spriteRegistry);
    }

    @Override
    public void resize(Rect worldBounds) {
        spriteRegistry.execute(s -> s.resize(worldBounds));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        spriteRegistry.execute(s -> {
            s.update(delta);
            s.draw(batch);
        });
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }
}

package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.SpriteRegistry;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;
    private Texture bg;

    private MainShip mainShip;

    private final SpriteRegistry spriteRegistry;

    public GameScreen() {
        this.spriteRegistry = new SpriteRegistry();
    }

    @Override
    public void show() {
        super.show();

        bg = new Texture("textures/bg.png");
        new Background(bg, spriteRegistry);

        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        for (int i = 0; i < STAR_COUNT; i++) {
            new Star(atlas, spriteRegistry);
        }

        mainShip = new MainShip(atlas, spriteRegistry, worldBounds);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        spriteRegistry.execute(sprite -> {
            sprite.update(delta);
            checkCollision();
            sprite.draw(batch);
        });
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        spriteRegistry.execute(s -> s.resize(worldBounds));
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == 32) { // D
            mainShip.right();
        } else if (keycode == 29) { // A
            mainShip.left();
        } else if (keycode == 47) { // S
            mainShip.stop();
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x > mainShip.getRight()) {
            mainShip.right();
        } else {
            mainShip.left();
        }
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    private void checkCollision() {

    }

}

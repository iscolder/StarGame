package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.DestinationSprite;
import ru.geekbrains.sprite.MoveSprite;

public class MoveScreen extends BaseScreen {

    private MoveSprite move;
    private DestinationSprite destination;

    @Override
    public void show() {
        super.show();
        this.move = new MoveSprite(new TextureRegion(new Texture("dot-start.png")));
        this.destination = new DestinationSprite(new TextureRegion(new Texture("dot-end.png")));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        destination.draw(batch);
        move.draw(batch);
        batch.end();

        if (move.pos.epsilonEquals(destination.pos, 0.001f)) {
            move.stop();
        }

        move.forward();
    }

    @Override
    public void resize(Rect worldBounds) {
        move.resize(worldBounds);
        destination.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        destination.touchDown(new Vector2(screenX, screenY), screenToWorld);
        move.setSpeed(new Vector2(destination.pos).sub(move.pos));
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
}

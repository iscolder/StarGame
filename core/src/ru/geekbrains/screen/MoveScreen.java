package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MoveScreen extends BaseScreen {
    private Texture imgStart;
    private Texture imgEnd;
    private Vector2 currentPosition;
    private Vector2 movementPosition;
    private Vector2 move;

    @Override
    public void show() {
        super.show();
        imgStart = new Texture("dot-start.png");
        imgEnd = new Texture("dot-end.png");
        currentPosition = new Vector2(0, 0);
        movementPosition = new Vector2(0, 0);
        move = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        batch.draw(imgEnd, movementPosition.x, movementPosition.y);
        batch.draw(imgStart, currentPosition.x, currentPosition.y);
        batch.end();

        if (currentPosition.epsilonEquals(movementPosition, 1)) {
            move.setZero();
        }

        currentPosition.add(move);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        movementPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        move.set(new Vector2(movementPosition).sub(currentPosition).nor());
        return super.touchDown(screenX, screenY, pointer, button);
    }


    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
}

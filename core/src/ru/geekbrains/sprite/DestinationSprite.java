package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class DestinationSprite extends Sprite {
    public DestinationSprite(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.setHeightProportion(worldBounds.getHeight() * 0.1f);
        pos.set(worldBounds.pos);
    }

    public void touchDown(Vector2 touch, Matrix3 screenToWorld) {
        pos.set(touch.x, Gdx.graphics.getHeight() - touch.y).mul(screenToWorld);
    }
}

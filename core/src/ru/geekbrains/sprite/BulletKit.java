package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class BulletKit extends Sprite {

    private Rect worldBounds;
    private Vector2 v;

    private static final float DECREASE_BULLETS_INTERVAL = 0.003f;

    public BulletKit(TextureRegion region, Rect worldBounds) {
        super(region);
        setHeightProportion(0.07f);
        this.worldBounds = worldBounds;
        v = new Vector2(0, -0.3f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public float getBulletInterval() {
        return DECREASE_BULLETS_INTERVAL;
    }
}

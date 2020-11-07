package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class SupportKit extends Sprite {

    private Rect worldBounds;
    private Vector2 v;

    public SupportKit(TextureRegion region, Rect worldBounds) {
        super(region);
        setHeightProportion(0.05f);
        this.worldBounds = worldBounds;
        v = new Vector2(0, -0.2f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

}

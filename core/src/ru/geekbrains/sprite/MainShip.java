package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MainShip extends Sprite {

    private static final float MOVE_SPEED = 0.3f;

    private Vector2 moveRight = new Vector2(MOVE_SPEED, 0f);
    private Vector2 moveLeft = new Vector2(-MOVE_SPEED, 0f);
    private Vector2 move = new Vector2(0, 0f);

    private final Rect worldBounds;

    public MainShip(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("main_ship"));
        TextureRegion region = regions[frame];
        region.setRegionWidth(region.getRegionWidth() / 2);
        this.worldBounds = worldBounds;
    }

    public MainShip(TextureAtlas atlas, SpriteRegistry registry, Rect worldBounds) {
        this(atlas, worldBounds);
        registry.register(this);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(-0.5f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(move, delta);
        if (getRight() > worldBounds.getRight()) {
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            stop();
        }
    }

    public void right() {
        move.set(moveRight);
    }

    public void left() {
        move.set(moveLeft);
    }

    public void stop() {
        move.setZero();
    }
}

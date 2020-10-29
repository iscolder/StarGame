package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MoveSprite extends Sprite {

    private Vector2 speed;
    private static final int brake = 100;

    public MoveSprite(TextureRegion region) {
        super(region);
        this.speed = new Vector2(0, 0);
    }

    public void stop() {
        speed.setZero();
    }

    public void forward() {
        pos.add(speed);
    }

    public void setSpeed(Vector2 speed) {
        this.speed.set(speed.x / brake, speed.y / brake);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.setHeightProportion(worldBounds.getHeight()*0.1f);
        pos.set(worldBounds.pos);
    }

}

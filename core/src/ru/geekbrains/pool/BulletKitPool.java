package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.BulletKit;

public class BulletKitPool extends SpritesPool<BulletKit> {

    private final Rect worldBounds;
    private final TextureRegion bulletKitRegion;

    public BulletKitPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.bulletKitRegion = new TextureRegion(new Texture("textures/bulletKit.png"));
    }

    @Override
    protected BulletKit newObject() {
        return new BulletKit(bulletKitRegion, worldBounds);
    }
}

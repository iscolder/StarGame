package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.SupportShip;

public class SupportShipPool extends SpritesPool<SupportShip> {

    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Rect worldBounds;
    private final TextureAtlas atlas;
    private final Sound bulletSound;

    public SupportShipPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, TextureAtlas atlas, Sound bulletSound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.atlas = atlas;
        this.bulletSound = bulletSound;
    }

    @Override
    protected SupportShip newObject() {
        return new SupportShip(atlas, bulletPool, explosionPool, worldBounds, bulletSound);
    }
}

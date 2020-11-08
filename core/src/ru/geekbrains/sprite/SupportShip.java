package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class SupportShip extends Ship {

    private static final float SHIP_HEIGHT = 0.1f;
    private static final float RELOAD_INTERVAL = 0.4f;
    private static final int HP = 50;

    public SupportShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound supportBulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSound = supportBulletSound;
        setHeightProportion(SHIP_HEIGHT);
    }

    public void set(int level) {
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.v.set(0, 0.1f);
        this.bulletV.set(0, 0.5f);
        this.reloadInterval = RELOAD_INTERVAL - (level - 1) * 0.02f;
        this.hp = HP + (level - 1) * 5;
        this.visible = true;
        this.destroyed = false;
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getTop());
        super.update(delta);

        if (getTop() > worldBounds.getTop()) {
            destroy();
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return bullet.getRight() >= getLeft() &&
                bullet.getLeft() <= getRight() &&
                bullet.getBottom() <= pos.y &&
                bullet.getTop() >= getBottom();
    }

}

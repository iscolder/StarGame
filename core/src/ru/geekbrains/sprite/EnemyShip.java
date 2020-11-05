package ru.geekbrains.sprite;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.EnemySettingsDto;
import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class EnemyShip extends Ship {

    private Vector2 normalSpeed;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getBottom());
        super.update(delta);

        if (getTop() <= worldBounds.getTop()) {
            this.visible = true;
            if (!boostCompleted) {
                this.v.set(normalSpeed); // set normal speed
                boostCompleted = true;
                shoot();
            }
        }

        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(EnemySettingsDto settings) {
        this.normalSpeed = new Vector2(settings.getV0());

        Vector2 boostSpeed = new Vector2(settings.getV0().x, settings.getBoost() * settings.getV0().y);
        this.v.set(boostSpeed);

        this.regions = settings.getRegions();
        this.bulletRegion = settings.getBulletRegion();
        this.bulletHeight = settings.getBulletHeight();
        this.bulletV.set(settings.getBulletV());
        this.bulletSound = settings.getBulletSound();
        this.damage = settings.getDamage();
        this.reloadInterval = settings.getReloadInterval();
        setHeightProportion(settings.getHeight());
        this.hp = settings.getHp();
        this.reloadTimer = 0;
    }

    public boolean isBulletCollision(Rect bullet) {
        return bullet.getRight() >= getLeft() &&
                                bullet.getLeft() <= getRight() &&
                                bullet.getBottom() <= getTop() &&
                                bullet.getTop() >= pos.y;
    }

}

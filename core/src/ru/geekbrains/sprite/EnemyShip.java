package ru.geekbrains.sprite;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.EnemySettingsDto;
import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class EnemyShip extends Ship {

    private Vector2 normalSpeed;

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getBottom());

        if (getTop() <= worldBounds.getTop()) {
            this.visible = true;
        }

        if (visible && !boostCompleted) {
            this.v.set(normalSpeed); // set normal speed
            boostCompleted = true;
            shoot();
        }

        super.update(delta);
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
    }

}

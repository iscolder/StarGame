package ru.geekbrains.pool;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.entity.CommandPost;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private CommandPost commandPost;

    public EnemyShipPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, CommandPost commandPost) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.commandPost = commandPost;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds, commandPost);
    }
}

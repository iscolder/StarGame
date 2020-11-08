package ru.geekbrains.utils;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BulletKitPool;
import ru.geekbrains.sprite.BulletKit;

public class BulletKitEmitter {
    private static final float GENERATE_INTERVAL = 8f;

    private final Rect worldBounds;
    private final BulletKitPool bulletKitPool;

    private float generateTimer;

    public BulletKitEmitter(Rect worldBounds, BulletKitPool bulletKitPool) {
        this.worldBounds = worldBounds;
        this.bulletKitPool = bulletKitPool;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0;

            BulletKit bulletKit = bulletKitPool.obtain();
            bulletKit.pos.x = Rnd.nextFloat(worldBounds.getLeft() + bulletKit.getHalfWidth(), worldBounds.getRight() - bulletKit.getHalfWidth());
            bulletKit.setBottom(worldBounds.getTop());
        }
    }
}

package ru.geekbrains.utils;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.SupportKitPool;
import ru.geekbrains.sprite.SupportKit;

public class SupportKitEmitter {

    private static final float GENERATE_INTERVAL = 4f;

    private final Rect worldBounds;
    private final SupportKitPool supportKitPool;
    private float generateTimer;

    public SupportKitEmitter(Rect worldBounds, SupportKitPool supportKitPool) {
        this.worldBounds = worldBounds;
        this.supportKitPool = supportKitPool;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0;

            SupportKit medKit = supportKitPool.obtain();
            medKit.pos.x = Rnd.nextFloat(worldBounds.getLeft() + medKit.getHalfWidth(), worldBounds.getRight() - medKit.getHalfWidth());
            medKit.setBottom(worldBounds.getTop());
        }
    }
}

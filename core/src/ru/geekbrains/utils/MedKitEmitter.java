package ru.geekbrains.utils;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.MedKitPool;
import ru.geekbrains.sprite.MedKit;

public class MedKitEmitter {

    private static final float GENERATE_INTERVAL = 3f;

    private final Rect worldBounds;
    private final MedKitPool medKitPool;

    private float generateTimer;

    public MedKitEmitter(Rect worldBounds, MedKitPool medKitPool) {
        this.worldBounds = worldBounds;
        this.medKitPool = medKitPool;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0;

            MedKit medKit = medKitPool.obtain();
            medKit.pos.x = Rnd.nextFloat(worldBounds.getLeft() + medKit.getHalfWidth(), worldBounds.getRight() - medKit.getHalfWidth());
            medKit.setBottom(worldBounds.getTop());
        }
    }

}

package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.SupportKit;

public class SupportKitPool extends SpritesPool<SupportKit> {

    private final Rect worldBounds;
    private final TextureRegion supportKitRegion;

    public SupportKitPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.supportKitRegion = new TextureRegion(new Texture("textures/supportKit.png"));
    }

    @Override
    protected SupportKit newObject() {
        return new SupportKit(supportKitRegion, worldBounds);
    }
}

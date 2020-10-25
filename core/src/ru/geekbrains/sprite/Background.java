package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Background extends Sprite {

    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }

    public Background(Texture texture, SpriteRegistry registry) {
        this(texture);
        registry.register(this);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}

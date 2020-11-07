package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.entity.CommandPost;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.MedKit;

public class MedKitPool extends SpritesPool<MedKit> {

    private final Rect worldBounds;

    private final TextureRegion medKitRegion;
    private final CommandPost commandPost;


    public MedKitPool(Rect worldBounds, CommandPost commandPost) {
        this.worldBounds = worldBounds;
        this.commandPost = commandPost;
        this.medKitRegion = new TextureRegion(new Texture("textures/medKit.png"));
    }

    @Override
    protected MedKit newObject() {
        return new MedKit(medKitRegion, worldBounds, commandPost);
    }

}

package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.entity.CommandPost;
import ru.geekbrains.math.Rect;

public class MedKit extends Sprite {

    private Rect worldBounds;
    private Vector2 v;

    private CommandPost commandPost;

    private static final int HEAL = 10;

    public MedKit(TextureRegion region, Rect worldBounds, CommandPost commandPost) {
        super(region);
        setHeightProportion(0.03f);
        this.worldBounds = worldBounds;
        v = new Vector2(0, -0.2f);
        this.commandPost = commandPost;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getBottom() < worldBounds.getBottom()) {
            commandPost.heal(getHeal());
            destroy();
        }
    }

    public int getHeal() {
        return HEAL;
    }
}

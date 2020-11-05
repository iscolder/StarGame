package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class GameOverMsg extends Sprite {

    private static final float GAME_OVER_MES_HEIGHT = 0.08f;

    public GameOverMsg(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(GAME_OVER_MES_HEIGHT);
    }

}

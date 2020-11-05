package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.BaseButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class NewGameMsg extends BaseButton {

    private static final float NEW_GAME_MES_HEIGHT = 0.08f;

    private GameScreen gameScreen;

    public NewGameMsg(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(NEW_GAME_MES_HEIGHT);
        setBottom(worldBounds.getBottom());
    }

    @Override
    public void action() {
        gameScreen.newGame();
    }
}

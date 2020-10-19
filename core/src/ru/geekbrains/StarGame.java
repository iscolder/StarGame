package ru.geekbrains;

import com.badlogic.gdx.Game;

import ru.geekbrains.screen.MenuScreen;
import ru.geekbrains.screen.MoveScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MoveScreen());
	}
}

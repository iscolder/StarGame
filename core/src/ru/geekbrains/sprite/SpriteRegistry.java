package ru.geekbrains.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ru.geekbrains.base.Sprite;

public class SpriteRegistry {

    private final List<Sprite> spriteStore = new ArrayList<>();

    public void register(Sprite sprite) {
        spriteStore.add(sprite);
    }

    public void execute(Consumer<Sprite> action) {
        spriteStore.forEach(action);
    }
}

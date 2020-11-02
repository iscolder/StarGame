package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SpritesPool<T extends Sprite> {

    protected final List<T> activeObjects = new ArrayList<>();
    protected final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public T obtain() {
        T object = freeObjects.isEmpty() ? newObject() : freeObjects.remove(freeObjects.size() - 1);
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta) {
        activeObjects.stream().filter(sprite -> !sprite.isDestroyed()).forEach(sprite -> sprite.update(delta));
    }

    public void drawActiveSprites(SpriteBatch batch) {
        activeObjects.stream().filter(sprite -> !sprite.isDestroyed()).forEach(sprite -> sprite.draw(batch));
    }

    public void freeAllDestroyedActiveSprites() {
        activeObjects.stream().filter(Sprite::isDestroyed)
                .collect(Collectors.toList())
                .forEach(s -> {
                    free(s);
                    s.flushDestroy();
                });
    }

    public void dispose() {
        activeObjects.forEach(Sprite::soundStop);
        freeObjects.forEach(Sprite::soundStop);
        activeObjects.clear();
        freeObjects.clear();
    }

    private void free(T object) {
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

}

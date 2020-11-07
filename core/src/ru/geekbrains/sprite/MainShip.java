package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final float RELOAD_INTERVAL = 0.5f;
    private static final int HP = 100;

    private Vector2 v1;

    private boolean pressedLeft;
    private boolean pressedRight;
    private boolean pressedUp;
    private boolean pressedDown;

    private Vector2 destination = new Vector2();

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.v0.set(0.5f, 0);
        this.bulletV.set(0, 0.5f);
        this.reloadInterval = RELOAD_INTERVAL;

        this.hp = HP;
        this.visible = true;

        v1 = new Vector2(0, 0.5f);
    }

    public void respawn(Rect worldBounds) {
        this.hp = HP;
        this.visible = true;
        this.pos.x = worldBounds.pos.x;
        pressedLeft = false;
        pressedRight = false;
        pressedUp = false;
        pressedDown = false;
        destroyed = false;
        reloadInterval = RELOAD_INTERVAL;
        stop();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getTop());
        super.update(delta);

        if (pos.epsilonEquals(destination, 0.001f)) {
            stop();
        } else if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        } else if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        } else if (getTop() > worldBounds.getTop()) {
            setTop(worldBounds.getTop());
            stop();
        } else if (getBottom() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom());
            stop();
        }
    }

    public void dispose() {
        bulletSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        destination = new Vector2(touch);
        v.set(touch.sub(pos));
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;

            case Input.Keys.W:
            case Input.Keys.UP:
                pressedUp = true;
                moveUp();
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                pressedDown = true;
                moveDown();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else if (pressedUp) {
                    moveUp();
                } else if (pressedDown) {
                    moveDown();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else if (pressedUp) {
                    moveUp();
                } else if (pressedDown) {
                    moveDown();
                } else {
                    stop();
                }
                break;

            case Input.Keys.W:
            case Input.Keys.UP:
                pressedUp = false;
                if (pressedDown) {
                    moveDown();
                } else if (pressedLeft) {
                    moveLeft();
                } else if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;

            case Input.Keys.S:
            case Input.Keys.DOWN:
                pressedDown = false;
                if (pressedUp) {
                    moveUp();
                } else if (pressedLeft) {
                    moveLeft();
                } else if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void moveUp() {
        v.set(v1);
    }

    private void moveDown() {
        v.set(v1).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    public boolean isBulletCollision(Rect bullet) {
        return bullet.getRight() >= getLeft() &&
                bullet.getLeft() <= getRight() &&
                bullet.getBottom() <= pos.y &&
                bullet.getTop() >= getBottom();
    }


    public void heal(int heal) {
        hp += heal;
    }

    public void decreaseBulletReloadTime(float bulletInterval) {
        this.reloadInterval -= bulletInterval;
    }

    public float getReloadInterval() {
        return this.reloadInterval;
    }

}

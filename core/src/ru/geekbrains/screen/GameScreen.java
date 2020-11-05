package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.GameOverMsg;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.NewGameMsg;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;
    private Texture bg;
    private Music music;
    private Sound enemyBulletSound;
    private Sound explosionSound;

    private Background background;
    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyShipPool enemyShipPool;
    private MainShip mainShip;
    private EnemyEmitter enemyEmitter;

    private boolean isGameOver;

    private GameOverMsg gameOverMsg;
    private NewGameMsg newGameMsg;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, enemyBulletSound, atlas);

        gameOverMsg = new GameOverMsg(atlas);
        newGameMsg = new NewGameMsg(atlas, this);

        music.setLooping(true);
        music.play();
    }

    public void newGame() {
        isGameOver = false;
        mainShip.respawn();
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        checkGameOver();
        draw();
    }

    private void checkGameOver() {
        if(mainShip.isDestroyed() && !isGameOver) {
            isGameOver = true;
            enemyShipPool.dispose();
            bulletPool.dispose();
            explosionPool.dispose();
            mainShip.dispose();
            music.pause();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOverMsg.resize(worldBounds);
        newGameMsg.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyShipPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        mainShip.dispose();
        explosionSound.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        newGameMsg.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        newGameMsg.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }

        if (isGameOver) return;

        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        mainShip.update(delta);
        enemyEmitter.generate(delta);
    }

    private void checkCollision() {

        if (isGameOver) return;

        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) continue;
            float minDist = mainShip.getHalfWidth() + enemyShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                mainShip.damage(enemyShip.getDamage());
                enemyShip.destroy();
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) continue;

            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }

            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isBulletCollision(bullet) && enemyShip.visible) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }

        }
    }

    private void freeAllDestroyed() {
        if (isGameOver) return;
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();

        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }

        if (isGameOver) {
            gameOverMsg.draw(batch);
            newGameMsg.draw(batch);
        } else {
            mainShip.draw(batch);
            enemyShipPool.drawActiveSprites(batch);
            bulletPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
        }
        batch.end();
    }
}

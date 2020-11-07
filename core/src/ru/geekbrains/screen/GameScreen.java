package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.entity.CommandPost;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletKitPool;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.pool.MedKitPool;
import ru.geekbrains.pool.SupportKitPool;
import ru.geekbrains.pool.SupportShipPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.BulletKit;
import ru.geekbrains.sprite.ButtonNewGame;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.MedKit;
import ru.geekbrains.sprite.MessageGameOver;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.SupportKit;
import ru.geekbrains.sprite.SupportShip;
import ru.geekbrains.sprite.TrackingStar;
import ru.geekbrains.utils.BulletKitEmitter;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.utils.Font;
import ru.geekbrains.utils.MedKitEmitter;
import ru.geekbrains.utils.SupportKitEmitter;

public class GameScreen extends BaseScreen {

    private enum State {PLAYING, GAME_OVER}

    private State state;

    private static final int STAR_COUNT = 64;
    private static final float FONT_SIZE = 0.02f;
    private static final float MARGIN = 0.01f;

    private static final String FRAGS = "FRAGS: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "LEVEL: ";
    private static final String COMMAND_POST = "COMMAND POST: ";
    private static final String BULLET_RELOAD = "RELOAD: ";

    private TextureAtlas atlas;
    private Texture bg;
    private Music music;
    private Sound enemyBulletSound;
    private Sound explosionSound;
    private Sound supportBulletSound;

    private Background background;
    private Star[] stars;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;

    private EnemyShipPool enemyShipPool;
    private EnemyEmitter enemyEmitter;

    private MainShip mainShip;
    private SupportShipPool supportShipPool;

    private MedKitPool medKitPool;
    private MedKitEmitter medKitEmitter;

    private SupportKitPool supportKitPool;
    private SupportKitEmitter supportKitEmitter;

    private BulletKitPool bulletKitPool;
    private BulletKitEmitter bulletKitEmitter;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private CommandPost commandPost;

    private int frags;
    private Font font;

    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private StringBuilder sbCommandPost;
    private StringBuilder sbBulletReload;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        supportBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        commandPost = new CommandPost();

        background = new Background(bg);

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, worldBounds, commandPost);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, enemyBulletSound, atlas);
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);

        medKitPool = new MedKitPool(worldBounds, commandPost);
        medKitEmitter = new MedKitEmitter(worldBounds, medKitPool);

        supportKitPool = new SupportKitPool(worldBounds);
        supportKitEmitter = new SupportKitEmitter(worldBounds, supportKitPool);

        bulletKitPool = new BulletKitPool(worldBounds);
        bulletKitEmitter = new BulletKitEmitter(worldBounds, bulletKitPool);

        supportShipPool = new SupportShipPool(bulletPool, explosionPool, worldBounds, atlas, supportBulletSound);

        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);

        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        sbCommandPost = new StringBuilder();
        sbBulletReload = new StringBuilder();

        music.setLooping(true);
        music.play();

        state = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkAfterUpdate();
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyShipPool.dispose();
        medKitPool.dispose();
        supportKitPool.dispose();
        bulletKitPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        supportBulletSound.dispose();
        mainShip.dispose();
        explosionSound.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }

        explosionPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            medKitPool.updateActiveSprites(delta);
            supportKitPool.updateActiveSprites(delta);
            supportShipPool.updateActiveSprites(delta);
            bulletKitPool.updateActiveSprites(delta);
            mainShip.update(delta);
            enemyEmitter.generate(delta, frags);
            medKitEmitter.generate(delta);
            supportKitEmitter.generate(delta);
            bulletKitEmitter.generate(delta);
        }
    }

    private void checkAfterUpdate() {
        if (commandPost.destroyed()) {
            state = State.GAME_OVER;
        }
    }

    private void checkCollision() {
        if (state == State.GAME_OVER) return;

        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        List<SupportShip> supportShipList = supportShipPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<MedKit> medKitList = medKitPool.getActiveObjects();
        List<BulletKit> bulletKitList = bulletKitPool.getActiveObjects();
        List<SupportKit> supportKitList = supportKitPool.getActiveObjects();

        checkShipCollisions(enemyShipList, supportShipList);
        checkBulletCollisions(bulletList, enemyShipList, supportShipList);
        checkMedKitCollisions(medKitList);
        checkBulletKitCollisions(bulletKitList);
        checkSupportKitCollisions(supportKitList);
    }

    private void checkShipCollisions(List<EnemyShip> enemyShipList, List<SupportShip> supportShips) {
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) continue;
            // check collisions with main ship
            float minDist = mainShip.getHalfWidth() + enemyShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                mainShip.damage(5 * enemyShip.getDamage());
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
                enemyShip.damage(mainShip.getDamage());
                return;
            }

            if (!enemyShip.isDestroyed()) {
                // check collisions with supporting ships
                for (SupportShip supportShip : supportShips) {
                    if (enemyShip.isDestroyed()) return;
                    if (supportShip.isDestroyed()) continue;
                    minDist = supportShip.getHalfWidth() + enemyShip.getHalfWidth();
                    if (enemyShip.pos.dst(supportShip.pos) < minDist) {
                        supportShip.damage(5 * enemyShip.getDamage());
                        enemyShip.damage(supportShip.getDamage());
                        return;
                    }
                }

            }
        }
    }

    private void checkBulletCollisions(List<Bullet> bulletList, List<EnemyShip> enemyShipList, List<SupportShip> supportShipList) {
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) continue;
            if (bullet.getOwner() instanceof EnemyShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                    bullet.destroy();
                } else {
                    for (SupportShip supportShip : supportShipList) {
                        if (supportShip.isBulletCollision(bullet)) {
                            supportShip.damage(bullet.getDamage());
                            bullet.destroy();
                            break;
                        }
                    }
                }
            } else {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.isBulletCollision(bullet) && enemyShip.visible) {
                        enemyShip.damage(bullet.getDamage());
                        if (enemyShip.isDestroyed()) {
                            frags++;
                        }
                        bullet.destroy();
                    }
                }
            }
        }
    }

    private void checkMedKitCollisions(List<MedKit> medKitList) {
        for (MedKit medKit : medKitList) {
            if (medKit.isDestroyed()) continue;
            float minDist = mainShip.getHalfWidth() + medKit.getHalfWidth();
            if (medKit.pos.dst(mainShip.pos) < minDist) {
                mainShip.heal(medKit.getHeal());
                medKit.destroy();
                return;
            }
        }
    }

    private void checkBulletKitCollisions(List<BulletKit> bulletKitList) {
        for (BulletKit bulletKit : bulletKitList) {
            if (bulletKit.isDestroyed()) continue;
            float minDist = mainShip.getHalfWidth() + bulletKit.getHalfWidth();
            if (bulletKit.pos.dst(mainShip.pos) < minDist) {
                mainShip.decreaseBulletReloadTime(bulletKit.getBulletInterval());
                bulletKit.destroy();
                return;
            }
        }
    }

    private void checkSupportKitCollisions(List<SupportKit> supportKitList) {
        for (SupportKit supportKit : supportKitList) {
            if (supportKit.isDestroyed()) continue;
            float minDist = mainShip.getHalfWidth() + supportKit.getHalfWidth();
            if (supportKit.pos.dst(mainShip.pos) < minDist) {
                supportKit.destroy();
                SupportShip supportShip = supportShipPool.obtain();
                supportShip.set(enemyEmitter.getLevel());
                supportShip.pos.set(mainShip.pos.x, 0);
                supportShip.setBottom(worldBounds.getBottom());
                return;
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        medKitPool.freeAllDestroyedActiveSprites();
        supportShipPool.freeAllDestroyedActiveSprites();
        supportKitPool.freeAllDestroyedActiveSprites();
        bulletKitPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();

        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);

        if (state == State.PLAYING) {
            mainShip.draw(batch);
            enemyShipPool.drawActiveSprites(batch);
            bulletPool.drawActiveSprites(batch);
            medKitPool.drawActiveSprites(batch);
            supportShipPool.drawActiveSprites(batch);
            supportKitPool.drawActiveSprites(batch);
            bulletKitPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);

        sbBulletReload.setLength(0);
        font.draw(batch, sbBulletReload.append(BULLET_RELOAD).append(String.format("%.3f", mainShip.getReloadInterval())), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - 5 * MARGIN);

        sbCommandPost.setLength(0);
        font.draw(batch, sbCommandPost.append(COMMAND_POST).append(commandPost.getHp()), worldBounds.pos.x, worldBounds.getBottom() + 3 * MARGIN, Align.center);

    }

    public void startNewGame() {
        state = State.PLAYING;
        mainShip.respawn(worldBounds);
        bulletPool.freeAllActive();
        enemyShipPool.freeAllActive();
        explosionPool.freeAllActive();
        medKitPool.freeAllActive();
        supportShipPool.freeAllActive();
        supportKitPool.freeAllActive();
        bulletKitPool.freeAllActive();
        commandPost.respawn();
        frags = 0;
    }

}

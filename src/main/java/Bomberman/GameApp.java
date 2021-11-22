package Bomberman;

import Bomberman.Menu.BombermanGameMenu;
import Bomberman.Menu.BombermanMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import Bomberman.Components.PlayerComponent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.*;


public class GameApp extends GameApplication {
    public static boolean sound_enabled = true;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(SCENE_WIDTH);
        gameSettings.setHeight(SCENE_HEIGHT);
        gameSettings.setTitle(GAME_TITLE);
        gameSettings.setVersion(GAME_VERSION);

//        gameSettings.setFullScreenAllowed(true);
//        gameSettings.setFullScreenFromStart(true);
        gameSettings.setIntroEnabled(false);
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.setFontUI("Quinquefive-Ea6d4.ttf");
        gameSettings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new BombermanMenu();
            }

            @Override
            public FXGLMenu newGameMenu() {
                return new BombermanGameMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameFactory());
        nextLevel();
        spawn("background");
    }

    private static Entity getPlayer() {
        return getGameWorld().getSingleton(GameType.PLAYER);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                getPlayer().getComponent(PlayerComponent.class).placeBomb(geti("flame"));
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                var entityGroup = getGameWorld().getGroup(GameType.ENEMY1, GameType.ENEMY2);
                if (entityGroup.getSize() == 0) {
                    play("next_level.wav");
                    getGameScene().getViewport().fade(() -> {
                        nextLevel();
                    });

                }

            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                play("slash.wav");
                onPlayerDied();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                play("slash.wav");
                onPlayerDied();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FIRE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                if (getPlayer().getComponent(PlayerComponent.class).getPlayerSkin() == PlayerSkin.NORMAL) {
                    onPlayerDied();
                }
            }
        });


    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalSoundVolume(sound_enabled ? 0.3 : 0.0);
        getSettings().setGlobalMusicVolume(sound_enabled ? 0.3 : 0.0);
        loopBGM("stage_theme.mp3");

    }

    @Override
    protected void onUpdate(double tpf) {
        inc("levelTime", -tpf);

        if (getd("levelTime") <= 0.0) {
            showMessage("you lose");
            set("levelTime", TIME_LEVEL);
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        vars.put("score", 0);
        vars.put("flame", 1);
        vars.put("speed", SPEED);
        vars.put("bomb", 1);
        vars.put("levelTime", TIME_LEVEL);

    }

    @Override
    protected void initUI() {
        Label score = new Label();
        score.setTextFill(Color.BLACK);
        score.setFont(Font.font(20));
        score.textProperty().bind(getip("score").asString("Score: %d"));
        addUINode(score, 76, 16);

        Label level = new Label();
        level.setTextFill(Color.BLACK);
        level.setFont(Font.font(20));
        level.textProperty().bind(getip("level").asString("Level: %d"));
        addUINode(level, 200, 16);

        Label flame = new Label();
        flame.setTextFill(Color.BLACK);
        flame.setFont(Font.font(20));
        flame.textProperty().bind(getip("flame").asString("Flame: %d"));
        addUINode(flame, 330, 16);

        Label speed = new Label();
        speed.setTextFill(Color.BLACK);
        speed.setFont(Font.font(20));
        speed.textProperty().bind(getip("speed").asString("Speed: %d"));
        addUINode(speed, 480, 16);

        Label bomb = new Label();
        bomb.setTextFill(Color.BLACK);
        bomb.setFont(Font.font(20));
        bomb.textProperty().bind(getip("bomb").asString("Bomb: %d"));
        addUINode(bomb, 650, 16);

        Label timeLabel = new Label();
        timeLabel.setTextFill(Color.BLACK);
        timeLabel.setFont(Font.font(20.0));
        timeLabel.textProperty().bind(FXGL.getdp("levelTime").asString("Time: %.0f"));
        FXGL.addUINode(timeLabel, 780, 16);
    }

    public void onPlayerDied() {
        play("playerDie.wav");
        getPlayer().getComponent(PlayerComponent.class).die();
        getGameTimer().runOnceAfter(() -> {
            getGameScene().getViewport().fade(() -> {
                setLevel();
            });
        }, Duration.seconds(0.9));
    }

    private void setLevel() {
        setLevelFromMap("level" + geti("level") + ".tmx");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport.bindToEntity(getPlayer(), getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
        set("levelTime", TIME_LEVEL);
    }

    private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            showMessage("Win !!!");
            return;
        }
        inc("level", +1);
        setLevel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

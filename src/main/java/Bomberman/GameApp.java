package Bomberman;

import Bomberman.Components.Enemy.*;
import Bomberman.Menu.GameMenu;
import Bomberman.Menu.MainMenu;
import Bomberman.UI.EndingScene;
import Bomberman.UI.StageStartScene;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import Bomberman.Components.PlayerComponent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

import static Bomberman.Components.PlayerComponent.*;
import static Bomberman.DynamicEntityState.State.*;
import static Bomberman.GameType.*;
import static Bomberman.Sounds.SoundEffect.*;
import static com.almasb.fxgl.dsl.FXGL.*;


public class GameApp extends GameApplication {
    public static final int SCENE_WIDTH = 1280;
    public static final int SCENE_HEIGHT = 720;
    public static final int GAME_WORLD_WIDTH = 1488;
    public static final int GAME_WORLD_HEIGHT = 720;
    public static final String GAME_TITLE = "BOMBER\n MAN";
    public static final String GAME_VERSION = "1.0";
    public static final Double UI_FONT_SIZE = 36.0;
    public static final int MAX_LEVEL = 6;
    public static final int STARTING_LEVEL = 0;
    public static final int TILED_SIZE = 48;
    public static final double TIME_LEVEL = 280.0;

    private Map temp = new HashMap();
    private boolean isLoading = false;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(SCENE_WIDTH);
        gameSettings.setHeight(SCENE_HEIGHT);
        gameSettings.setTitle(GAME_TITLE);
        gameSettings.setVersion(GAME_VERSION);

        gameSettings.setFullScreenAllowed(true);
        gameSettings.setFullScreenFromStart(true);

        gameSettings.setIntroEnabled(false);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setFontUI("game_font.ttf");
        gameSettings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }

            @Override
            public FXGLMenu newGameMenu() {
                return new GameMenu();
            }

        });
//        gameSettings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameFactory());
        nextLevel();
        spawn("background");
    }

    private Entity getPlayer() {
        return getGameWorld().getSingleton(PLAYER);
    }

    private PlayerComponent getPlayerComponent() {
        return getPlayer().getComponent(PlayerComponent.class);
    }


    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayerComponent().up();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayerComponent().down();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayerComponent().left();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayerComponent().right();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                getPlayerComponent().placeBomb(geti("flame"));
            }
        }, KeyCode.SPACE);

//        getInput().addAction(new UserAction("test") {
//            @Override
//            protected void onActionBegin() {
//                isLoading = true;
//                getPlayerComponent().setBombInvalidation(true);
//                turnOffMusic();
//                play("next_level.wav");
//                getGameTimer().runOnceAfter(() -> {
//                    turnOnMusic();
//                    nextLevel();
//                }, Duration.seconds(4));
//            }
//
//        }, KeyCode.P);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        onCollisionBegin(PLAYER, DOOR, (player, door) -> {
            if (isLoading == false
                    && getGameWorld().getGroup(ENEMY1, ENEMY2,
                    ENEMY3, ENEMY4, ENEMY5).getSize() == 0) {
                isLoading = true;
                getPlayerComponent().setBombInvalidation(true);
                turnOffMusic();
                play("next_level.wav");
                getGameTimer().runOnceAfter(() -> {
                    turnOnMusic();
                    nextLevel();
                }, Duration.seconds(4));
            }
        });

        onCollisionBegin(PLAYER, ENEMY1, (player, enemy) -> {
            if (enemy.getComponent(Enemy1.class).getState() != DIE
                    && getPlayerComponent().getState() != DIE) {
                onPlayerDied();
            }
        });
        onCollisionBegin(PLAYER, ENEMY2, (player, enemy) -> {
            if (enemy.getComponent(Enemy2.class).getState() != DIE
                    && getPlayerComponent().getState() != DIE) {
                onPlayerDied();
            }
        });
        onCollisionBegin(PLAYER, ENEMY3, (player, enemy) -> {
            if (enemy.getComponent(Enemy3.class).getState() != DIE
                    && getPlayerComponent().getState() != DIE) {
                onPlayerDied();
            }
        });
        onCollisionBegin(PLAYER, ENEMY4, (player, enemy) -> {
            if (enemy.getComponent(Enemy4.class).getState() != DIE
                    && getPlayerComponent().getState() != DIE) {
                onPlayerDied();
            }
        });
        onCollisionBegin(PLAYER, ENEMY5, (player, enemy) -> {
            if (enemy.getComponent(Enemy5.class).getState() != DIE
                    && getPlayerComponent().getState() != DIE) {
                onPlayerDied();
            }
        });
        onCollisionBegin(PLAYER, FLAME, (player, flame) -> {
            if (getPlayerComponent().getPlayerSkin() == PlayerSkin.NORMAL
                    && getPlayerComponent().getState() != DIE) {
                onPlayerDied();
            }
        });
    }

    @Override
    protected void onPreInit() {
        unmute();
        loopBGM("stage_theme.mp3");
    }

    @Override
    protected void onUpdate(double tpf) {
        if (isLoading) {
            return;
        }
        inc("levelTime", -tpf);

        if (getd("levelTime") <= 0.0) {
            showMessage("Time Up !!!");
            onPlayerDied();
            set("levelTime", TIME_LEVEL);
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        vars.put("life", 3);
        vars.put("enemies", 0);
        vars.put("score", 0);
        vars.put("flame", 1);
        vars.put("speed", PLAYER_SPEED);
        vars.put("bomb", 1);
        vars.put("levelTime", TIME_LEVEL);
    }

    @Override
    protected void initUI() {
        addILabelUI("level", "🚩 %d", 35, 25);
        addILabelUI("life", "💜 %d", 160, 25);
        addILabelUI("score", "💵  %d", 300, 25);
        addILabelUI("flame", "🔥 %d", 560, 25);
        addILabelUI("speed", "👟  %d", 670, 25);
        addILabelUI("bomb", "💣 %d", 840, 25);
        addILabelUI("enemies", "👻 %d", 1010, 25);
        addDLabelUI("levelTime", "⏰ %.0f", 1140, 25);
    }

    public void addILabelUI(String varName, String title, double x, double y) {
        Label text = new Label();
        text.setTextFill(Color.BLACK);
        text.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        text.textProperty().bind(getip(varName).asString(title));
        addUINode(text, x, y);
    }

    public void addDLabelUI(String varName, String title, double x, double y) {
        Label text = new Label();
        text.setTextFill(Color.BLACK);
        text.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        text.textProperty().bind(getdp(varName).asString(title));
        addUINode(text, x, y);
    }

    public void onPlayerDied() {
        turnOffMusic();
        play("player_die.wav");
        isLoading = true;
        getPlayerComponent().setState(DIE);
        getPlayerComponent().setBombInvalidation(true);
        getGameTimer().runOnceAfter(() -> {
            getGameScene().getViewport().fade(() -> {
                turnOnMusic();
                inc("life", -1);
                if (geti("life") > 0) {
                    setLevel();
                } else {
                    turnOffMusic();
                    getSceneService().pushSubScene(new EndingScene("   GAME OVER !!!\n\n\n\n   DO YOUR BEST"));
                }
            });
        }, Duration.seconds(2.2));
    }

    private void setLevel() {
        isLoading = false;
        setLevelFromMap("level" + geti("level") + ".tmx");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport.bindToEntity(getPlayer(), getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);

        set("score", temp.get("score"));
        set("flame", temp.get("flame"));
        set("bomb", temp.get("bomb"));
        set("levelTime", TIME_LEVEL);
        set("enemies", getGameWorld().getGroup(ENEMY1,
                ENEMY2, ENEMY3, ENEMY4, ENEMY5).getSize());
    }

    private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            turnOffMusic();
            getSceneService().pushSubScene(new EndingScene("CONGRATULATIONS !!!\n\n\n\n    GOOD BYE"));
            return;
        }
        inc("level", +1);
        if (geti("level") > 1) {
            turnOffMusic();
            getSceneService().pushSubScene(new StageStartScene());
        }

        temp.put("score", geti("score"));
        temp.put("flame", geti("flame"));
        temp.put("bomb", geti("bomb"));

        setLevel();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

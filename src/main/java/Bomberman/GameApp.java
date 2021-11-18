package Bomberman;

import Bomberman.Components.Enemy.Enemy1;
import Bomberman.Components.Enemy.Enemy2;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.Viewport;
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

import java.util.Map;

import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.*;


public class GameApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(SCENE_WIDTH);
        gameSettings.setHeight(SCENE_HEIGHT);
        gameSettings.setTitle(GAME_TITLE);
        gameSettings.setVersion(GAME_VERSION);
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
                door.removeFromWorld();
                getGameScene().getViewport().fade(() -> {
                    nextLevel();
                });
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                onPlayerDied();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                onPlayerDied();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FIRE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                onPlayerDied();
            }
        });


    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        vars.put("score", 0);
        vars.put("flame", 1);
        vars.put("speed", SPEED);
        vars.put("bomb", 1);

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
    }

    private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            showMessage("Bạn đã thắng !!!");
            return;
        }
        inc("level", +1);
        setLevel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

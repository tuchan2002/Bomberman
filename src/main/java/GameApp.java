import Components.Enemy.Enemy1;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import Components.PlayerComponent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Map;

import static Constants.Constanst.*;
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

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport.bindToEntity(getPlayer(), SCENE_WIDTH/2, SCENE_HEIGHT / 2);
        viewport.setLazy(true);
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
                getPlayer().getComponent(PlayerComponent.class).placeBomb(geti("damage"));
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.WOOD) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity wood) {
                wood.removeFromWorld();
                inc("score", 10);
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.BRICK) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity brick) {
                fire.removeFromWorld();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.INCREASEDAMAGE) {

            @Override
            protected void onCollisionBegin(Entity player, Entity increaseDamage) {
                increaseDamage.removeFromWorld();
                play("increaseDamage.wav");
                inc("damage", 1);
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.INCREASEDAMAGE) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity increaseDamage) {
                fire.removeFromWorld();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR) {

            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                door.removeFromWorld();
                getGameScene().getViewport().fade(() -> {
                    nextLevel();
                });
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity brick) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.WOOD) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity wood) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.INCREASEDAMAGE) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity increaseDamage) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity door) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        vars.put("score", 0);
        vars.put("damage", 1);

    }

    @Override
    protected void initUI() {
        Label score = new Label();
        score.setTextFill(Color.BLACK);
        score.setFont(Font.font(20));
        score.textProperty().bind(getip("score").asString("Score: %d"));
        addUINode(score, 76, 16);

        Label damage = new Label();
        damage.setTextFill(Color.BLACK);
        damage.setFont(Font.font(20));
        damage.textProperty().bind(getip("damage").asString("Damage: %d"));
        addUINode(damage, 200, 16);

        Label level = new Label();
        level.setTextFill(Color.BLACK);
        level.setFont(Font.font(20));
        level.textProperty().bind(getip("level").asString("Level: %d"));
        addUINode(level, 330, 16);
    }

    private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            showMessage("Chúc mừng bạn đã phá đảo game này !!!");
            return;
        }
        inc("level", +1);
        setLevelFromMap("level" + geti("level") + ".tmx");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

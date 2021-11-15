import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import component.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;


public class GameApp extends GameApplication {
    private Entity player;
    private static final int MAX_LEVEL = 5;
    private static final int STARTING_LEVEL = 0;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(896);
        gameSettings.setHeight(704);
        gameSettings.setTitle("Basic Game App");
        gameSettings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameFactory());
        player = null;
        nextLevel();
        player = spawn("player", 64, 64);

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 1200, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    public PlayerComponent getPlayerComponent() {
        return player.getComponent(PlayerComponent.class);
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
                getPlayerComponent().placeBomb(geti("damage"));
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

        onCollisionOneTimeOnly(GameType.PLAYER, GameType.DOOR, (player, door) -> {
            door.removeFromWorld();
            getGameScene().getViewport().fade(() -> {
                nextLevel();
            });
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
        Label score =  new Label();
        score.setTextFill(Color.BLACK);
        score.setFont(Font.font(20));
        score.textProperty().bind(getip("score").asString("Score: %d"));
        addUINode(score,76,16);

        Label damage =  new Label();
        damage.setTextFill(Color.BLACK);
        damage.setFont(Font.font(20d));
        damage.textProperty().bind(getip("damage").asString("Damage: %d"));
        addUINode(damage,200,16);
    }

    public void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            showMessage("OK !!!");
            return;
        }

        inc("level", +1);

        setLevel(geti("level"));
    }

    public void setLevel(int levelNum) {
        if (player != null) {
            player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(64, 64));
            player.setZIndex(Integer.MAX_VALUE);
        }

        Level level = setLevelFromMap("level" + levelNum  + ".tmx");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

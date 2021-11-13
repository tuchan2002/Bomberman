import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import component.PlayerComponent;
import javafx.scene.input.KeyCode;


public class GameApp extends GameApplication {
    private Entity player;
    private Entity ghost;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(704);
        gameSettings.setHeight(704);
        gameSettings.setTitle("Basic Game App");
        gameSettings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameFactory());
        FXGL.setLevelFromMap("level1.tmx");


        player = FXGL.spawn("player", 64, 64);
        ghost = FXGL.spawn("Ghost", 300, 200);

        Viewport viewport = FXGL.getGameScene().getViewport();
        viewport.setBounds(0, 0, 1200, FXGL.getAppHeight());
        viewport.bindToEntity(player, FXGL.getAppWidth() / 2, FXGL.getAppHeight() / 2);
        viewport.setLazy(true);
    }

    public PlayerComponent getPlayerComponent() {
        return player.getComponent(PlayerComponent.class);
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayerComponent().up();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.W);

        FXGL.getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayerComponent().down();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.S);

        FXGL.getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayerComponent().left();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayerComponent().right();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                getPlayerComponent().placeBomb();
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 0);
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.GHOST) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity ghost) {
                FXGL.play("ghost.wav");
                ghost.setY(200 + Math.random() * 300);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.WOOD) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity wood) {
                wood.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.BRICK) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity brick) {
                fire.removeFromWorld();
                System.out.println("OK");
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}

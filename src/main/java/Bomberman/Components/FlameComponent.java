package Bomberman.Components;

import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FlameComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animationFlame;

    public FlameComponent() {
        PhysicsWorld physics = getPhysicsWorld();

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.BRICK) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity brick) {
                fire.removeFromWorld();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.WOOD) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity wood) {
                wood.removeFromWorld();
                inc("score", 10);
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.POWERUP_FLAMES) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity powerup) {
                powerup.removeFromWorld();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.POWERUP_BOMBS) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity powerup) {
                powerup.removeFromWorld();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.POWERUP_SPEED) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity powerup) {
                powerup.removeFromWorld();
            }
        });


        animationFlame = new AnimationChannel(image("fire.png"), 8, 64, 64,  Duration.seconds(0.4), 0, 7);

        texture = new AnimatedTexture(animationFlame);
        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

}

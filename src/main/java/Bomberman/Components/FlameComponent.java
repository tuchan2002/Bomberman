package Bomberman.Components;

import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static Bomberman.Constants.Constanst.*;

public class FlameComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animationFlame;

    public FlameComponent() {
        PhysicsWorld physics = getPhysicsWorld();

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.WALL) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity wall) {
                fire.removeFromWorld();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.BRICK) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity brick) {
                Entity bBreak = spawn("brick_break", new SpawnData(brick.getX(), brick.getY()));
                brick.removeFromWorld();
                fire.removeFromWorld();
                getGameTimer().runOnceAfter(() -> {
                    bBreak.removeFromWorld();
                }, Duration.seconds(0.4));
                inc("score", 10);
            }
        });


        animationFlame = new AnimationChannel(image("bomb_exploded_1.png"), 3, TILED_SIZE, TILED_SIZE,  Duration.seconds(0.4), 0, 2);

        texture = new AnimatedTexture(animationFlame);
        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

}

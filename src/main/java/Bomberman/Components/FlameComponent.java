package Bomberman.Components;

import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static Bomberman.GameApp.TILED_SIZE;
import static com.almasb.fxgl.dsl.FXGL.*;
public class FlameComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animationFlame;

    public FlameComponent() {
        onCollisionBegin(GameType.FLAME, GameType.WALL, (flame, wall) -> {
            flame.removeFromWorld();
        });
        onCollisionBegin(GameType.FLAME, GameType.BRICK, (flame, brick) -> {
            Entity brickBreak = spawn("brick_break", new SpawnData(brick.getX(), brick.getY()));
            brick.removeFromWorld();
            flame.removeFromWorld();
            getGameTimer().runOnceAfter(() -> {
                brickBreak.removeFromWorld();
            }, Duration.seconds(0.4));
            inc("score", 10);
        });

        animationFlame = new AnimationChannel(image("bomb_exploded.png"), 3, TILED_SIZE, TILED_SIZE,  Duration.seconds(0.4), 0, 2);

        texture = new AnimatedTexture(animationFlame);
        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

}

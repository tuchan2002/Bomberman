package Bomberman.Components;

import Bomberman.Components.Enemy.Enemy5;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import Bomberman.GameType;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import java.util.ArrayList;

import static Bomberman.GameApp.TILED_SIZE;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BombComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animation;
    private ArrayList<Entity> listFlame = new ArrayList<>();

    private Entity virtualBomb;

    public BombComponent() {
        onCollisionEnd(GameType.PLAYER, GameType.BOMB, (player, bomb) -> {
            if (entity != null && virtualBomb == null) {
                virtualBomb = spawn("virtual_bomb", new SpawnData(bomb.getX(), bomb.getY()));
            }
        });

        animation = new AnimationChannel(image("bomb.png"), 3, TILED_SIZE, TILED_SIZE, Duration.seconds(0.3), 0, 2);
        texture = new AnimatedTexture(animation);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public void explode(int flames) {
        for (int i = 1; i <= flames; i++) {
            if (i != flames) {
                listFlame.add(spawn("horizontal_flame", new SpawnData(entity.getX() + TILED_SIZE * i, entity.getY())));
                listFlame.add(spawn("horizontal_flame", new SpawnData(entity.getX() - TILED_SIZE * i, entity.getY())));
                listFlame.add(spawn("vertical_flame", new SpawnData(entity.getX(), entity.getY() + TILED_SIZE * i)));
                listFlame.add(spawn("vertical_flame", new SpawnData(entity.getX(), entity.getY() - TILED_SIZE * i)));
            } else {
                listFlame.add(spawn("top_right_flame", new SpawnData(entity.getX() + TILED_SIZE * i, entity.getY())));
                listFlame.add(spawn("top_left_flame", new SpawnData(entity.getX() - TILED_SIZE * i, entity.getY())));
                listFlame.add(spawn("top_down_flame", new SpawnData(entity.getX(), entity.getY() + TILED_SIZE * i)));
                listFlame.add(spawn("top_up_flame", new SpawnData(entity.getX(), entity.getY() - TILED_SIZE * i)));
            }
        }
        listFlame.add(spawn("central_flame", new SpawnData(entity.getX(), entity.getY())));

        getGameTimer().runOnceAfter(() -> {
            for (int i = 0; i < listFlame.size(); i++) {
                listFlame.get(i).removeFromWorld();
            }
        }, Duration.seconds(0.4));

        entity.removeFromWorld();
        if (virtualBomb != null) {
            virtualBomb.removeFromWorld();
        }
    }

}

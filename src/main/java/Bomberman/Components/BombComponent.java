package Bomberman.Components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import Bomberman.GameType;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import java.util.ArrayList;

import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BombComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animation;
    private ArrayList<Entity> listFire = new ArrayList<Entity>();

    public BombComponent() {
        animation = new AnimationChannel(image("bomb.png"), 3, TILED_SIZE, TILED_SIZE,  Duration.seconds(0.3), 0, 2);

        texture = new AnimatedTexture(animation);
        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public void explode(int damageLevel) {
        for (int i = 1; i <= damageLevel; i++) {
            listFire.add(spawn("fire", new SpawnData(entity.getX() + FIRE_SIZE * i, entity.getY())));
            listFire.add(spawn("fire", new SpawnData(entity.getX() - FIRE_SIZE * i, entity.getY())));
            listFire.add(spawn("fire", new SpawnData(entity.getX(), entity.getY() + FIRE_SIZE * i)));
            listFire.add(spawn("fire", new SpawnData(entity.getX(), entity.getY() - FIRE_SIZE * i)));
        }
        listFire.add(spawn("fire", new SpawnData(entity.getX(), entity.getY())));

        getGameTimer().runOnceAfter(() -> {
            for (int i = 0; i < listFire.size(); i++) {
                listFire.get(i).removeFromWorld();
            }
        }, Duration.seconds(0.4));


        entity.removeFromWorld();
    }

}

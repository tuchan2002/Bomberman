package Bomberman.Components.Enemy;

import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.image;

public class Enemy2 extends Enemy {

    public Enemy2() {
        super(-ENEMY_SPEED, 0, 1, "enemy2.png");
        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity brick) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity FRAME_SIZE) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity door) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.BOMB) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity door) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.FLAME, GameType.ENEMY2) {

            @Override
            protected void onCollisionBegin(Entity flame, Entity enemy2) {
                enemy2.getComponent(Enemy2.class).die();
                getGameTimer().runOnceAfter(() -> {
                    enemy2.removeFromWorld();
                }, Duration.seconds(2.4));
            }
        });

    }

    @Override
    public void turn() {
        super.turn();
        speedFactor = Math.random() > 0.6 ? 1 : 2;
    }
}

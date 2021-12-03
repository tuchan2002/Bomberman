package Bomberman.Components.Enemy;

import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static Bomberman.Constants.Constanst.*;

public class Enemy1 extends Enemy {
    public Enemy1() {
        super(-ENEMY_SPEED, 0, 1,3, "enemy1.png");

        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity brick) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity wall) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity door) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.BOMB) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity door) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.FLAME, GameType.ENEMY1) {

            @Override
            protected void onCollisionBegin(Entity flame, Entity enemy1) {
                enemy1.getComponent(Enemy1.class).die();
                getGameTimer().runOnceAfter(() -> {
                    enemy1.removeFromWorld();
                }, Duration.seconds(2.4));
            }
        });
    }

}

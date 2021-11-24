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
    private double dx = -ENEMY_SPEED;
    private double dy = 0;

    public Enemy2() {
        super("enemy2.png");
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
        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.ENEMY2) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity enemy2) {
                enemy2.getComponent(Enemy2.class).die();
                getGameTimer().runOnceAfter(() -> {
                    enemy2.removeFromWorld();
                }, Duration.seconds(1.5));
            }
        });

    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);

        super.onUpdate(tpf);
    }

    public void turn() {
        if (dx < 0) {
            entity.translateX(4);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = MoveDirection.DOWN;
            } else {
                currentMoveDir = MoveDirection.UP;
            }
        } else if (dx > 0) {
            entity.translateX(-4);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = MoveDirection.DOWN;
            } else {
                currentMoveDir = MoveDirection.UP;
            }
        } else if (dy < 0.0) {
            entity.translateY(4);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = MoveDirection.RIGHT;
            } else {
                currentMoveDir = MoveDirection.LEFT;
            }
        } else {
            entity.translateY(-4);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = MoveDirection.RIGHT;
            } else {
                currentMoveDir = MoveDirection.LEFT;
            }
        }

    }

    private double getRandomSpeed() {
        double x = Math.random() > 0.5 ? 1 : 1.6;
        double speed = Math.random() > 0.5 ? ENEMY_SPEED * x : -ENEMY_SPEED * x;

        return speed;
    }

    public void die() {
        dx = 0;
        dy = 0;
        currentMoveDir = MoveDirection.DIE;
    }

}

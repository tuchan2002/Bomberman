package Bomberman.Components.Enemy;

import Bomberman.Constants.Constanst;
import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.util.Duration;

import static Bomberman.Constants.Constanst.ENEMY_SPEED;
import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;

public class Enemy4 extends Enemy {
    private double dx = -ENEMY_SPEED * 2;
    private double dy = 0;

    public Enemy4() {
        super("enemy4.png");
        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY4, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity brick) {
                enemy.getComponent(Enemy4.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY4, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity FRAME_SIZE) {
                enemy.getComponent(Enemy4.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY4, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity door) {
                enemy.getComponent(Enemy4.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY4, GameType.BOMB) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity door) {
                enemy.getComponent(Enemy4.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.FLAME, GameType.ENEMY4) {

            @Override
            protected void onCollisionBegin(Entity flame, Entity enemy) {
                enemy.getComponent(Enemy4.class).die();
                getGameTimer().runOnceAfter(() -> {
                    enemy.removeFromWorld();
                }, Duration.seconds(2.4));
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
                currentMoveDir = Constanst.MoveDirection.DOWN;
            } else {
                currentMoveDir = Constanst.MoveDirection.UP;
            }
        } else if (dx > 0) {
            entity.translateX(-4);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = Constanst.MoveDirection.DOWN;
            } else {
                currentMoveDir = Constanst.MoveDirection.UP;
            }
        } else if (dy < 0.0) {
            entity.translateY(4);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = Constanst.MoveDirection.RIGHT;
            } else {
                currentMoveDir = Constanst.MoveDirection.LEFT;
            }
        } else {
            entity.translateY(-4);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = Constanst.MoveDirection.RIGHT;
            } else {
                currentMoveDir = Constanst.MoveDirection.LEFT;
            }
        }

    }

    private double getRandomSpeed() {
        double speed = Math.random() > 0.5 ? ENEMY_SPEED * 2 : -ENEMY_SPEED * 2;

        return speed;
    }

    @Override
    public void die() {
        dx = 0;
        dy = 0;
        currentMoveDir = Constanst.MoveDirection.DIE;
    }

}

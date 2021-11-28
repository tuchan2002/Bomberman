package Bomberman.Components.Enemy;

import Bomberman.Collisions.FlameEnemy3Handler;
import Bomberman.Components.PlayerComponent;
import Bomberman.Constants.Constanst;
import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.util.Duration;

import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.*;

public class Enemy3 extends Enemy {
    private double dx = -ENEMY_SPEED * 2.25;
    private double dy = 0;

    public Enemy3() {
        super("enemy3.png");
        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY3, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity brick) {
                enemy.getComponent(Enemy3.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY3, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity wall) {
                enemy.getComponent(Enemy3.class).turn();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY3, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity door) {
                enemy.getComponent(Enemy3.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY3, GameType.BOMB) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity door) {
                enemy.getComponent(Enemy3.class).turn();
            }
        });

        physics.addCollisionHandler(new FlameEnemy3Handler());
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
        return Math.random() > 0.5 ? ENEMY_SPEED * 2 : -ENEMY_SPEED * 2;
    }

    @Override
    public void die() {
        dx = 0;
        dy = 0;
        currentMoveDir = Constanst.MoveDirection.DIE;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

}

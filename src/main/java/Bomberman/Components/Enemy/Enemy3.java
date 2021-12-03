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
    public Enemy3() {
        super(-ENEMY_SPEED, 0, 2, "enemy3.png");
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

        super.onUpdate(tpf);
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

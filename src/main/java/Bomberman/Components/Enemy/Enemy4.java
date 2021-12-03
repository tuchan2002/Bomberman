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
    public Enemy4() {
        super(-ENEMY_SPEED, 0,2.25, "enemy4.png");
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

        super.onUpdate(tpf);
    }


    @Override
    public void die() {
        dx = 0;
        dy = 0;
        currentMoveDir = Constanst.MoveDirection.DIE;
    }

}

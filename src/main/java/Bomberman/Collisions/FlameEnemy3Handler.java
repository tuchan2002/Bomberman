package Bomberman.Collisions;

import Bomberman.Components.Enemy.Enemy1;
import Bomberman.Components.Enemy.Enemy3;
import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.util.Duration;


import static Bomberman.Components.Enemy.Enemy.ENEMY_SPEED;
import static Bomberman.DynamicEntityState.State.*;
import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class FlameEnemy3Handler extends CollisionHandler {
    public FlameEnemy3Handler() {
        super(GameType.FLAME, GameType.ENEMY3);
    }

    @Override
    protected void onCollisionBegin(Entity fire, Entity enemy) {
        if(enemy.getComponent(Enemy3.class).getState() != DIE) {
            enemy.getComponent(Enemy3.class).setStateDie();

            getGameTimer().runOnceAfter(() -> {
                onTransform(enemy);
                enemy.removeFromWorld();
            }, Duration.seconds(2.4));
        }
    }

    private void onTransform(Entity parent) {
        Entity child1 = spawn("enemy1", new SpawnData(parent.getX(), parent.getY()));
        Entity child2 = spawn("enemy1", new SpawnData(parent.getX(), parent.getY()));

        if (parent.getComponent(Enemy3.class).getDx() == 0) {
            child1.getComponent(Enemy1.class).setState(UP);
            child1.getComponent(Enemy1.class).setDxDy(0, -ENEMY_SPEED);
            child2.getComponent(Enemy1.class).setState(DOWN);
            child2.getComponent(Enemy1.class).setDxDy(0, ENEMY_SPEED);
        } else if (parent.getComponent(Enemy3.class).getDy() == 0) {
            child1.getComponent(Enemy1.class).setState(LEFT);
            child1.getComponent(Enemy1.class).setDxDy(-ENEMY_SPEED, 0);
            child2.getComponent(Enemy1.class).setState(RIGHT);
            child2.getComponent(Enemy1.class).setDxDy(ENEMY_SPEED, 0);
        }
    }
}

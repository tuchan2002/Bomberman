package Bomberman.Components.Enemy;

import Bomberman.Collisions.FlameEnemy3Handler;
import Bomberman.GameType;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Enemy3 extends Enemy {
    public Enemy3() {
        super(-ENEMY_SPEED, 0, 2,4, "enemy3.png");
        PhysicsWorld physics = getPhysicsWorld();

        onCollisionBegin(GameType.ENEMY3, GameType.BRICK, (enemy3, brick) -> {
            enemy3.getComponent(Enemy3.class).turn();
        });
        onCollisionBegin(GameType.ENEMY3, GameType.WALL, (enemy3, wall) -> {
            enemy3.getComponent(Enemy3.class).turn();
        });
        onCollisionBegin(GameType.ENEMY3, GameType.DOOR, (enemy3, door) -> {
            enemy3.getComponent(Enemy3.class).turn();
        });
        onCollisionBegin(GameType.ENEMY3, GameType.BOMB, (enemy3, bomb) -> {
            enemy3.getComponent(Enemy3.class).turn();
        });

        physics.addCollisionHandler(new FlameEnemy3Handler());
    }

}

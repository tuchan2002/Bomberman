package Bomberman.Components.Enemy;

import Bomberman.GameType;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;

public class Enemy1 extends Enemy {
    public Enemy1() {
        super(-ENEMY_SPEED, 0, 1, 3, "enemy1.png");

        onCollisionBegin(GameType.ENEMY1, GameType.BRICK, (enemy1, brick) -> {
            enemy1.getComponent(Enemy1.class).turn();
        });
        onCollisionBegin(GameType.ENEMY1, GameType.WALL, (enemy1, wall) -> {
            enemy1.getComponent(Enemy1.class).turn();
        });
        onCollisionBegin(GameType.ENEMY1, GameType.DOOR, (enemy1, door) -> {
            enemy1.getComponent(Enemy1.class).turn();
        });
        onCollisionBegin(GameType.ENEMY1, GameType.BOMB, (enemy1, bomb) -> {
            enemy1.getComponent(Enemy1.class).turn();
        });
        onCollisionBegin(GameType.ENEMY1, GameType.FLAME, (enemy1, flame) -> {
            enemy1.getComponent(Enemy1.class).setStateDie();
            getGameTimer().runOnceAfter(() -> {
                enemy1.removeFromWorld();
            }, Duration.seconds(2.4));
        });
    }

}

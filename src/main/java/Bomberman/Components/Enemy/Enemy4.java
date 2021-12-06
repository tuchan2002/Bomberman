package Bomberman.Components.Enemy;

import Bomberman.GameType;
import javafx.util.Duration;

import static Bomberman.GameType.*;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;

public class Enemy4 extends Enemy {
    public Enemy4() {
        super(-ENEMY_SPEED, 0, 2.25, 4.5, "enemy4.png");
        onCollisionBegin(GameType.ENEMY4, GameType.BRICK, (enemy4, brick) -> {
            enemy4.getComponent(Enemy4.class).turn();
        });
        onCollisionBegin(GameType.ENEMY4, GameType.WALL, (enemy4, wall) -> {
            enemy4.getComponent(Enemy4.class).turn();
        });
        onCollisionBegin(GameType.ENEMY4, GameType.DOOR, (enemy4, door) -> {
            enemy4.getComponent(Enemy4.class).turn();
        });
        onCollisionBegin(GameType.ENEMY4, GameType.BOMB, (enemy4, bomb) -> {
            enemy4.getComponent(Enemy4.class).turn();
        });
        onCollisionBegin(GameType.ENEMY4, GameType.FLAME, (enemy4, flame) -> {
            enemy4.getComponent(Enemy4.class).setStateDie();
            getGameTimer().runOnceAfter(() -> {
                enemy4.removeFromWorld();
                set("enemies", getGameWorld().getGroup(ENEMY1,
                        ENEMY2, ENEMY3, ENEMY4, ENEMY5).getSize());
            }, Duration.seconds(2.4));
        });

    }

    @Override
    public void turn() {
        speedFactor = Math.random() > 0.8 ? 1 : 2.25;
        super.turn();
    }
}

package Bomberman.Components.Enemy;

import Bomberman.Components.PlayerComponent;
import Bomberman.Constants.Constanst;
import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.util.Duration;

import static Bomberman.Constants.Constanst.ENEMY_SPEED;
import static Bomberman.Constants.Constanst.TILED_SIZE;
import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.*;
import static Bomberman.Constants.Constanst.*;

public class Enemy5 extends Enemy {
    private boolean check = true;

    public Enemy5() {
        super(-ENEMY_SPEED, 0, 1, "enemy5.png");
        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY5, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity brick) {
                if (speedFactor == 1) {
                    enemy.getComponent(Enemy5.class).turn();
                }
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY5, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity FRAME_SIZE) {
                enemy.getComponent(Enemy5.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY5, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity door) {
                enemy.getComponent(Enemy5.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY5, GameType.BOMB) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity door) {
                enemy.getComponent(Enemy5.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.FLAME, GameType.ENEMY5) {

            @Override
            protected void onCollisionBegin(Entity flame, Entity enemy) {
                enemy.getComponent(Enemy5.class).die();
                getGameTimer().runOnceAfter(() -> {
                    enemy.removeFromWorld();
                }, Duration.seconds(2.4));
            }
        });

    }

    @Override
    public void onUpdate(double tpf) {
        Entity player = getGameWorld().getSingleton(GameType.PLAYER);
        int cellX = (int) (player.getX() / TILED_SIZE);
        int cellY = (int) (player.getY() / TILED_SIZE);
        int enemyCellY = (int) (entity.getY() / TILED_SIZE);
        int enemyCellX = (int) (entity.getX() / TILED_SIZE);
        if (getEntity().distance(player) < TILED_SIZE * 4) {
            if (check == true && player.getComponent(PlayerComponent.class).getCurrentMoveDir() != MoveDirection.DIE) {
                speedFactor = 1.25;
                if (dx == 0) {
                    if (enemyCellY == cellY) {
                        if (player.getX() > entity.getX()) {
                            currentMoveDir = MoveDirection.RIGHT;
                            dx = ENEMY_SPEED;
                            dy = 0;
                        } else {
                            currentMoveDir = MoveDirection.LEFT;
                            dx = -ENEMY_SPEED;
                            dy = 0;
                        }
                    }
                } else {
                    if (enemyCellX == cellX) {
                        if (player.getY() > entity.getY()) {
                            currentMoveDir = MoveDirection.DOWN;
                            dx = 0;
                            dy = ENEMY_SPEED;
                        } else {
                            currentMoveDir = MoveDirection.UP;
                            dx = 0;
                            dy = -ENEMY_SPEED;
                        }
                    }
                }
            } else if (dx == 0 && ((int) entity.getY() % TILED_SIZE <= 5 && (int) entity.getY() % TILED_SIZE > 0)) {
                check = true;
            } else if (dy == 0 && ((int) entity.getX() % TILED_SIZE <= 5 && (int) entity.getY() % TILED_SIZE > 0)) {
                check = true;
            }
        } else {
            speedFactor = 1;
            check = true;
        }


        super.onUpdate(tpf);
    }

}

package Bomberman.Components.Enemy;

import Bomberman.Components.PlayerComponent;
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
    private boolean isCatching = true;

    public Enemy5() {
        super(-ENEMY_SPEED, 0, 1,4, "enemy5.png");
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
        super.onUpdate(tpf);

        Entity player = getGameWorld().getSingleton(GameType.PLAYER);

        if (currentMoveDir == MoveDirection.DIE
                || player
                .getComponent(PlayerComponent.class)
                .getCurrentMoveDir() == MoveDirection.DIE) {
            return;
        }

        int cellX = (int) (player.getX() / TILED_SIZE);
        int cellY = (int) (player.getY() / TILED_SIZE);
        int enemyCellY = (int) (entity.getY() / TILED_SIZE);
        int enemyCellX = (int) (entity.getX() / TILED_SIZE);
        if (getEntity().distance(player) < TILED_SIZE * 5) {
            if (isCatching == true) {
                if (dx == 0) {
                    if ((entity.getY() - player.getY()) * dy < 0) {
                        speedFactor = 1.5;
                    } else {
                        speedFactor = 1;
                    }

                    if (enemyCellY == cellY) {
                        if (player.getX() > entity.getX()) {
                            turnRight();
                        } else {
                            turnLeft();
                        }
                    }
                } else if (dy == 0) {
                    if ((entity.getX() - player.getX()) * dx < 0) {
                        speedFactor = 1.5;
                    } else {
                        speedFactor = 1;
                    }

                    if (enemyCellX == cellX) {
                        if (player.getY() > entity.getY()) {
                            turnDown();
                        } else {
                            turnUp();
                        }
                    }
                }
            } else if (dx == 0 && ((int) entity.getY() % TILED_SIZE <= 5 && (int) entity.getY() % TILED_SIZE > 0)) {
                isCatching = true;
            } else if (dy == 0 && ((int) entity.getX() % TILED_SIZE <= 5 && (int) entity.getY() % TILED_SIZE > 0)) {
                isCatching = true;
            }
        } else {
            speedFactor = 1;
            isCatching = true;
        }

    }

    @Override
    public void turn() {
        isCatching = false;
        super.turn();
    }
}

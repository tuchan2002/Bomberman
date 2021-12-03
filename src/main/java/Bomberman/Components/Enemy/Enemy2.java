package Bomberman.Components.Enemy;

import Bomberman.Components.PlayerComponent;
import Bomberman.GameType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.image;

public class Enemy2 extends Enemy {
    private boolean isCatching = true;

    public Enemy2() {
        super(-ENEMY_SPEED, 0, 1,3, "enemy2.png");
        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity brick) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity FRAME_SIZE) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity door) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.BOMB) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity door) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.FLAME, GameType.ENEMY2) {

            @Override
            protected void onCollisionBegin(Entity flame, Entity enemy2) {
                enemy2.getComponent(Enemy2.class).die();
                getGameTimer().runOnceAfter(() -> {
                    enemy2.removeFromWorld();
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
        if (getEntity().distance(player) < TILED_SIZE * 3) {
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

package Bomberman.Components.Enemy;

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

public class Enemy2 extends Component {
    private double dx = -ENEMY_SPEED;
    private double dy = 0;

    private MoveDirection currentMoveDir = MoveDirection.LEFT;
    private AnimatedTexture texture;
    private AnimationChannel animDie;
    private AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;

    public Enemy2() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity brick) {
                enemy2.getComponent(Enemy2.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY2, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy2, Entity wall) {
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
        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.ENEMY2) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity enemy2) {
                enemy2.getComponent(Enemy2.class).die();
                play("skeleton.wav");
                getGameTimer().runOnceAfter(() -> {
                    enemy2.removeFromWorld();
                }, Duration.seconds(1.5));
            }
        });

        animDie = new AnimationChannel(image("skeleton2Die.png"), 6, 64, 64, Duration.seconds(1.5), 0, 0 + 6 - 1);

        animWalkDown = new AnimationChannel(image("skeleton2.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 10, 9 * 10 + 9 - 1);
        animWalkRight = new AnimationChannel(image("skeleton2.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 11, 9 * 11 + 9 - 1);
        animWalkUp = new AnimationChannel(image("skeleton2.png"), 9, 64, 64, Duration.seconds(1), 9 * 8, 9 * 8 + 9 - 1);
        animWalkLeft = new AnimationChannel(image("skeleton2.png"), 9, 64, 64, Duration.seconds(1), 9 * 9, 9 * 9 + 9 - 1);

        texture = new AnimatedTexture(animWalkLeft);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);

        switch (currentMoveDir) {
            case UP:
                texture.loopNoOverride(animWalkUp);
                break;
            case RIGHT:
                texture.loopNoOverride(animWalkRight);
                break;
            case DOWN:
                texture.loopNoOverride(animWalkDown);
                break;
            case LEFT:
                texture.loopNoOverride(animWalkLeft);
                break;
            case DIE:
                texture.loopNoOverride(animDie);
                break;
        }
    }

    public void turn() {
        if (dx < 0) {
            entity.translateX(4);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = MoveDirection.DOWN;
            } else {
                currentMoveDir = MoveDirection.UP;
            }
        } else if (dx > 0) {
            entity.translateX(-4);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = MoveDirection.DOWN;
            } else {
                currentMoveDir = MoveDirection.UP;
            }
        } else if (dy < 0.0) {
            entity.translateY(4);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = MoveDirection.RIGHT;
            } else {
                currentMoveDir = MoveDirection.LEFT;
            }
        } else {
            entity.translateY(-4);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = MoveDirection.RIGHT;
            } else {
                currentMoveDir = MoveDirection.LEFT;
            }
        }

    }

    private double getRandomSpeed() {
        double x = Math.random() > 0.5 ? 1 : 1.8;
        double speed = Math.random() > 0.5 ? ENEMY_SPEED * x : -ENEMY_SPEED * x;

        return speed;
    }

    private void die() {
        dx = 0;
        dy = 0;
        currentMoveDir = MoveDirection.DIE;
    }
}

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

public class Enemy1 extends Component {
    private final int FRAME_SIZE = 48;
    private double dx = -ENEMY_SPEED;
    private double dy = 0;

    private MoveDirection currentMoveDir = MoveDirection.LEFT;
    private AnimatedTexture texture;
    private AnimationChannel animDie;
    private AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;

    public Enemy1() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity brick) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.WALL) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity wall) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity door) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.ENEMY1, GameType.BOMB) {
            @Override
            protected void onCollisionBegin(Entity enemy1, Entity door) {
                enemy1.getComponent(Enemy1.class).turn();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.FIRE, GameType.ENEMY1) {

            @Override
            protected void onCollisionBegin(Entity fire, Entity enemy1) {
                enemy1.getComponent(Enemy1.class).die();
                play("skeleton.wav");
                getGameTimer().runOnceAfter(() -> {
                    enemy1.removeFromWorld();
                }, Duration.seconds(1.5));
            }
        });
        animDie = new AnimationChannel(image("skeletonDie.png"), 6, FRAME_SIZE, FRAME_SIZE, Duration.seconds(1.5), 0, 0 + 6 - 1);

        animWalkDown = new AnimationChannel(image("skeleton.png"), 9, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.8), 9 * 10, 9 * 10 + 9 - 1);
        animWalkRight = new AnimationChannel(image("skeleton.png"), 9, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.8), 9 * 11, 9 * 11 + 9 - 1);
        animWalkUp = new AnimationChannel(image("skeleton.png"), 9, FRAME_SIZE, FRAME_SIZE, Duration.seconds(1), 9 * 8, 9 * 8 + 9 - 1);
        animWalkLeft = new AnimationChannel(image("skeleton.png"), 9, FRAME_SIZE, FRAME_SIZE, Duration.seconds(1), 9 * 9, 9 * 9 + 9 - 1);

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
            entity.translateX(2);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = MoveDirection.DOWN;
            } else {
                currentMoveDir = MoveDirection.UP;
            }
        } else if (dx > 0) {
            entity.translateX(-2);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = MoveDirection.DOWN;
            } else {
                currentMoveDir = MoveDirection.UP;
            }
        } else if (dy < 0.0) {
            entity.translateY(2);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = MoveDirection.RIGHT;
            } else {
                currentMoveDir = MoveDirection.LEFT;
            }
        } else {
            entity.translateY(-2);
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
        return Math.random() > 0.5 ? ENEMY_SPEED : -ENEMY_SPEED;
    }

    public void die() {
        dx = 0;
        dy = 0;
        currentMoveDir = MoveDirection.DIE;
    }


}

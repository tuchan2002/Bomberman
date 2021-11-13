package component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import component.BombComponent;
import javafx.util.Duration;

public class PlayerComponent extends Component {
    enum MoveDirection {
        UP, RIGHT, DOWN, LEFT, STOP
    }

    public static final int SPEED = 150;

    private PhysicsComponent physics;

    private MoveDirection currentMoveDir = MoveDirection.STOP;

    private int maxBombs = 3;
    private int bombsPlaced = 0;

    public void increaseMaxBombs() {
        maxBombs++;
    }

    private AnimatedTexture texture;
    private AnimationChannel animIdleDown, animIdleRight, animIdleUp, animIdleLeft;
    private AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;

    public PlayerComponent() {

        animIdleDown = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 10, 9 * 10);
        animIdleRight = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 11, 9 * 11);
        animIdleUp = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 8, 9 * 8);
        animIdleLeft = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 9, 9 * 9);

        animWalkDown = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 10, 9 * 10 + 9 - 1);
        animWalkRight = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 11, 9 * 11 + 9 - 1);
        animWalkUp = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 8, 9 * 8 + 9 - 1);
        animWalkLeft = new AnimationChannel(FXGL.image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 9, 9 * 9 + 9 - 1);

        texture = new AnimatedTexture(animIdleDown);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.getVelocityX() != 0) {

            physics.setVelocityX((int) physics.getVelocityX() * 0.9);

            if (FXGLMath.abs(physics.getVelocityX()) < 1) {
                physics.setVelocityX(0);
            }
        }

        if (physics.getVelocityY() != 0) {

            physics.setVelocityY((int) physics.getVelocityY() * 0.9);

            if (FXGLMath.abs(physics.getVelocityY()) < 1) {
                physics.setVelocityY(0);
            }
        }

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
            case STOP:
                if (texture.getAnimationChannel() == animWalkDown) {
                    texture.loopNoOverride(animIdleDown);
                } else if (texture.getAnimationChannel() == animWalkUp) {
                    texture.loopNoOverride(animIdleUp);
                } else if (texture.getAnimationChannel() == animWalkLeft) {
                    texture.loopNoOverride(animIdleLeft);
                } else if (texture.getAnimationChannel() == animWalkRight) {
                    texture.loopNoOverride(animIdleRight);
                }
                break;
        }
    }

    public void up() {
        currentMoveDir = MoveDirection.UP;
        physics.setVelocityY(-SPEED);
    }

    public void down() {
        currentMoveDir = MoveDirection.DOWN;
        physics.setVelocityY(SPEED);
    }

    public void left() {
        currentMoveDir = MoveDirection.LEFT;
        physics.setVelocityX(-SPEED);
    }

    public void right() {
        currentMoveDir = MoveDirection.RIGHT;
        physics.setVelocityX(SPEED);
    }

    public void stop() {
        currentMoveDir = MoveDirection.STOP;
    }

    public void placeBomb(int damageLevel) {
        if (bombsPlaced == maxBombs) {
            return;
        }
        bombsPlaced++;

        int bombLocationX = (int) (entity.getX() % 64 > 32
                ? entity.getX() + 64 - entity.getX() % 64 + 1
                : entity.getX() - entity.getX() % 64 + 1);
        int bombLocationY = (int) (entity.getY() % 64 > 32
                ? entity.getY() + 64 - entity.getY() % 64 + 1
                : entity.getY() - entity.getY() % 64 + 1);

        Entity bomb = FXGL.spawn("Bomb", new SpawnData(bombLocationX, bombLocationY));

        FXGL.getGameTimer().runOnceAfter(() -> {
            bomb.getComponent(BombComponent.class).explode(damageLevel);
            FXGL.play("slash.wav");
            bombsPlaced--;
        }, Duration.seconds(1.3));
    }
}

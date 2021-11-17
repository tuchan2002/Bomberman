package Bomberman.Components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;
import static Bomberman.Constants.Constanst.*;
public class PlayerComponent extends Component {
    private MoveDirection currentMoveDir = MoveDirection.STOP;
    private PhysicsComponent physics;

    private int maxBombs = 2;
    private int bombsPlaced = 0;

    public void increaseMaxBombs() {
        maxBombs++;
    }

    private AnimatedTexture texture;
    private AnimationChannel animIdleDown, animIdleRight, animIdleUp, animIdleLeft;
    private AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;
    private AnimationChannel animDie;

    public PlayerComponent() {
        animDie = new AnimationChannel(image("playerDie.png"), 6, 64, 64, Duration.seconds(1.8), 0, 0+6-1);

        animIdleDown = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(1), 9 * 10, 9 * 10);
        animIdleRight = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(1), 9 * 11, 9 * 11);
        animIdleUp = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(1), 9 * 8, 9 * 8);
        animIdleLeft = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(1), 9 * 9, 9 * 9);

        animWalkDown = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 10, 9 * 10 + 9 - 1);
        animWalkRight = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 11, 9 * 11 + 9 - 1);
        animWalkUp = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(1), 9 * 8, 9 * 8 + 9 - 1);
        animWalkLeft = new AnimationChannel(image("player.png"), 9, 64, 64, Duration.seconds(1), 9 * 9, 9 * 9 + 9 - 1);

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
            case DIE:
                texture.loopNoOverride(animDie);
                break;
        }
    }

    public void up() {
        if(currentMoveDir != MoveDirection.DIE) {
            currentMoveDir = MoveDirection.UP;
            physics.setVelocityY(-SPEED);
        }
    }

    public void down() {
        if(currentMoveDir != MoveDirection.DIE) {
            currentMoveDir = MoveDirection.DOWN;
            physics.setVelocityY(SPEED);
        }
    }

    public void left() {
        if(currentMoveDir != MoveDirection.DIE) {
            currentMoveDir = MoveDirection.LEFT;
            physics.setVelocityX(-SPEED);
        }
    }

    public void right() {
        if(currentMoveDir != MoveDirection.DIE) {
            currentMoveDir = MoveDirection.RIGHT;
            physics.setVelocityX(SPEED);
        }
    }

    public void stop() {
        if(currentMoveDir != MoveDirection.DIE) {
            currentMoveDir = MoveDirection.STOP;
        }
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

        Entity bomb = spawn("Bomb", new SpawnData(bombLocationX, bombLocationY));

        FXGL.getGameTimer().runOnceAfter(() -> {
            bomb.getComponent(BombComponent.class).explode(damageLevel);
            play("slash.wav");
            bombsPlaced--;
        }, Duration.seconds(2.1));
    }
    public void die() {
        currentMoveDir = MoveDirection.DIE;
    }


}

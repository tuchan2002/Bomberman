package Bomberman.Components.Enemy;

import Bomberman.Constants.Constanst;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static Bomberman.Constants.Constanst.*;

import static com.almasb.fxgl.dsl.FXGL.image;

public abstract class Enemy extends Component {
    private final int FRAME_SIZE = 48;
    protected double dx;
    protected double dy;
    protected double speedFactor;

    protected MoveDirection currentMoveDir = Constanst.MoveDirection.LEFT;
    protected AnimatedTexture texture;
    protected AnimationChannel animDie;
    protected AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;

    public Enemy(double dx, double dy, double speedFactor, String assetName) {
        this.dx = dx;
        this.dy = dy;
        this.speedFactor = speedFactor;

        animDie = new AnimationChannel(image(assetName), 6, FRAME_SIZE, FRAME_SIZE, Duration.seconds(2.4), 0, 5);

        animWalkDown = new AnimationChannel(image(assetName), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 3, 5);
        animWalkRight = new AnimationChannel(image(assetName), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 6, 8);
        animWalkUp = new AnimationChannel(image(assetName), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 6, 8);
        animWalkLeft = new AnimationChannel(image(assetName), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 3, 5);

        texture = new AnimatedTexture(animWalkLeft);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX((dx * speedFactor) * tpf);
        entity.translateY((dy * speedFactor) * tpf);

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
            entity.translateX(speedFactor * 2);
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = Constanst.MoveDirection.DOWN;
            } else {
                currentMoveDir = Constanst.MoveDirection.UP;
            }
        } else if (dx > 0) {
            entity.translateX(-(speedFactor * 2));
            dx = 0;
            dy = getRandomSpeed();
            if (dy > 0) {
                currentMoveDir = Constanst.MoveDirection.DOWN;
            } else {
                currentMoveDir = Constanst.MoveDirection.UP;
            }
        } else if (dy < 0.0) {
            entity.translateY(speedFactor * 2);
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = Constanst.MoveDirection.RIGHT;
            } else {
                currentMoveDir = Constanst.MoveDirection.LEFT;
            }
        } else {
            entity.translateY(-(speedFactor * 2));
            dy = 0;
            dx = getRandomSpeed();
            if (dx > 0) {
                currentMoveDir = Constanst.MoveDirection.RIGHT;
            } else {
                currentMoveDir = Constanst.MoveDirection.LEFT;
            }
        }

    }

    public double getRandomSpeed() {
        return Math.random() > 0.5 ? ENEMY_SPEED : -ENEMY_SPEED;
    }

    public void die() {
        dx = 0;
        dy = 0;
        currentMoveDir = MoveDirection.DIE;
    }

    public void setDxDy(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setCurrentMoveDir(MoveDirection currentMoveDir) {
        this.currentMoveDir = currentMoveDir;
    }

    public MoveDirection getCurrentMoveDir() {
        return currentMoveDir;
    }
}

package Bomberman.Components.Enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.image;

public class Enemy1 extends Component {
    private double dx = -ENEMY_SPEED;
    private double dy = 0;

    private MoveDirection currentMoveDir = MoveDirection.LEFT;
    private AnimatedTexture texture;
    private AnimationChannel animDie;
    private AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;

    public Enemy1() {
        animDie = new AnimationChannel(image("skeletonDie.png"), 6, 64, 64, Duration.seconds(1.5), 0, 0+6-1);

        animWalkDown = new AnimationChannel(image("skeleton.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 10, 9 * 10 + 9 - 1);
        animWalkRight = new AnimationChannel(image("skeleton.png"), 9, 64, 64, Duration.seconds(0.8), 9 * 11, 9 * 11 + 9 - 1);
        animWalkUp = new AnimationChannel(image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 8, 9 * 8 + 9 - 1);
        animWalkLeft = new AnimationChannel(image("skeleton.png"), 9, 64, 64, Duration.seconds(1), 9 * 9, 9 * 9 + 9 - 1);

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

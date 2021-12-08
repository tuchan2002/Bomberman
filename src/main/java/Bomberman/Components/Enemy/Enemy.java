package Bomberman.Components.Enemy;

import Bomberman.DynamicEntityState.State;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static Bomberman.DynamicEntityState.State.*;
import static com.almasb.fxgl.dsl.FXGL.image;

public abstract class Enemy extends Component {
    public static final int ENEMY_SPEED = 80;
    private final int FRAME_SIZE = 48;
    protected double dx;
    protected double dy;
    protected double speedFactor;
    private double reactionForce;

    protected State state;
    protected AnimatedTexture texture;
    protected AnimationChannel animDie;
    protected AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;

    public Enemy(double dx, double dy, double speedFactor, double reactionForce, String assetName) {
        this.dx = dx;
        this.dy = dy;
        this.speedFactor = speedFactor;
        this.reactionForce = reactionForce;
        state = LEFT;
        
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

        switch (state) {
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
        double randomDirection = Math.random();
        if (dx < 0) {
            entity.translateX(reactionForce);
            if (randomDirection > 0.5) {
                turnDown();
            } else {
                turnUp();
            }
        } else if (dx > 0) {
            entity.translateX(-reactionForce);
            if (randomDirection > 0.5) {
                turnDown();
            } else {
                turnUp();
            }
        } else if (dy < 0.0) {
            entity.translateY(reactionForce);
            if (randomDirection > 0.5) {
                turnRight();
            } else {
                turnLeft();
            }
        } else {
            entity.translateY(-reactionForce);
            if (randomDirection > 0.5) {
                turnRight();
            } else {
                turnLeft();
            }
        }

    }

    public void turnLeft() {
        state = LEFT;
        dx = -ENEMY_SPEED;
        dy = 0;
    }

    public void turnRight() {
        state = RIGHT;
        dx = ENEMY_SPEED;
        dy = 0;
    }

    public void turnUp() {
        state = UP;
        dx = 0;
        dy = -ENEMY_SPEED;
    }

    public void turnDown() {
        state = DOWN;
        dx = 0;
        dy = ENEMY_SPEED;
    }

    public void setStateDie() {
        dx = 0;
        dy = 0;
        state = DIE;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

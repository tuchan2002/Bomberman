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

    protected MoveDirection currentMoveDir = Constanst.MoveDirection.LEFT;
    protected AnimatedTexture texture;
    protected AnimationChannel animDie;
    protected AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;

    public Enemy(String assetName) {
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

    public MoveDirection getCurrentMoveDir() {
        return currentMoveDir;
    }

    public abstract void die();
}

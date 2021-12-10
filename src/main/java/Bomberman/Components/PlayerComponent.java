package Bomberman.Components;

import Bomberman.DynamicEntityState.State;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static Bomberman.Constants.Constant.TILED_SIZE;
import static Bomberman.GameType.*;
import static Bomberman.DynamicEntityState.State.*;
import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {
    private final int BONUS_SPEED = 100;
    private final int FRAME_SIZE = 45;

    public enum PlayerSkin {
        NORMAL, GOLD
    }

    private State state;
    private PhysicsComponent physics;

    private boolean bombInvalidation;
    private int bombCounter;
    private PlayerSkin playerSkin;

    private AnimatedTexture texture;
    private AnimationChannel animIdleDown, animIdleRight, animIdleUp, animIdleLeft;
    private AnimationChannel animWalkDown, animWalkRight, animWalkUp, animWalkLeft;
    private AnimationChannel animDie;

    public PlayerComponent() {
        state = STOP;
        bombInvalidation = false;
        bombCounter = 0;

        onCollisionBegin(PLAYER, POWERUP_FLAMES, (player, powerup) -> {
            powerup.removeFromWorld();
            play("powerup.wav");
            inc("flame", 1);
        });
        onCollisionBegin(PLAYER, POWERUP_BOMBS, (player, powerup) -> {
            powerup.removeFromWorld();
            play("powerup.wav");
            inc("bomb", 1);
        });
        onCollisionBegin(PLAYER, POWERUP_SPEED, (player, powerup) -> {
            powerup.removeFromWorld();
            handlePowerUpSpeed();
        });
        onCollisionBegin(PLAYER, POWERUP_FLAMEPASS, (player, powerup) -> {
            powerup.removeFromWorld();
            play("powerup.wav");
            getGameWorld().getSingleton(PLAYER)
                    .getComponent(PlayerComponent.class)
                    .setSkin(PlayerSkin.GOLD);
        });
        onCollisionBegin(PLAYER, POWERUP_LIFE, (player, powerup) -> {
            powerup.removeFromWorld();
            play("powerup.wav");
            inc("life", 1);
        });

        setSkin(PlayerSkin.NORMAL);
        texture = new AnimatedTexture(animIdleDown);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    private void setSkin(PlayerSkin skin) {
        playerSkin = skin;
        if (playerSkin == PlayerSkin.NORMAL) {
            animDie = new AnimationChannel(image("player_die.png"), 5, FRAME_SIZE, FRAME_SIZE, Duration.seconds(3.5), 0, 4);

            animIdleDown = new AnimationChannel(image("player_down.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);
            animIdleRight = new AnimationChannel(image("player_right.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);
            animIdleUp = new AnimationChannel(image("player_up.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);
            animIdleLeft = new AnimationChannel(image("player_left.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);

            animWalkDown = new AnimationChannel(image("player_down.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
            animWalkRight = new AnimationChannel(image("player_right.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
            animWalkUp = new AnimationChannel(image("player_up.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
            animWalkLeft = new AnimationChannel(image("player_left.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
        } else if (playerSkin == PlayerSkin.GOLD) {
            animDie = new AnimationChannel(image("player_die.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(3.5), 0, 4);

            animIdleDown = new AnimationChannel(image("gold_player_down.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);
            animIdleRight = new AnimationChannel(image("gold_player_right.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);
            animIdleUp = new AnimationChannel(image("gold_player_up.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);
            animIdleLeft = new AnimationChannel(image("gold_player_left.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 0);

            animWalkDown = new AnimationChannel(image("gold_player_down.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
            animWalkRight = new AnimationChannel(image("gold_player_right.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
            animWalkUp = new AnimationChannel(image("gold_player_up.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
            animWalkLeft = new AnimationChannel(image("gold_player_left.png"), 3, FRAME_SIZE, FRAME_SIZE, Duration.seconds(0.5), 0, 2);
        }
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
        if (state != DIE) {
            state = UP;
            physics.setVelocityY(-geti("speed"));
        }
    }

    public void down() {
        if (state != DIE) {
            state = DOWN;
            physics.setVelocityY(geti("speed"));
        }
    }

    public void left() {
        if (state != DIE) {
            state = LEFT;
            physics.setVelocityX(-geti("speed"));
        }
    }

    public void right() {
        if (state != DIE) {
            state = RIGHT;
            physics.setVelocityX(geti("speed"));
        }
    }

    public void stop() {
        if (state != DIE) {
            state = STOP;
        }
    }

    public void placeBomb(int flames) {
        if (state != DIE) {
            if (bombCounter == geti("bomb")) {
                return;
            }
            bombCounter++;

            int bombLocationX = (int) (entity.getX() % TILED_SIZE > TILED_SIZE / 2
                    ? entity.getX() + TILED_SIZE - entity.getX() % TILED_SIZE + 1
                    : entity.getX() - entity.getX() % TILED_SIZE + 1);
            int bombLocationY = (int) (entity.getY() % TILED_SIZE > TILED_SIZE / 2
                    ? entity.getY() + TILED_SIZE - entity.getY() % TILED_SIZE + 1
                    : entity.getY() - entity.getY() % TILED_SIZE + 1);

            Entity bomb = spawn("bomb", new SpawnData(bombLocationX, bombLocationY));
            play("place_bomb.wav");
            getGameTimer().runOnceAfter(() -> {
                if (!bombInvalidation) {
                    bomb.getComponent(BombComponent.class).explode(flames);
                    play("explosion.wav");
                } else {
                    bomb.removeFromWorld();
                }
                bombCounter--;
            }, Duration.seconds(2.1));
        }
    }

    public void handlePowerUpSpeed() {
        play("powerup.wav");
        inc("speed", BONUS_SPEED);
        getGameTimer().runOnceAfter(() -> {
            inc("speed", -BONUS_SPEED);
        }, Duration.seconds(6));
    }

    public PlayerSkin getPlayerSkin() {
        return playerSkin;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setBombInvalidation(boolean bombInvalidation) {
        this.bombInvalidation = bombInvalidation;
    }
}

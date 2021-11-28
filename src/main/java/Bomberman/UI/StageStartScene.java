package Bomberman.UI;

import Bomberman.GameApp;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import javafx.geometry.Point2D;
import javafx.scene.effect.Bloom;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.*;

public class StageStartScene extends SubScene {
    public StageStartScene() {
        play("stage_start.wav");

        var background = new Rectangle(SCENE_WIDTH, SCENE_HEIGHT, Color.color(0, 0, 0, 1));

        var title = getUIFactoryService().newText("Stage " + geti("level") , Color.WHITE, 40);
        title.setStroke(Color.WHITESMOKE);
        title.setStrokeWidth(1.5);
        title.setEffect(new Bloom(0.6));
        title.setX(SCENE_WIDTH / 3);
        title.setY(SCENE_HEIGHT / 2);
        getContentRoot().getChildren().addAll(background, title);

        animationBuilder()
                .onFinished(() -> popSubScene())
                .duration(Duration.seconds(4))
                .fade(getContentRoot())
                .from(1)
                .to(1)
                .buildAndPlay(this);
    }

    public void popSubScene() {
        GameApp.sound_enabled = !GameApp.sound_enabled;
        getSettings().setGlobalMusicVolume(GameApp.sound_enabled ? 0.3 : 0.0);
        getSceneService().popSubScene();
    }

}

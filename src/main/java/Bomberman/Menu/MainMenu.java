package Bomberman.Menu;

import Bomberman.UI.StageStartScene;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.input.view.KeyView;
import javafx.geometry.Pos;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static Bomberman.Sounds.SoundEffect.setSoundSwitch;
import static Bomberman.Sounds.SoundEffect.turnOffMusic;
import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;

public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);
        ImageView background = new ImageView();
        background.setImage(new Image("assets/textures/background_demo.png"));

        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.WHITE, 50);
        title.setStroke(Color.WHITESMOKE);
        title.setStrokeWidth(1.5);
        title.setEffect(new Bloom(0.6));
        centerTextBind(title, getAppWidth() / 2.0, 250);

        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 20);
        centerTextBind(version, getAppWidth() / 2.0, 280);

        var menuBox = new VBox(
                new MenuButton("New Game", () -> newGame()),
                new MenuButton("Control", () -> instruct()),
                new MenuButton("Sound", () -> setSoundSwitch()),
                new MenuButton("Exit", () -> fireExit())
        );

        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(getAppWidth() / 2.0 - 100);
        menuBox.setTranslateY(getAppHeight() / 2.0 + 60);
        menuBox.setSpacing(20);

        getContentRoot().getChildren().addAll(background, title, version, menuBox);
    }

    private void instruct() {
        GridPane pane = new GridPane();

        pane.addRow(0, getUIFactoryService().newText(" Movement      "),
                new HBox(new KeyView(W), new KeyView(S), new KeyView(A), new KeyView(D)));
        pane.addRow(1, getUIFactoryService().newText(" Placed Bomb      "),
                new KeyView(SPACE));

        getDialogService().showBox("How to Play", pane, getUIFactoryService().newButton("OK"));
    }


    public void newGame() {
        fireNewGame();
        getGameTimer().runOnceAfter(() -> {
            turnOffMusic();
            getSceneService().pushSubScene(new StageStartScene());
        }, Duration.millis(10));
    }
}

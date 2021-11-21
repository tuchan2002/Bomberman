package Bomberman.Menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.input.view.KeyView;
import javafx.geometry.Pos;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static Bomberman.Menu.BombermanGameMenu.setSoundEnabled;
import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;

public class BombermanMenu extends FXGLMenu {
    public BombermanMenu() {
        super(MenuType.MAIN_MENU);

        // UI background
        ImageView iv1 = new ImageView();
        iv1.setImage(new Image("assets/textures/background_demo.png"));

        // UI game title
        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.WHITE, 50);
        title.setStroke(Color.WHITESMOKE);
        title.setStrokeWidth(1.5);
        title.setEffect(new Bloom(0.6));
        centerTextBind(title, getAppWidth() / 2.0, 250);

        // UI game version
        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 20);
        centerTextBind(version, getAppWidth() / 2.0, 280);

        // UI Button
        var menuBox = new VBox(
                new MenuButton("New Game", () -> fireNewGame()),
                new MenuButton("Control", () -> instructions()),
                new MenuButton("Sounds", () -> setSoundEnabled()),
                new MenuButton("Exit", () -> fireExit())
        );

        // set pos menu button
        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(getAppWidth() / 2.0 - 100);
        menuBox.setTranslateY(getAppHeight() / 2.0 + 60);
        menuBox.setSpacing(20);

        getContentRoot().getChildren().addAll(iv1, title, version, menuBox);
    }

    private void instructions() {
        GridPane pane = new GridPane();
        pane.setHgap(25);
        pane.setVgap(10);
        pane.addRow(0, getUIFactoryService().newText("Movement      "),
                new HBox(new KeyView(W), new KeyView(S), new KeyView(A), new KeyView(D)));
        pane.addRow(1, getUIFactoryService().newText("Placed Bomb      "),
                new KeyView(SPACE));

        getDialogService().showBox("How to Play", pane, getUIFactoryService().newButton("OK"));
    }
}

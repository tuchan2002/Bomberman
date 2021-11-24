package Bomberman.Menu;

import Bomberman.GameApp;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static Bomberman.Constants.Constanst.*;
import static Bomberman.Menu.BombermanMenu.setSoundEnabled;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.centerTextBind;

public class BombermanGameMenu extends FXGLMenu {
    public BombermanGameMenu() {
        super(MenuType.GAME_MENU);
        Shape shape = new Rectangle(SCENE_WIDTH, SCENE_HEIGHT, Color.GREY);
        shape.setOpacity(0.5);

        // UI background
        ImageView iv1 = new ImageView();
        iv1.setImage(new Image("assets/textures/background_demo_1.png"));
        iv1.setX(200);
        iv1.setY(120);
        iv1.setEffect(new DropShadow(5, 3.5, 3.5, Color.WHITE));
        iv1.setEffect(new Lighting());

        // UI game title
        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.WHITE, 30);
        title.setStroke(Color.WHITESMOKE);
        title.setStrokeWidth(1.5);
        title.setEffect(new Bloom(0.6));
        centerTextBind(title, getAppWidth() / 2.0, 180);


        // UI game version
        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 20);
        centerTextBind(version, getAppWidth() / 2.0, 230);

        // UI Button
        var menuBox = new VBox(
                new MenuButton("Resume", () -> fireResume()),
                new MenuButton("Menu", () -> fireExitToMainMenu()),
                new MenuButton("Sounds", () -> setSoundEnabled()),
                new MenuButton("Exit", () -> fireExit())
        );

        // set pos menu button
        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(getAppWidth() / 2.0 - 70);
        menuBox.setTranslateY(getAppHeight() / 2.0);
        menuBox.setSpacing(20);

        getContentRoot().getChildren().addAll(shape, iv1, title, version, menuBox);
    }



}

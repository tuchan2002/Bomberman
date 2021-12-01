package Bomberman.Menu;

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
import static Bomberman.Sounds.SoundEffect.setSoundSwitch;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.centerTextBind;

public class GameMenu extends FXGLMenu {
    public GameMenu() {
        super(MenuType.GAME_MENU);
        Shape shape = new Rectangle(SCENE_WIDTH, SCENE_HEIGHT, Color.GREY);
        shape.setOpacity(0.5);

        ImageView background = new ImageView();
        background.setImage(new Image("assets/textures/background_demo_1.png"));
        background.setX(200);
        background.setY(120);
        background.setEffect(new DropShadow(5, 3.5, 3.5, Color.WHITE));
        background.setEffect(new Lighting());

        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.WHITE, 30);
        title.setStroke(Color.WHITESMOKE);
        title.setStrokeWidth(1.5);
        title.setEffect(new Bloom(0.6));
        centerTextBind(title, getAppWidth() / 2.0, 180);

        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 20);
        centerTextBind(version, getAppWidth() / 2.0, 230);

        var menuBox = new VBox(
                new MenuButton("Resume", () -> fireResume()),
                new MenuButton("Menu", () -> fireExitToMainMenu()),
                new MenuButton("Sound", () -> setSoundSwitch()),
                new MenuButton("Exit", () -> fireExit())
        );

        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(getAppWidth() / 2.0 - 70);
        menuBox.setTranslateY(getAppHeight() / 2.0);
        menuBox.setSpacing(20);

        getContentRoot().getChildren().addAll(shape, background, title, version, menuBox);
    }

}

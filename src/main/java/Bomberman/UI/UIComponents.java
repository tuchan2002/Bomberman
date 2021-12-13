package Bomberman.UI;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static Bomberman.Constants.Constant.UI_FONT_SIZE;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.addUINode;

public class UIComponents {
    public static void addILabelUI(String varName, String title, double x, double y) {
        Label text = new Label();
        text.setTextFill(Color.BLACK);
        text.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        text.textProperty().bind(getip(varName).asString(title));
        addUINode(text, x, y);
    }

    public static void addDLabelUI(String varName, String title, double x, double y) {
        Label text = new Label();
        text.setTextFill(Color.BLACK);
        text.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        text.textProperty().bind(getdp(varName).asString(title));
        addUINode(text, x, y);
    }
}

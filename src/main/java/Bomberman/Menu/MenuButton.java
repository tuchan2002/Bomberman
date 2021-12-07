package Bomberman.Menu;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public class MenuButton extends Parent {
    MenuButton(String name, double fontSize, Runnable action) {
        var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, fontSize);
        text.setStrokeWidth(1.5);
        text.strokeProperty().bind(text.fillProperty());

        text.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.rgb(248, 185, 54))
                        .otherwise(Color.WHITE)
        );

        setOnMouseClicked(e -> action.run());

        setPickOnBounds(true);
        getChildren().add(text);
    }
}

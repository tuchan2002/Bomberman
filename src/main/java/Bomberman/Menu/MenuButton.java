package Bomberman.Menu;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public class MenuButton extends Parent {
    MenuButton(String name, Runnable action) {
        var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 20);
        text.setStrokeWidth(1.5);
        text.strokeProperty().bind(text.fillProperty());

        text.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.BLUE)
                        .otherwise(Color.WHITE)
        );

        setOnMouseClicked(e -> action.run());

        setPickOnBounds(true);

        getChildren().add(text);
    }
}

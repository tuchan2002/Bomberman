package component;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import javafx.util.Duration;

import java.util.ArrayList;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BombComponent extends Component {
    public static final int FIRE_SIZE = 60;
    private ArrayList<Entity> listFire = new ArrayList<Entity>();

    public BombComponent() {
    }

    public void explode(int damageLevel) {
        listFire.add(spawn("Fire", new SpawnData(entity.getX(), entity.getY())));
        for (int i = 1; i <= damageLevel; i++) {
            listFire.add(spawn("Fire", new SpawnData(entity.getX() + FIRE_SIZE * i, entity.getY())));
            listFire.add(spawn("Fire", new SpawnData(entity.getX() - FIRE_SIZE * i, entity.getY())));
            listFire.add(spawn("Fire", new SpawnData(entity.getX(), entity.getY() + FIRE_SIZE * i)));
            listFire.add(spawn("Fire", new SpawnData(entity.getX(), entity.getY() - FIRE_SIZE * i)));
        }


        getGameTimer().runOnceAfter(() -> {
            for (int i = 0; i < listFire.size(); i++) {
                listFire.get(i).removeFromWorld();
            }
        }, Duration.seconds(0.4));


        entity.removeFromWorld();
    }

}

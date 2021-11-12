import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class GameFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.PLAYER)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .buildAndAttach();
    }
    @Spawns("Bomb")
    public Entity newBomb(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.BOMB)
                .viewWithBBox("bomb.png")
                .with(new BombComponent(15))
                .atAnchored(new Point2D(13, 11), new Point2D(data.getX() + 64 / 2, data.getY() + 64 / 2))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("Fire")
    public Entity newFire(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.FIRE)
                .viewWithBBox("fire.png")
                .with(new FireComponent())
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX() , data.getY() ))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("Ghost")
    public Entity newGhost(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.GHOST)
                .viewWithBBox("ghost.png")
                .with(new GhostComponent())
                .with(new CollidableComponent(true))
                .build();
    }

}

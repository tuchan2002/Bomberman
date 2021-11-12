import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class GameFactory implements EntityFactory {
    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.BRICK)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("wood")
    public Entity newWood(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.WOOD)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.PLAYER)
                .viewWithBBox(new Rectangle(63, 63, Color.TRANSPARENT))
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
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX() , data.getY() ))
                .with(new CollidableComponent(true))
                .zIndex(-1)
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

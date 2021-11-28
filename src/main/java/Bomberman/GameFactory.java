package Bomberman;

import Bomberman.Components.Enemy.Enemy1;
import Bomberman.Components.Enemy.Enemy2;
import Bomberman.Components.Enemy.Enemy3;
import Bomberman.Components.Enemy.Enemy4;
import Bomberman.Components.FlameComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import Bomberman.Components.BombComponent;
import Bomberman.Components.PlayerComponent;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.*;

public class GameFactory implements EntityFactory {
    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .view(new Rectangle(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, Color.rgb(0, 125, 0)))
                .zIndex(-100)
                .with(new IrremovableComponent())
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.WALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.BRICK)
                .view("brick.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick_break")
    public Entity newBrickBreak(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.BRICK_BREAK)
                .with(new Bomberman.Components.BrickBreakComponent())
                .viewWithBBox(new Rectangle(TILED_SIZE, TILED_SIZE, Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .zIndex(1)
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);

        return entityBuilder(data)
                .type(GameType.PLAYER)
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.circle(20)))
                .with(physics)
                .with(new PlayerComponent())
                .with(new CollidableComponent(true))
                .zIndex(5)
                .build();
    }

    @Spawns("enemy1")
    public Entity newEnemy1(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.ENEMY1)
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(38, 38)))
                .with(new Enemy1())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy2")
    public Entity newEnemy2(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.ENEMY2)
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(38, 38)))
                .with(new Enemy2())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy3")
    public Entity newEnemy3(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.ENEMY3)
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(38, 38)))
                .with(new Enemy3())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy4")
    public Entity newEnemy4(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.ENEMY4)
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(38, 38)))
                .with(new Enemy4())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("bomb")
    public Entity newBomb(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.BOMB)
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.circle(22)))
                .with(new BombComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("virtual_bomb")
    public Entity newVirtualBomb(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.VIRTUAL_BOMB)
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(48, 48)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("flame")
    public Entity newFlame(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.FLAME)
                .viewWithBBox(new Rectangle(TILED_SIZE - 3, TILED_SIZE - 3, Color.TRANSPARENT))
                .with(new FlameComponent())
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_flames")
    public Entity newItem(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.POWERUP_FLAMES)
                .view("powerup_flames.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_bombs")
    public Entity newItem2(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.POWERUP_BOMBS)
                .view("powerup_bombs.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_speed")
    public Entity newItem3(SpawnData data) {
        return entityBuilder(data)
                .type(GameType.POWERUP_SPEED)
                .view("powerup_speed.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_flamepass")
    public Entity newFlamePassItem(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.POWERUP_FLAMEPASS)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .view("powerup_flamepass.png")
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }


}

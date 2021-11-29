package Bomberman.Constants;

public class Constanst {
    public static final int SCENE_WIDTH = 1280;
    public static final int SCENE_HEIGHT = 720;
    public static final int GAME_WORLD_WIDTH = 1488;
    public static final int GAME_WORLD_HEIGHT = 720;
    public static final String GAME_TITLE = "BOMBERMAN";
    public static final String GAME_VERSION = "1.0";
    public static final Double UI_FONT_SIZE = 33.0;
    public static final int MAX_LEVEL = 5;
    public static final int STARTING_LEVEL = 0;

    public static final int SPEED = 150;
    public static final int ENEMY_SPEED = 80;
    public static final int TILED_SIZE = 48;
    public static final int INC_SPEED = 100;
    public static final double TIME_LEVEL = 220.0;


    public enum MoveDirection {
        UP, RIGHT, DOWN, LEFT, STOP, DIE
    }

    public enum PlayerSkin {
        NORMAL, FLAME_PASS
    }

}

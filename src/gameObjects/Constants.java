package gameObjects;

public class Constants {
    // Window size
    public static final int WIDTH = 1000, HEIGHT = 600;  
    public static final long GAME_OVER_TIME = 2000;
    // Player properties
    public static final int FIRE_RATE = 200;
    public static final double DELTA_ANGLE = 0.1;
    public static final double ACC = 0.2;
    public static final double PLAYER_MAX_VEL = 4.0;

    public static final long SPAWNING_TIME = 3000;
    public static final long FLICKER_TIME = 200;

    // Laser properties
    public static final double LASER_MAX_VEL = 10.0;

    // Meteor properties
    public static final double METEOR_MAX_VEL = 1.0;
    public static final int METEOR_SCORE = 10;

    // Radiues
    public static final int NODE_RADIUS = 150;
    public static final double ALIEN_MASS = 60;
    public static final int ALIEN_FIRE_RATE = 3000;
    public static final double ALIEN_ANGLE_RANGE = Math.PI/4;
    public static final int ALIEN_SCORE = 50;

    public static final String PLAY = "PLAY";
	
	public static final String EXIT = "EXIT";


    public static final long ALIEN_SPAWN_RATE = 5000;

    public static final int LOADING_BAR_WIDTH = 500;
	public static final int LOADING_BAR_HEIGHT = 50;
}

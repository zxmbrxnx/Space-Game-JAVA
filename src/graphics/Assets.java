package graphics;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

public class Assets {
    
    public static BufferedImage player;
    public static BufferedImage playerLive;
    public static BufferedImage laser;
    public static BufferedImage[] laserCharged = new BufferedImage[2];
    public static BufferedImage[] laserChargedSingle = new BufferedImage[2];
    
    // Enemies
    public static BufferedImage[] alienAnimation = new BufferedImage[6];

    // Sounds
    public static Clip backgroundMusic, explosion, playerLoose, playerShoot, alienShoot, gameOverSound;

    // Meteor images
	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] smalls = new BufferedImage[2];
	public static BufferedImage[] tinies = new BufferedImage[2];
    // Numbers
    public static BufferedImage[] numbers = new BufferedImage[10];
    public static Font fontBig;
    public static Font fontMed;
    // Efects
    public static BufferedImage[] exp = new BufferedImage[6];
    public static BufferedImage[] estela = new BufferedImage[2];

    // Player images
    public static String _playerImg0 = "/ships/player.png";
    public static String _playerImg1 = "/ships/player_l.png";
    public static String _playerImg2 = "/ships/player_r.png";

    // Laser
    public static String _laserImg0 = "/lasers/laser.png";

    public static void init() {
        player = Loader.imageLoader(_playerImg0);
        laser = Loader.imageLoader(_laserImg0);
        playerLive = Loader.imageLoader("/ships/player_life.png");

        fontBig = Loader.loadFont("/fonts/PressStart2P.ttf", 42);
        fontMed = Loader.loadFont("/fonts/PressStart2P.ttf", 15);

        for(int i = 0; i < bigs.length; i++){
			bigs[i] = Loader.imageLoader("/meteors/big"+(i+1)+".png");
        }

		for(int i = 0; i < meds.length; i++)
			meds[i] = Loader.imageLoader("/meteors/med"+(i+1)+".png");
		
		for(int i = 0; i < smalls.length; i++)
			smalls[i] = Loader.imageLoader("/meteors/small"+(i+1)+".png");
		
		for(int i = 0; i < tinies.length; i++)
			tinies[i] = Loader.imageLoader("/meteors/tiny"+(i+1)+".png");

        for(int i = 0; i < exp.length; i++)
            exp[i] = Loader.imageLoader("/effects/efect_exp"+(i+1)+".png");

        for(int i = 0; i < estela.length; i++)
            estela[i] = Loader.imageLoader("/effects/estela_"+(i+1)+".png");

        for(int i = 0; i < numbers.length; i++)
            numbers[i] = Loader.imageLoader("/fonts/"+i+".png");

        for(int i = 0; i < laserCharged.length; i++){
            laserCharged[i] = Loader.imageLoader("/lasers/laser_charged"+(i+1)+".png");
        }
        for(int i = 0; i < laserChargedSingle.length; i++){
            laserChargedSingle[i] = Loader.imageLoader("/lasers/laser_charged_single"+(i+1)+".png");
        }
        for(int i = 0; i < alienAnimation.length; i++){
            alienAnimation[i] = Loader.imageLoader("/alien/alien"+(i+1)+".png");
        }

        backgroundMusic = Loader.loadSound("/sounds/backgroundMusic.wav");
		explosion = Loader.loadSound("/sounds/explosion.wav");
		playerLoose = Loader.loadSound("/sounds/Hit-1.wav");
		playerShoot = Loader.loadSound("/sounds/ufoShoot.wav");
		alienShoot = Loader.loadSound("/sounds/Fire-1.wav");
		gameOverSound = Loader.loadSound("/sounds/Game-Over.wav");
    }   
}

package graphics;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import gameObjects.Chronometer;

public class Assets {

    public static boolean loaded = false;    
    public static float count = 0;    
    public static float MAX_COUNT = 430;
    
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
    public static Font fontSmall;

    // Efects
    public static BufferedImage[] exp = new BufferedImage[6];
    public static BufferedImage[] estela = new BufferedImage[2];

    // Player images
    public static String _playerImg0 = "/ships/player.png";
    public static String _playerImg1 = "/ships/player_l.png";
    public static String _playerImg2 = "/ships/player_r.png";

    // Laser
    public static String _laserImg0 = "/lasers/laser.png";

    // ui
	public static BufferedImage blueBtn;
	public static BufferedImage greyBtn;

    public static void init() {
        player = loadImage(_playerImg0);
        laser = loadImage(_laserImg0);
        playerLive = loadImage("/ships/player_life.png");

        fontBig = loadFont("/fonts/PressStart2P.ttf", 42);
        fontSmall = loadFont("/fonts/PressStart2P.ttf", 20);        
        fontMed = loadFont("/fonts/PressStart2P.ttf", 15);


        for(int i = 0; i < bigs.length; i++){
			bigs[i] = loadImage("/meteors/big"+(i+1)+".png");
        }

		for(int i = 0; i < meds.length; i++)
			meds[i] = loadImage("/meteors/med"+(i+1)+".png");
		
		for(int i = 0; i < smalls.length; i++)
			smalls[i] = loadImage("/meteors/small"+(i+1)+".png");
		
		for(int i = 0; i < tinies.length; i++)
			tinies[i] = loadImage("/meteors/tiny"+(i+1)+".png");

        for(int i = 0; i < exp.length; i++)
            exp[i] = loadImage("/effects/efect_exp"+(i+1)+".png");

        for(int i = 0; i < estela.length; i++)
            estela[i] = loadImage("/effects/estela_"+(i+1)+".png");

        for(int i = 0; i < numbers.length; i++)
            numbers[i] = loadImage("/fonts/"+i+".png");

        for(int i = 0; i < laserCharged.length; i++){
            laserCharged[i] = loadImage("/lasers/laser_charged"+(i+1)+".png");
        }
        for(int i = 0; i < laserChargedSingle.length; i++){
            laserChargedSingle[i] = loadImage("/lasers/laser_charged_single"+(i+1)+".png");
        }
        for(int i = 0; i < alienAnimation.length; i++){
            alienAnimation[i] = loadImage("/alien/alien"+(i+1)+".png");
        }

        backgroundMusic = loadSound("/sounds/backgroundMusic.wav");
		explosion = loadSound("/sounds/explosion.wav");
		playerLoose = loadSound("/sounds/Hit-1.wav");
		playerShoot = loadSound("/sounds/ufoShoot.wav");
		alienShoot = loadSound("/sounds/Fire-1.wav");
		gameOverSound = loadSound("/sounds/Game-Over.wav");

        //Ui
        greyBtn = loadImage("/ui/grey_button.png");
		blueBtn = loadImage("/ui/blue_button.png");

        //========================================================================

        loaded = true;
    } 
    
    public static BufferedImage loadImage(String path){
        count++;
        return Loader.imageLoader(path);
    }

    public static Font loadFont(String path, int size){
        count++;
        return Loader.loadFont(path, size);
    }

    public static Clip loadSound(String path){
        count++;
        return Loader.loadSound(path);
    }
}

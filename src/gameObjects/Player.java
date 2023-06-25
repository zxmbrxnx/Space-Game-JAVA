package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;
import graphics.Loader;
import graphics.Sound;
import input.KeyBoard;
import input.MouseManager;
import main.Window;
import math.Vector2;
import states.GameState;

public class Player extends MovingGameObject{

    private Vector2 heading;
    private Vector2 acceleration;
    private boolean isMoving = false;
    private Animation estela;
    private Chronometer fireRate = new Chronometer();

    private boolean spawing, visible;

    private Chronometer spawnTime, flickerTime;

    private boolean isCharged = false;
    private Sound laserSound;
    private Sound playerHit;
    protected Sound gameOverSound;

    public Player(Vector2 position, Vector2 velocity, Double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        heading = new Vector2(0, 1);
        acceleration = new Vector2();
        this.gameState = gameState;
        fireRate = new Chronometer();
        spawnTime = new Chronometer();
        flickerTime = new Chronometer();
        spawnTime.run(Constants.SPAWNING_TIME);
        estela = new Animation(Assets.estela, 50, getCenter().add(heading.scale(width)), true);
        laserSound = new Sound(Assets.playerShoot);
        playerHit = new Sound(Assets.playerLoose);
        gameOverSound = new Sound(Assets.gameOverSound);
    }

    @Override
    public void update() {

        if(!spawnTime.isRunning()){
            spawing = false;
            visible = true;
        }

        if(spawing){
            if(!flickerTime.isRunning()){
                flickerTime.run(Constants.FLICKER_TIME);
                visible = !visible;
            }
        }

        if (KeyBoard.SHOOT && !fireRate.isRunning() && spawing == false || MouseManager.drawReady && !fireRate.isRunning()  && spawing == false) {
            
            if(isCharged){
                gameState.getMovingObjects().add(0,new LaserCharged(
                    getCenter().add(heading.scale(width)), 
                    heading, 
                    Constants.LASER_MAX_VEL, 
                    angle + Math.PI / 2, 
                    Assets.laserCharged[0],
                    gameState,
                    true
                ));
            }else{
                gameState.getMovingObjects().add(0,new Laser(
                getCenter().add(heading.scale(width)), 
                heading, 
                Constants.LASER_MAX_VEL, 
                angle + Math.PI / 2, 
                Assets.laserChargedSingle[0],
                gameState,
                true
            ));
            }
            laserSound.play();
            fireRate.run(Constants.FIRE_RATE);

            //acceleration = heading.scale(-10.0);
        }

        if(laserSound.getFramePosition() > 7000){
            laserSound.stop();
        }

        angle = Math.atan2(MouseManager.mouse_y - position.getY(), MouseManager.mouse_x - position.getX());
        
        if (KeyBoard.RIGHT) {
            angle += Constants.DELTA_ANGLE;
            Assets.player = Loader.imageLoader(Assets._playerImg1);
        }
        if (KeyBoard.LEFT) {
            angle -= Constants.DELTA_ANGLE;
            Assets.player = Loader.imageLoader(Assets._playerImg2);
        }
        if (!KeyBoard.RIGHT && !KeyBoard.LEFT) {
            Assets.player = Loader.imageLoader(Assets._playerImg0);
        }
        if (KeyBoard.POWER) {
            isCharged = !isCharged;
        }
        
        if (KeyBoard.UP) {
            acceleration = heading.scale(Constants.ACC);
            isMoving = true;
        }else{
            if (velocity.getMagnitude() != 0) {
                acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC / 2);
            }
            isMoving = false;
        }

        velocity = velocity.add(acceleration);

        velocity = velocity.limit(Constants.PLAYER_MAX_VEL);

        heading = heading.setDirection(angle);
        position = position.add(velocity);

        if (position.getX() > Constants.WIDTH - Assets.player.getWidth()) {
            position.setX(0);
        }
        if (position.getX() < 0 - Assets.player.getWidth()) {
            position.setX(Constants.WIDTH - Assets.player.getWidth());
        }
        if (position.getY() > Constants.HEIGHT - Assets.player.getHeight()) {
            position.setY(0);
        }
        if (position.getY() < 0 - Assets.player.getHeight()) {
            position.setY(Constants.HEIGHT - Assets.player.getHeight());
        }

        fireRate.update();
        spawnTime.update();
        flickerTime.update();
        estela.update();
        collidesWith();
    }

    @Override
    public void draw(Graphics g) {

        if(!visible){
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX(), position.getY() + height/2);
        at1.rotate(angle + Math.PI / 2, width / 2,0);
        
        // Dibujar estela
        if (isMoving) {
            g2d.drawImage(estela.getCurrentFrame(), at1, null);
        }

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle + Math.PI / 2, width / 2, height / 2);

        g2d.drawImage(Assets.player, at, null);

        //g.setColor(Color.red);
        //g.fillRect((int) position.getX(), (int) position.getY(), width, height);
        //g.drawOval((int) position.getX(), (int) position.getY(), width, height);
    }
    
    @Override
    public void Destroy(){
        spawing = true;
        spawnTime.run(Constants.SPAWNING_TIME);
        gameState.getMessages().add(new Message("-1 Life", position, Color.RED, true, true, Assets.fontMed, gameState));
        
        gameState.lives--;
        
        if(gameState.lives > 0){
            playerHit.play();
        }
        if(gameState.lives == 0){
            gameOverSound.play();
            super.Destroy();
        }
        resetValues();
    }

    public void resetValues(){
        angle = 0;
        velocity = new Vector2();
        position = new Vector2(Constants.WIDTH/2 - Assets.player.getWidth()/2, Constants.HEIGHT/2 - Assets.player.getHeight()/2);
    }

    public boolean isSpawing() {
        return spawing;
    }
}

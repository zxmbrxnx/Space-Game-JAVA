package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Animation;
import graphics.Assets;
import graphics.Sound;
import math.Vector2;
import states.GameState;

public class EnemyAlien extends MovingGameObject{

    private Animation alieAnimation;
    private ArrayList<Vector2> path;
    private Vector2 currentNode;
    private int index;
    private boolean following;
    private int enemyLives = 3;
    private Sound alienShoot;
    private Chronometer fireRate;

    public EnemyAlien(Vector2 position, Vector2 velocity, Double maxVel, BufferedImage texture, ArrayList<Vector2> path, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.path = path;
        index = 0;
        following = true;
        fireRate = new Chronometer();
        fireRate.run(Constants.ALIEN_FIRE_RATE);
        alieAnimation = new Animation(
            Assets.alienAnimation, 
        150, 
        position.subtract(new Vector2(Assets.laserCharged[0].getWidth()/2, Assets.laserCharged[0].getHeight()/2) ), 
        true);
        alienShoot = new Sound(Assets.alienShoot);
    }

    private Vector2 pathFollowing() {
        currentNode = path.get(index);
        
        double distanceToNode = currentNode.subtract(getCenter()).getMagnitude();

        if (distanceToNode < Constants.NODE_RADIUS) {
            index++;
            if (index >= path.size()) {
                //following = false;
                index = 0;
            }
        }

        return seekForce(currentNode);
    }

    private Vector2 seekForce(Vector2 target) {
        Vector2 desired = target.subtract(getCenter());

        desired = desired.normalize().scale(maxVel);

        return desired.subtract(velocity);
    }

    @Override
    public void update() {
        
        Vector2 _pathFollowing = new Vector2();

        if (following) {
            _pathFollowing = pathFollowing();
        } 

        _pathFollowing = _pathFollowing.scale(1/Constants.ALIEN_MASS);
        velocity = velocity.add(_pathFollowing);
        velocity = velocity.limit(maxVel);
        position = position.add(velocity);

        /*
        if (position.getX() < 0 || position.getX() > Constants.WIDTH || position.getY() < 0 || position.getY() > Constants.HEIGHT) {
            Destroy();
        }
        */
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

        // shoot
        if (!fireRate.isRunning()) {

            Vector2 toPlayer = gameState.getPlayer().getCenter().subtract(getCenter());

            toPlayer = toPlayer.normalize();

            double currentAngle = toPlayer.getAngle();

            currentAngle += Math.random()* Constants.ALIEN_ANGLE_RANGE - Constants.ALIEN_ANGLE_RANGE / 2;

            if(toPlayer.getX() < 0)
                currentAngle = -currentAngle + Math.PI;

            toPlayer = toPlayer.setDirection(currentAngle);
            alienShoot.play();
            Laser laser = new Laser(
                getCenter().add(toPlayer.scale(width)), 
                toPlayer, 
                Constants.LASER_MAX_VEL, 
                currentAngle + Math.PI/2, 
                Assets.laser,
                gameState,
                false
            );
            gameState.getMovingObjects().add(0, laser);
            fireRate.run(Constants.ALIEN_FIRE_RATE);
        }
        
        collidesWith();
        alieAnimation.update();
        fireRate.update();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);

        g2d.drawImage(alieAnimation.getCurrentFrame(), at, null);
        g.setColor(Color.GREEN);
        
        if(enemyLives == 3){
            g.fillRect((int) position.getX(), (int) position.getY() -5, width, 5);
        }
        if(enemyLives == 2){
            g.fillRect((int) position.getX(), (int) position.getY() -5, width/2, 5);
        }
        if(enemyLives == 1){
            g.fillRect((int) position.getX(), (int) position.getY() -5, width/4, 5);
        }

        // PATH
        /*
        for(int i = 0; i < path.size(); i++){
            Vector2 _path = path.get(i);
            g.setColor(Color.PINK);
            g.drawOval((int) _path.getX(), (int) _path.getY(), 10,10);
        }
        */
    }

    @Override
    public void Destroy() {
        if(enemyLives == 1){
            gameState.addScore(Constants.ALIEN_SCORE, position);
            super.Destroy();
        }
        enemyLives--;
    }
    
}

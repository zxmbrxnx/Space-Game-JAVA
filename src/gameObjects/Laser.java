package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;
import math.Vector2;
import states.GameState;

public class Laser extends MovingGameObject{

    public boolean isPlayer = true;
    Animation laserAnimation;
    
    public Laser(Vector2 position, Vector2 velocity, Double maxVel, Double angle, BufferedImage texture, GameState gameState, Boolean isPlayer) {
        super(position, velocity, maxVel, texture, gameState);
        this.angle = angle;
        this.velocity = velocity.scale(maxVel);
        this.isPlayer = isPlayer;

        laserAnimation = new  Animation(
                Assets.laserChargedSingle, 
                50, 
                position.subtract(new Vector2(Assets.laserChargedSingle[0].getWidth()/2, Assets.laserChargedSingle[0].getHeight()/2) ), 
                true
        );
    }
     
    @Override
    public void update() {
        position = position.add(velocity);

        if (position.getX() < 0 || position.getX() > Constants.WIDTH || position.getY() < 0 || position.getY() > Constants.HEIGHT) {
            Destroy();
        }
        laserAnimation.update();
        collidesWith();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX() - width / 2, position.getY());
        at.rotate(angle, width/2, 0);
        
        g2d.drawImage(laserAnimation.getCurrentFrame(), at, null);
    }
    /*
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX() - 15, position.getY() - height / 2);
        at.rotate(angle, 15, height/2);
        
        g2d.drawImage(texture, at, null);
 
        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + 10, position.getY() - height / 2);
        at1.rotate(angle, -10, height/2);
        
        g2d.drawImage(texture, at1, null);
    }
    */
    @Override
    public Vector2 getCenter() {
        return new Vector2(position.getX() + width/2, position.getY() + width/2);
    }
}

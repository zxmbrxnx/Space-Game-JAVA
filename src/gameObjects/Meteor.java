package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2;
import states.GameState;

public class Meteor extends MovingGameObject{

    private Size size;

    public Meteor(Vector2 position, Vector2 velocity, Double maxVel, BufferedImage texture, GameState gameState, Size size) {
        super(position, velocity, maxVel, texture, gameState);
        this.size = size;
        this.velocity = velocity.scale(maxVel);
    }
    
    @Override
    public void update() {
        
        position = position.add(velocity);

        if (position.getX() > Constants.WIDTH) {
            position.setX(-width);
        }
        if (position.getX() < -width ) {
            position.setX(Constants.WIDTH);
        }
        if (position.getY() > Constants.HEIGHT) {
            position.setY(-height);
        }
        if (position.getY() < -height) {
            position.setY(Constants.HEIGHT);
        }

        angle += Constants.DELTA_ANGLE;
    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);

        g2d.drawImage(texture, at, null);
        
        g.setColor(Color.red);
        g.drawOval((int) position.getX(), (int) position.getY(), width, height);
    }

    @Override
    public void Destroy() {
        gameState.divideMeteoro(this);
        gameState.addScore(Constants.METEOR_SCORE, position);
        super.Destroy();

    }

    public Size getSize() {
        return size;
    }
}

package gameObjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2;
import states.GameState;

public abstract class MovingGameObject extends GameObject{

    protected Vector2 velocity;
    protected AffineTransform at;
    protected double angle;
    protected GameState gameState;
    protected double maxVel;

    protected int width;
    protected int height;

    protected Sound explSound;

    protected boolean Dead;


    public MovingGameObject(Vector2 position, Vector2 velocity, Double maxVel, BufferedImage texture, GameState gameState) {
        super(position, texture);
        this.velocity = velocity;
        this.maxVel = maxVel;
        width = texture.getWidth();
        height = texture.getHeight();
        angle = 0;
        this.gameState = gameState;
        explSound = new Sound(Assets.explosion);
        Dead = false;

    }

    protected void collidesWith() {
        ArrayList<MovingGameObject> movingObjects = gameState.getMovingObjects(); 
		
		for(int i = 0; i < movingObjects.size(); i++){
			
			MovingGameObject m  = movingObjects.get(i);
			
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().subtract(getCenter()).getMagnitude();
			
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.Dead && !Dead){
				objectCollision(m, this);
			}
		}
    }

    private void objectCollision(MovingGameObject a, MovingGameObject b) {
        if (a instanceof Player && ((Player)a).isSpawing()){
            return;
        }
        if (b instanceof Player && ((Player)b).isSpawing()){
            return;
        }
        if (a instanceof Player && b instanceof Laser && ((Laser)b).isPlayer){
            return;
        }
        if (b instanceof Player && a instanceof Laser && ((Laser)a).isPlayer){
            return;
        }
        if (a instanceof Player && b instanceof LaserCharged && ((LaserCharged)b).isPlayer){
            return;
        }
        if (b instanceof Player && a instanceof LaserCharged && ((LaserCharged)a).isPlayer){
            return;
        }
        if (b instanceof EnemyAlien && a instanceof EnemyAlien){
            return;
        }
        if ((b instanceof EnemyAlien && a instanceof Meteor) || (a instanceof EnemyAlien && b instanceof Meteor)){
            return;
        }
        if ((b instanceof EnemyAlien && a instanceof Laser && !((Laser)a).isPlayer) || (a instanceof EnemyAlien && b instanceof Laser && !((Laser)b).isPlayer)){
            return;
        }
        if(!(a instanceof Meteor && b instanceof Meteor)){
			gameState.playExplotion(getCenter());
            a.Destroy();
			b.Destroy();
		}
    }

    
    protected Vector2 getCenter() {
        return new Vector2(position.getX() + width/2, position.getY() + height/2);
    }

    protected void Destroy() {
        Dead = true;
        if(!(this instanceof Laser) && !(this instanceof LaserCharged) && !(this instanceof Meteor) ){
            explSound.play();
        }
    }

    public boolean isDead() {return Dead;}
}

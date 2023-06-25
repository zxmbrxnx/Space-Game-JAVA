package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;
import math.Vector2;

public class EstelaNave extends GameObject{
    
    protected AffineTransform at;
    protected double angle;

    private Animation estela;

    protected int width;
    protected int height;

    public EstelaNave(Vector2 position, BufferedImage texture, Double angle) {
        super(position, texture);
        this.angle = angle;
        width = texture.getWidth();
        height = texture.getHeight();
        estela = new Animation(
                Assets.estela, 
                10, 
                position.subtract(new Vector2(Assets.estela[0].getWidth()/2, Assets.estela[0].getHeight()/2) ), true
            );
    }

    @Override
    public void update() {
        estela.update();
    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX() - (width +7), position.getY() + height);
        at.rotate(angle, + (width+7), -height);
        // Estela
        g2d.drawImage(estela.getCurrentFrame(), at, null);
    }
    
}

package gameObjects;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import math.Vector2;

public abstract class GameObject {

    protected BufferedImage texture;
    protected Vector2 position;

    public GameObject(Vector2 position, BufferedImage texture) {
        this.position = position;
        this.texture = texture;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
    

}

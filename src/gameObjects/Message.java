package gameObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import graphics.Text;
import math.Vector2;
import states.GameState;

public class Message {
    String text;
    private GameState gameState;

    private float alpha;
    private final float deltaAlpha = 0.01f;

    private Vector2 position;
    private Color color;
    private boolean center;
    private boolean fade;
    private Font font;

    public Message(String text,Vector2 position, Color color, boolean center, boolean fade, Font font, GameState gameState) {
        this.position = position;
        this.text = text;
        this.color = color;
        this.center = center;
        this.fade = fade;
        this.font = font;
        this.gameState = gameState;

        if(fade){
            alpha = 1;
        }else{
            alpha = 0;
        }

    }

    public void draw(Graphics2D g2d){

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        Text.drawText(g2d, text, position, center, color, font);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        position.setY(position.getY() - 1);

        if(fade){
            alpha -= deltaAlpha;
        }else{
            alpha += deltaAlpha;
        }

        if(fade && alpha < 0 || !fade && alpha > 1){
            gameState.getMessages().remove(this);
        }
    }
}

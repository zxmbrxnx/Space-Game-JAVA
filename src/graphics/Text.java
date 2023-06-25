package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import math.Vector2;

public class Text {
    

    public static void drawText(Graphics g, String text, Vector2 position, boolean center, Color color, Font font){
        g.setColor(color);
        g.setFont(font);
        Vector2 pos = new Vector2(position.getX(), position.getY());

        if(center){
            FontMetrics fm = g.getFontMetrics(font);
            pos.setX(pos.getX() - fm.stringWidth(text) / 2);
            pos.setY(pos.getY() - fm.getHeight() / 2);
        }
        g.drawString(text, (int) pos.getX(), (int) pos.getY());
        g.setFont(null);
    }

}

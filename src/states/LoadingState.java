package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Loader;
import graphics.Text;
import math.Vector2;

public class LoadingState extends State{

    private Thread loadingThread;
    private Font font;

    public LoadingState(Thread loadingThread){
        this.loadingThread = loadingThread;
        this.loadingThread.start();
        font = Loader.loadFont("/fonts/PressStart2P.ttf", 26);
    }


    @Override
    public void update(){

        if(Assets.loaded){
            State.changeState(new MenuState());
            try {
                loadingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void draw(Graphics g) {
        GradientPaint gp = new GradientPaint(
            Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
            Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
            Color.WHITE, 
            Constants.WIDTH / 2 + Constants.LOADING_BAR_WIDTH / 2,
            Constants.HEIGHT / 2 + Constants.LOADING_BAR_HEIGHT / 2,
            Color.RED
            );

            Graphics2D g2d = (Graphics2D)g;
            g2d.setPaint(gp);

            float porcentaje = 0.4f + (Assets.count / Assets.MAX_COUNT);

            g2d.fillRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
            Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
            (int)(Constants.LOADING_BAR_WIDTH * porcentaje),
            Constants.LOADING_BAR_HEIGHT);
    
            g2d.drawRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
                    Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
                    Constants.LOADING_BAR_WIDTH,
                    Constants.LOADING_BAR_HEIGHT);
            
            Text.drawText(g2d, "Cargando...", new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 50),
                   true, Color.WHITE, font);
            

    }
    
}

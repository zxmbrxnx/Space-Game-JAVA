package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Text;
import math.Vector2;
import ui.Action;
import ui.Button;

public class MenuState extends State{

    private ArrayList<Button> buttons;

    public MenuState(){
        buttons = new ArrayList<Button>();

        buttons.add(new Button(
            Assets.greyBtn,
            Assets.blueBtn,
            Constants.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
            Constants.HEIGHT / 2 - Assets.greyBtn.getHeight(),
            Constants.PLAY,
            new Action() {
                @Override
                public void doAction(){
                    State.changeState(new GameState());
                }
            }
        ));

        buttons.add(new Button(
            Assets.greyBtn,
            Assets.blueBtn,
            Constants.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
            Constants.HEIGHT / 2 + Assets.greyBtn.getHeight() / 2,
            Constants.EXIT,
            new Action() {
                @Override
                public void doAction(){
                    System.exit(0);
                }
            }
        ));
    }

    @Override
    public void update() {
        for(Button b: buttons){
            b.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for(Button b: buttons){
            b.draw(g);
        }
        Graphics2D g2d = (Graphics2D)g;
        Text.drawText(g2d, "SPACE GAME v1.0", new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 150),
            true, Color.WHITE, Assets.fontBig);

        Text.drawText(g2d, "By: zxmbrxnx", new Vector2(860, 550),
            true, Color.WHITE, Assets.fontMed);
    }
    
}

package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameObjects.Constants;
import gameObjects.EnemyAlien;
import gameObjects.GameObject;
import gameObjects.Message;
import gameObjects.Meteor;
import gameObjects.MovingGameObject;
import gameObjects.Player;
import gameObjects.Size;
import graphics.Animation;
import graphics.Assets;
import graphics.Sound;
import graphics.Text;
import math.Vector2;

public class GameState {

    private Player player;
    private ArrayList<MovingGameObject> movingObjects = new ArrayList<MovingGameObject>();
    private ArrayList<GameObject> Objects = new ArrayList<GameObject>();
    private ArrayList<Animation> explotions = new ArrayList<Animation>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    
    private int score = 0;
    public int lives = 2;

    private int meteors;
    private int waves = 1;

    private Sound backgrounSound;

    public GameState() {
        player = new Player(new Vector2(Constants.WIDTH/2 - Assets.player.getWidth()/2, Constants.HEIGHT/2 - Assets.player.getHeight()/2), new Vector2(),(double) 5, Assets.player, this);
        movingObjects.add(player);
        backgrounSound = new Sound(Assets.backgroundMusic);
        meteors = 1;

        startWave();
        //backgrounSound.changeVolume(-10.0f);
        //backgrounSound.loop();
    }

    private void startWave() {

        messages.add(new Message("Oleada " + waves, new Vector2(Constants.WIDTH/2, Constants.HEIGHT/2),  Color.WHITE, true, true, Assets.fontBig, this));

        double x, y;
        for(int i = 0; i < meteors; i++){
            x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
            y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;

            BufferedImage texture = Assets.bigs[(int) (Math.random()*Assets.bigs.length)];

            movingObjects.add(
                new Meteor(
                    new Vector2(x,y), 
                    new Vector2(0,1).setDirection(Math.random()*Math.PI * 2), 
                    Constants.METEOR_MAX_VEL * Math.random() + 1, 
                    texture, 
                    this, 
                    Size.BIG)
            );
            
        }
        meteors++;
        waves++;
        spawnAlien();
    }

    private void spawnAlien(){

        ArrayList<Vector2> path = new ArrayList<Vector2>();

        int rand = (int) (Math.random()*2);

        double x = rand == 0 ? Math.random()*Constants.WIDTH : 0;
        double y = rand == 0 ? 0 : Math.random()*Constants.HEIGHT;

        double posX, posY;

        posX = Math.random()*Constants.WIDTH;
        posY = Math.random()*Constants.HEIGHT;
        path.add(new Vector2(posX, posY));

        posX = Math.random()*(Constants.WIDTH/2) + (Constants.WIDTH/2);
        posY = Math.random()*Constants.HEIGHT;
        path.add(new Vector2(posX, posY));

        posX = Math.random()*Constants.WIDTH/2;
        posY = Math.random()*(Constants.HEIGHT/2) + (Constants.HEIGHT/2);
        path.add(new Vector2(posX, posY));

        posX = Math.random()*(Constants.WIDTH/2) + (Constants.WIDTH/2);
        posY = Math.random()*(Constants.HEIGHT/2) + (Constants.HEIGHT/2);
        path.add(new Vector2(posX, posY));

        movingObjects.add(new EnemyAlien(
            new Vector2(x,y), 
            new Vector2(), 
            (double) 3, 
            Assets.player, 
            path, 
            this));
    }

    public void playExplotion(Vector2 position){
        explotions.add(new Animation(
                Assets.exp, 
                50, 
                position.subtract(new Vector2(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2) ), false
            
            ));
    }

    public void update() {
        for(int i = 0; i < movingObjects.size(); i++){
            movingObjects.get(i).update();
        }
        for(int i = 0; i < Objects.size(); i++){
            Objects.get(i).update();
        }
        // Explote
        for(int i = 0; i < explotions.size(); i++){
            Animation animation = explotions.get(i);
            animation.update();
            if(!animation.isRunning()){
                explotions.remove(i);
            }
        }

        for(int i = 0; i < movingObjects.size(); i++){
            if (movingObjects.get(i) instanceof Meteor){
                return;
            }
        }

        startWave();
    }

    public void addScore(int value, Vector2 position){
        score += value;
        messages.add(new Message("+" + value, position, Color.WHITE, true, true, Assets.fontMed, this));
    }

    public void divideMeteoro(Meteor meteor){
        Size size = meteor.getSize();

        BufferedImage[] textures = size.textures;

        Size newSize = null;

        switch (size) {
            case BIG:
                newSize = Size.MED;
                break;
            case MED:
                newSize = Size.SMALL;
                break;
            case SMALL:
                newSize = Size.TINY;
                break;
            default:
                return;
        }

        for(int i = 0; i < size.quantity; i++){
            movingObjects.add(
                new Meteor(
                    meteor.getPosition(), 
                    new Vector2(0,1).setDirection(Math.random()*Math.PI * 2), 
                    Constants.METEOR_MAX_VEL * Math.random() + 1, 
                    textures[(int) (Math.random()*textures.length)], 
                    this, 
                    newSize)
            );
        }
    }

    private void drawScore(Graphics g){
        Vector2 pos = new Vector2(940, 10);

        if(score > 9){
            pos = new Vector2(920, 10);
        }
        if(score > 99){
            pos = new Vector2(900, 10);
        }
        if(score > 999){
            pos = new Vector2(880, 10);
        }

        String scoreToString = Integer.toString(score);

        for(int i = 0; i < scoreToString.length(); i++){
            g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i+1))], (int) pos.getX(), (int) pos.getY(),Assets.player.getWidth() -10,Assets.player.getWidth() -10, null);
            pos.add(new Vector2(Assets.numbers[0].getWidth(), 0));
            pos.setX(pos.getX() + 20);
        }
    }

    private void drawLives(Graphics g){
        Vector2 livePos = new Vector2(20, 10);
        
        g.drawImage(Assets.player, (int) livePos.getX(), (int) livePos.getY(),Assets.player.getWidth() -10, Assets.player.getWidth() -10,null);

        String livesToString = Integer.toString(lives);

        Vector2 pos = new Vector2(livePos.getX(), livePos.getY());

        for(int i = 0; i < livesToString.length(); i++){
            int number = Integer.parseInt(livesToString.substring(i, i+1));
            if(number < 0){
                break;
            }

            g.drawImage(Assets.numbers[number], (int) pos.getX() + 30, (int) pos.getY(),Assets.numbers[number].getWidth() -10,Assets.numbers[number].getWidth() -10, null);
            pos.setX(pos.getX() + 20);
        }
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for(int i = 0; i < messages.size(); i++){
            messages.get(i).draw(g2d);
        }

        for(int i = 0; i < movingObjects.size(); i++){
            movingObjects.get(i).draw(g);
        }
        for(int i = 0; i < Objects.size(); i++){
            Objects.get(i).draw(g);
        }
        // Explote
        for(int i = 0; i < explotions.size(); i++){
            Animation animation = explotions.get(i);
            g2d.drawImage(animation.getCurrentFrame(), (int) animation.getPosition().getX(), (int) animation.getPosition().getY(), null);
        }
        drawScore(g);
        drawLives(g);
    }

    public ArrayList<MovingGameObject> getMovingObjects() {
        return movingObjects;
    }

    public ArrayList<GameObject> getObjects() {
        return Objects;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMovingObjects(ArrayList<MovingGameObject> movingObjects) {
        this.movingObjects = movingObjects;
    }

    public Player getPlayer() {
        return player;
    }

    
}

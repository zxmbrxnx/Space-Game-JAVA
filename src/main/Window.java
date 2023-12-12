package main;
import javax.swing.JFrame;

import gameObjects.Constants;
import graphics.Assets;
import input.KeyBoard;
import input.MouseManager;
import states.GameState;
import states.LoadingState;
import states.MenuState;
import states.State;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements Runnable{

    private Canvas canvas;
    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 60;
    private double TARGETTIME = 1000000000 / FPS;
    private double delta = 0;
    private int AVERAGEFPS = FPS;

    private KeyBoard keyBoard;
    Cursor _cursor;
    Image cursorImage;
    private MouseManager m;

    public Window() {

        setTitle("Space game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);

        canvas = new Canvas();
        keyBoard = new KeyBoard();

        m = new MouseManager();
	

        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setFocusable(true);

        add(canvas);
        canvas.addKeyListener(keyBoard);
        canvas.addMouseListener(m);
		canvas.addMouseMotionListener(m);
        

        setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new Window().start();
    } 

    public void update() {
        keyBoard.update();
        State.getCurrentState().update();

        //Cambiar cursor
        if(State.getCurrentState() instanceof GameState){
            //Cursor
            cursorImage = Toolkit.getDefaultToolkit().getImage("res/cursor/cursor.png");
            _cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "cursor_personalizado");
            canvas.setCursor(_cursor);
        }else{
            canvas.setCursor(Cursor.getDefaultCursor());
        }
        //System.out.println("Position X: " + m.mouse_x + "\n Position Y: " + m.mouse_y);
    }

    public void draw() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        // Draw here

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        // Mostrar FPS del juego
        g.setFont(null);
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + AVERAGEFPS, 10, 550);
        
        State.getCurrentState().draw(g);
        
        g.setFont(null);
        // End drawing
        g.dispose();
        bs.show();
    }

    private void init() {

        Thread loadingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Initialize here
                Assets.init();
            }
            
        });


        State.changeState(new LoadingState(loadingThread));
    }

    @Override
    public void run() {
        
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        init();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / TARGETTIME;
            time += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }
            if (time >= 1000000000) {
                AVERAGEFPS = frames;
                frames = 0;
                time = 0;
            }
        }

        stop();
    }

    private void start() {
        thread = new Thread(this);
        thread.start();

        running = true;
    }

    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

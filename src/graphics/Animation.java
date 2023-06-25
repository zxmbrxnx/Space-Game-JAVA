package graphics;

import java.awt.image.BufferedImage;

import math.Vector2;

public class Animation {

    private BufferedImage[] frames;
    private int velocity;
    private boolean running, loop;
    private int index;
    private Vector2 position;

    private long lastTime, time;

    public Animation(BufferedImage[] frames, int velocity, Vector2 position, boolean loop) {
        this.frames = frames;
        this.velocity = velocity;
        this.position = position;
        index = 0;
        running = true;
        time = 0;
        lastTime = System.currentTimeMillis();
        this.loop = loop;
    }

    public void update() {
        if (running) {
            time += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if (time > velocity) {
                index++;
                time = 0;
                if (index >= frames.length) {
                    index = 0;
                    if (!loop) {
                        running = false;
                    }
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Vector2 getPosition() {
        return position;
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
}

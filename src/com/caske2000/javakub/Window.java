package com.caske2000.javakub;

import com.caske2000.javakub.gfx.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * Created by Caske2000 on 03/08/2015. Thanks to ScratchForFun for helping with the rendering part.
 */
public class Window implements Runnable
{
    private JFrame frame;
    private Display display;
    private boolean isRunning;

    // Window properties
    public int WIDTH, HEIGHT;
    private String TITLE;

    private int TARGET_TPS;

    public Window(String TITLE, int WIDTH, int HEIGHT)
    {
        this.TITLE = TITLE;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        this.TARGET_TPS = 30;

        // Initialize
        this.frame = new JFrame(TITLE);
        //
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set size
        this.frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // Update window (with size)
        this.frame.pack();
        // Centers the window
        this.frame.setLocationRelativeTo(null);

        this.frame.setResizable(false);
        this.frame.setVisible(true);

        this.display = new Display(this);
        this.frame.add(this.display);
    }

    public void start()
    {
        isRunning = true;
        new Thread(this).start();
        System.out.println("Thread started!");
    }

    public void stop()
    {
        System.out.println("Stopped!");
        isRunning = false;
    }

    public void tick()
    {

    }

    public void render()
    {
        BufferStrategy bs = this.display.getBufferStrategy();
        if (bs == null)
        {
            this.display.createBufferStrategy(2);
            this.display.requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);

        // Draw stuff to the frame here!
        g.drawString("Hello World!", 5, 15);

        g.dispose();
        bs.show();
    }

    @Override
    public void run()
    {
        int fps = 0, tick = 0;
        double fpsTime = System.currentTimeMillis();

        // FPS = Frames per second, secsPerTick = seconds per frame
        double secsPerTick = 1D / TARGET_TPS;
        double nsPerTick = secsPerTick * 1000000000D;
        double then = System.nanoTime(), now;
        // When this gets to 1, we will update the frame. This creates our fps.
        double unprocessed = 0;

        while (isRunning)
        {
            now = System.nanoTime();
            unprocessed += (now - then) / nsPerTick;
            then = now;

            while (unprocessed >= 1)
            {
                tick();
                tick++;
                unprocessed--;
            }

            try
            {
                Thread.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            render();
            fps++;

            if (System.currentTimeMillis() - fpsTime >= 1000)
            {
                System.out.printf("FPS: %d, UPS: %d%n", fps, tick);
                fps = tick = 0;
                fpsTime += 1000;
            }
        }
        this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
    }
}

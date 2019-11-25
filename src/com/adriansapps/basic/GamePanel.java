package com.adriansapps.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 500, HEIGHT = 500;

    private Thread thread;
    private boolean running;
    private boolean right = true, left = false, up = false, down = false;

    private BodyPart b;
    private ArrayList<BodyPart> snake;

    private Apple apple;
    private ArrayList<Apple> apples;

    private Random r;

    private int xCoordinates = 10, yCoordinates = 10, size = 15;
    private int ticks = 0;


    public GamePanel(){
        // set focus on object window
        setFocusable(true);

        // windows size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // make object listen for pressed keys
        addKeyListener(this);

        // call out method to run Panel
        start();

        // init snake
        snake = new ArrayList<>();

        // init apples that appear randomly on board
        apples = new ArrayList<>();
        r = new Random();

    }

    public void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        if (snake.size() == 0){
            b = new BodyPart(xCoordinates, yCoordinates,10);
            snake.add(b);
        }
        ticks++;

        if (ticks > 250000){
            if (right)
                xCoordinates++;
            if (left)
                xCoordinates--;
            if (up)
                yCoordinates--;
            if (down)
                yCoordinates++;

            ticks = 0;

            b = new BodyPart(xCoordinates, yCoordinates, 10);
            snake.add(b);

            if (snake.size() > size)
                snake.remove(0);
        }

        if (apples.size() == 0) {
            int xCoordinates = r.nextInt(49);
            int yCoordinates = r.nextInt(49);

            apples.add(new Apple(xCoordinates, yCoordinates, 10));
        }

        // collision on border
        for (int i = 0; i < apples.size(); i++) {
            if (xCoordinates == apples.get(i).getxCoordinates() &&
                        yCoordinates == apples.get(i).getyCoordinates()){
                size++;
                apples.remove(i);
                i++;
            }
        }

        // collision on snake body part
        for (int i = 0; i < snake.size(); i++) {
            if (xCoordinates == snake.get(i).getxCoordinates() &&
                    yCoordinates == snake.get(i).getyCoordinates()){
                if (i != snake.size() - 1) {
                    System.out.println("Game Over");
                    stop();
                }
            }
        }

        if (xCoordinates < 0 || xCoordinates > 49 || yCoordinates < 0 || yCoordinates > 49){
            System.out.println("Game Over");
            stop();
        }
    }

    public void paint(Graphics g){
        g.clearRect(0, 0, HEIGHT, WIDTH);
        g.setColor(Color.black);
        g.fillRect(0, 0, HEIGHT, WIDTH);
        // drawing the grid
        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(i * 10, 0, i * 10, HEIGHT);
        }

        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(0, i * 10,  HEIGHT, i * 10);
        }

        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }

        for (int i = 0; i < apples.size(); i++) {
            apples.get(i).draw(g);
        }
    }


    @Override
    public void run() {
        while (running){
            tick();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        }

        if (key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        }

        if (key == KeyEvent.VK_UP && !down) {
            right = false;
            left = false;
            up = true;
        }

        if (key == KeyEvent.VK_DOWN && !up) {
            right = false;
            left = false;
            down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

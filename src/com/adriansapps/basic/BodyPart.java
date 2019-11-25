package com.adriansapps.basic;

import java.awt.*;

public class BodyPart {

    private int xCoordinates, yCoordinates, width, height;

    public BodyPart(int xCoordinates, int yCoordinates, int tileSize){
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        width = tileSize;
        height = tileSize;
    }

    public void tick(){

    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(xCoordinates * width, yCoordinates * height, width, height);
    }

    public int getxCoordinates() {
        return xCoordinates;
    }

    public void setxCoordinates(int xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    public int getyCoordinates() {
        return yCoordinates;
    }

    public void setyCoordinates(int yCoordinates) {
        this.yCoordinates = yCoordinates;
    }
}

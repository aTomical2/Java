package com.javaeducational.game.tools;

public class CollisionRect {
    private float x, y;
    private int width;
    private int height;

    public CollisionRect(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //moves rect
    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //checks for collisions
    public boolean collidesWidth (CollisionRect rect) {
        return this.x < rect.getX() + rect.getWidth()
                && this.y < rect.getY() + rect.getHeight()
                && this.x + this.width > rect.getX()
                && this.y + this.height > rect.getY();
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}

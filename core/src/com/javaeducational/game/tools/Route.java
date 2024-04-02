package com.javaeducational.game.tools;

import java.util.ArrayList;

public class Route extends Transport {
    ArrayList<Transport> transportationModes;
    private int startPoint;
    private int endPoint;
    private int length;
    private int carbonFootprint; //Possibly create this from length and carbon footprint of modes
    private int totalTime; //Combine length with times from transport
    public Route(String name, int carbonFootprint, int estimatedTime) {
        super(name, carbonFootprint, estimatedTime);
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }
}

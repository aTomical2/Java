package com.javaeducational.game.tools;

public class Transport {
    private String name;
    private int carbonFootprint;
    private int  estimatedTime;
    public Transport (String name, int carbonFootprint, int estimatedTime) {
        this.name = name;
        this.carbonFootprint = carbonFootprint;
        this.estimatedTime = estimatedTime;
    }

    public int getCarbonFootprint() {
        return carbonFootprint;
    }

    public void setCarbonFootprint(int carbonFootprint) {
        this.carbonFootprint = carbonFootprint;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}

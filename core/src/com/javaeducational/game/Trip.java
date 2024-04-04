package com.javaeducational.game;

import com.javaeducational.game.tools.Route;

public class Trip {
    // Setting the instance variables for the class
    private Route route;
    private int currentLocation;
    private boolean tripStarted;

    public Trip(Route route) {
        this.route= route;
        this.currentLocation = route.getStartPoint();
        this.tripStarted = false;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void startTrip() {
        System.out.println("The trip has started!");
        tripStarted = true;
    }

    //    public void travel() {
//        System.out.println("Player is now traveling... ");
//    }
//    public endTrip(){
//
//    }

}


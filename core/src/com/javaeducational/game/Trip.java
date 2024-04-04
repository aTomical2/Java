package com.javaeducational.game;

import com.javaeducational.game.tools.Route;

public class Trip {
    // Setting the instance variables for the class
    private Route route;
    private int currentLocation;
    private boolean tripStarted;

    public Trip(Route route) {
        this.route = route;
        this.currentLocation = route.getStartPoint();
        this.tripStarted = false;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void startTrip(){
        System.out.println("Trip has started!");
        System.out.println("Player is now traveling... ");
        tripStarted = true;
    }

    public void travel() {
        if (!tripStarted) {
            System.out.println("Trip has not started yet");
            return;
        }

        // Logical check to see if currentLocation is less than endPoint of route
        if (currentLocation < route.getEndPoint()) {
            currentLocation++;      //increment the movement of the player by 1
            System.out.println("Player has now moved to location " + currentLocation);
        } else {
            System.out.println("Player has reached the end point of the route!");
            endTrip();
        }
    }

    public void endTrip() {
    tripStarted = false;
    System.out.println("Current trip has ended!");
    }

}




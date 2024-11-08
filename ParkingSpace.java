package Operating_System;

public class ParkingSpace {
    private int spaceId;
    private boolean isOccupied;
    private int distanceFromEntrance;

    public ParkingSpace(int spaceId, boolean isOccupied, int distanceFromEntrance) {
        this.spaceId = spaceId;
        this.distanceFromEntrance = distanceFromEntrance;
        this.isOccupied = isOccupied;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getDistanceFromEntrance() {
        return distanceFromEntrance;
    }
}
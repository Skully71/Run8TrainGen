package traingen;

import traingen.railvehicles.RailVehicle;

public class RailVehicleInstance {
    private final RailVehicle railVehicle;
    private float loadWeightUSTons;
    private final String destinationTag;
    private final boolean reverseDirection;

    public RailVehicleInstance(final RailVehicle railVehicle, final String destinationTag, boolean reverseDirection) {
        this.railVehicle = railVehicle;
        this.destinationTag = destinationTag;
        this.reverseDirection = reverseDirection;
    }

    public String getDestinationTag() {
        return destinationTag;
    }

    public float getLoadWeightUSTons() {
        return loadWeightUSTons;
    }

    public RailVehicle getRailVehicle() {
        return railVehicle;
    }

    public void setLoadWeightUSTons(float loadWeightUSTons) {
        this.loadWeightUSTons = loadWeightUSTons;
    }
}

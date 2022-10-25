package vladimir.loshchin.drones.model;

public enum DroneModel {

    LIGHT(50),
    MIDDLE(100),
    CRUISER(250),
    HEAVY(500);

    /**
     * Maximum drone of this model can carry.
     */
    private final int maxLoad /* gramm */;

    private DroneModel(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public int maxLoad() {
        return maxLoad;
    }
}

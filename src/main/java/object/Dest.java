package object;

public class Dest {
    private final Location location;
    private final String id;

    public Dest(double latitude, double longitude, int idx, String id) {
        this.location = new Location(latitude, longitude, idx);
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }
}

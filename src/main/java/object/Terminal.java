package object;

public class Terminal {

    private final Location location;
    private final String region, id;

    public Terminal(double latitude, double longitude, String region, int idx, String id) {
        this.location = new Location(latitude, longitude, idx);
        this.region = region;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getRegion() {
        return region;
    }
}

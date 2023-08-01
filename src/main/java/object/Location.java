package object;

public class Location {
    private final double latitude, longitude;
    private final int idx;

    public Location(double latitude, double longitude, int idx) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idx = idx;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getIdx() {
        return idx;
    }
}

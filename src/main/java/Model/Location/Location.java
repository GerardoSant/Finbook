package Model.Location;

public class Location {

    private int PC;
    private String name;
    private String countryCode;
    private double latitude;
    private double longitude;
    private String adminState;
    private String municipality; //adminName2
    private String state; //adminName3

    public Location(int PC, String name, String countryCode, double latitude, double longitude, String adminState,String municipality, String state) {
        this.PC = PC;
        this.name = name;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adminState= adminState;
        this.municipality = municipality;
        this.state = state;
    }

    public int getPC() {
        return PC;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getState() {
        return state;
    }

    public String getAdminState() {
        return adminState;
    }

    @Override
    public String toString() {
        return "Location{" +
                "PC=" + PC +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", adminState='" + adminState + '\'' +
                ", municipality='" + municipality + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

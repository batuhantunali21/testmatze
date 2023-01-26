import java.util.ArrayList;

public class ChargingStation {

    private String _operator;
    private String _street;
    private String _houseNumber;
    private int _postalCode;
    private String _location;
    private String _state;
    private double _latitude;
    private double _longitude;
    private double _powerSupply;
    private ArrayList<ChargingStation> _chargingStationInRange;

    public ChargingStation(String _operator, String _street,
                           String _houseNumber, int _postalCode,
                           String _location,
                           String _state, double _latitude, double _longitude,
                           double _powerSupply) {

        this._operator = _operator;
        this._street = _street;
        this._houseNumber = _houseNumber;
        this._postalCode = _postalCode;
        this._location = _location;
        this._state = _state;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._powerSupply = _powerSupply;
        this._chargingStationInRange = new ArrayList<ChargingStation>();
    }

    public String get_operator() {
        return _operator;
    }

    public void set_operator(String _operator) {
        this._operator = _operator;
    }

    public String get_street() {
        return _street;
    }

    public void set_street(String _street) {
        this._street = _street;
    }

    public String get_houseNumber() {
        return _houseNumber;
    }

    public void set_houseNumber(String _houseNumber) {
        this._houseNumber = _houseNumber;
    }

    public int get_postalCode() {
        return _postalCode;
    }

    public void set_postalCode(int _postalCode) {
        this._postalCode = _postalCode;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public double get_latitude() {
        return _latitude;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public double get_longitude() {
        return _longitude;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    public double get_powerSupply() {
        return _powerSupply;
    }

    public void set_powerSupply(double _powerSupply) {
        this._powerSupply = _powerSupply;
    }

    public ArrayList<ChargingStation> get_chargingStationInRange() {
        return _chargingStationInRange;
    }

    public void set_chargingStationInRange
            (ArrayList<ChargingStation> _chargingStationInRange) {
        this._chargingStationInRange = _chargingStationInRange;
    }

    /**

     @return boolean indicating whether the charging station's latitude and
     longitude are within valid range (between 47 and 55 latitude,
     and between 6 and 15 longitude)
     */
    public boolean isChargingStationValid() {
        return this.get_latitude() < 55 && this.get_latitude() > 47 &&
                this.get_longitude() < 15 && this.get_longitude() > 6;
    }

    @Override
    public String toString() {
        return get_operator() + ", " + get_street() + " " + get_houseNumber() +
                ", " + get_postalCode() + " " + get_location() + ", " +
                get_powerSupply();
    }
}

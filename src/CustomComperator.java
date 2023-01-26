import enums.ProgEACompareOption;

import java.util.Comparator;

public class CustomComperator implements Comparator<ChargingStation> {

    private ProgEACompareOption _option;
    private int _distance;
    public CustomComperator(ProgEACompareOption option) {
        this._option = option;
    }



// Noch ein Konstruktor, weil wir mit der Klasse plz und die entfernung
    // vergleichen

    public CustomComperator(ProgEACompareOption _option, int _minimumDistance) {
        this._option = _option;
        this._distance = _minimumDistance;
    }

    @Override
    public int compare(ChargingStation stationOne,
                           ChargingStation stationTwo) {
        if (this._option == ProgEACompareOption.PostalCode) {
          return  this.comparePostalCode(stationOne, stationTwo);
        }
        else {
            return this.compareDistance(stationOne, stationTwo);
        }

    }

    private int compareDistance(ChargingStation stationOne,
                                ChargingStation stationTwo) {
        double haversine = Haversine.haversine(stationOne.get_latitude(),
                stationOne.get_longitude(), stationTwo.get_latitude(),
                stationTwo.get_longitude());
        if (haversine <= this._distance) {
            return -1;
        }
        else if(haversine > this._distance) {
            return 1;
        }
        return 0;
    }

    private int comparePostalCode(ChargingStation stationOne,
                                  ChargingStation stationTwo) {
        if (stationOne.get_postalCode() == stationTwo.get_postalCode()) {
            return this.comparePowerSupply(stationOne.get_powerSupply(),
                    stationTwo.get_powerSupply());
        }

        if (stationOne.get_postalCode() > stationTwo.get_postalCode()) {
            return 1;
        }
        return -1;
    }

    private int comparePowerSupply(double powerSupplyOne,
                                   double powerSupplyTwo) {
        if (powerSupplyOne < powerSupplyTwo) {
            return 1;
        }
        if (powerSupplyOne > powerSupplyTwo) {
            return -1;
        }
        return 0;
    }



    public void set_distance(int _distance) {
        this._distance = _distance;
    }
}

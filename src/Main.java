import Control.Timer;
import enums.ProgEACompareOption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final String filePath =
            "C:/Users/batuh/IdeaProjects/PRG_EA/src/res/";
    static final String fileName = "csv";

    public static void main(String[] args) throws IOException {

        FileHelper fileHelper = new FileHelper(filePath, fileName);
        if (!fileHelper.checkIfFileExists()) {
            System.out.println("Fehler: Die Datei " + fileName +
                    " wurde in dem Pfad: " + filePath + " nicht gefunden.");
        return;
        }
        System.out.println("Dateifpad: " + filePath);
        Timer.start();
        int amountOfLines = fileHelper.getAmountOfLines();
        long elpasedTime = Timer.getElapsedTimeInMilliseconds();
        System.out.println("Eingelesene Datensätze " + amountOfLines +
                " ( " + elpasedTime + "ms ) ");

        Timer.start();

        ArrayList<String> lines = fileHelper.processFile();
/** AUFGABE 1 AUSGABE FEHLT BREITEN UND LÄNGENGRAD */
        ArrayList<ChargingStation> validChargingStations = new ArrayList<>();
        for (String line : lines) {
            ChargingStation station = createChargingStation(line);
            if (!station.isChargingStationValid()){
                //System.out.println("Trage Ladestation nicht ein. Breiten- " +
                //        "oder " +
                //        "Längengrad nicht korrekt: ");
                continue;
            }
            validChargingStations.add(station);
        }

        elpasedTime = Timer.getElapsedTimeInMilliseconds();

        System.out.println("Anzahl valider Datensätze: " +
                validChargingStations.size() + " ( " + elpasedTime + "ms ) " );

        /** AUFGABE 2 sortierte Ausgabe mit Zeit */
        Timer.start();
        Collections.sort(validChargingStations,
                new CustomComperator(ProgEACompareOption.PostalCode));
        elpasedTime = Timer.getElapsedTimeInMilliseconds();
        //System.out.println("Anzahl valider Datensätze: " +
        //        validChargingStations.size() + " (in " + elpasedTime + "
        //        Ms)");
        for (ChargingStation station : validChargingStations) {
         //   System.out.println(station.toString());
        }

        /** Aufgabe 3
         *
         */
        boolean validInput = true;
        int minimumRangeInput = 0, maximumRangeInput = 0;
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                System.out.println("Bitte eine Epsilonumgebung eingeben");
                minimumRangeInput = Integer.parseInt(br.readLine());
                System.out.println("Bitte maximale Entfernung eingeben");
                maximumRangeInput = Integer.parseInt(br.readLine());
                validInput = true;
            } catch (Exception e) {
                validInput = false;
                System.out.println("Bitte eine natürliche Zahl eingeben...");
            }
        } while (! validInput);


        CustomComperator distanceComperator =
                new CustomComperator
                        (ProgEACompareOption.Distance, minimumRangeInput);
        Timer.start();
        for (int i = 0; i < validChargingStations.size(); i++) {
            for (int j = i+1; j < validChargingStations.size(); j++) {
                int result =
                        distanceComperator.compare(validChargingStations.get(i),
                        validChargingStations.get(j));
                if (result == -1) {
                    validChargingStations.remove(validChargingStations.get(j));
                    j--;
                }
            }
        }

        System.out.println("Programmaufruf mit den Argumenten " + minimumRangeInput + " und "+ maximumRangeInput);
        System.out.println("Lade Daten aus Datei: " + filePath + fileName);
        System.out.println("Prüfe auf Redundanz mit Epsilon = " + minimumRangeInput);
        System.out.println("Anzahl Ladestationen: "
                + validChargingStations.size());

        distanceComperator.set_distance(maximumRangeInput);
        for (int i = 0; i < validChargingStations.size(); i++) {
            for (int j = 0; j < validChargingStations.size(); j++) {
              int result = distanceComperator
                      .compare(validChargingStations.get(i),
                      validChargingStations.get(j));
              if (result == -1) {
                  validChargingStations.get(i)
                    .get_chargingStationInRange()
                    .add(validChargingStations.get(j));
              }
            }
            if (validChargingStations.get(i).get_postalCode()==86153) {
                System.out.print(validChargingStations.get(i).get_postalCode() + " "
                        + validChargingStations.get(i).get_location() + " -> ");
                for(ChargingStation station :
                        validChargingStations.get(i)
                                .get_chargingStationInRange()) {
                    System.out.print(station.get_postalCode() + " "
                            + station.get_location() + ", ");
                }
            }
        }
         System.out.println("Startknoteneingeben: ");
        int startPoint = Integer.parseInt(br.readLine());
        System.out.println("Endknosten");
        int endPoint = Integer.parseInt(br.readLine());
        Optional<ChargingStation> start =
                validChargingStations.stream().filter(f -> f.get_postalCode() == startPoint).findFirst();
        if(start.isPresent()) {
            ArrayList<ChargingStation> currentPath = new ArrayList<>();
            ArrayList<Integer> checkedChargingStation =
                    new ArrayList<>();
            // currentPath.add(start.get());
            findPath(currentPath, start.get(), checkedChargingStation, endPoint);
        for (ChargingStation station :  currentPath) {
            System.out.println(station.toString());
        }
        }
    }

    /**

     This method creates a ChargingStation object by parsing the values from a
     string
     @param line string containing the values for the ChargingStation
     @return ChargingStation object created from the values in the string
     */
    private static ChargingStation createChargingStation(String line) {
        try {
            String[] colums = line.split(";");
            return new ChargingStation(colums[0], colums[1], colums[2],
                    Integer.parseInt(colums[3]), colums[4], colums[5],
                    Double.parseDouble(colums[6]),
                    Double.parseDouble(colums[7]),
                    Double.parseDouble(colums[8]));
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
    private static void minimalRange() {

    }

    private static void maxmimumRange() {

    }
    Stack<ChargingStation> path = new Stack<>();
    Set<Integer> visited = new HashSet<>();

    private static void findPath(ArrayList<ChargingStation> currentPath,
                                 ChargingStation currentStation,
                                 ArrayList<Integer> checkedChargingStations,
                                 int postalCodeDestination) {
        System.out.println(currentStation.toString());
        if (currentPath.size() > 1 && currentPath.contains(currentStation)) {
            currentPath.remove(currentPath.size() - 1 );
            return;
        }
        boolean allChecked = true;
        for (ChargingStation station :
                currentStation.get_chargingStationInRange()) {
            if (! checkedChargingStations.contains(station.get_postalCode())) {
                allChecked = false;
                break;
            }
        }
        if (currentPath.size() == 1 && allChecked) {
            System.out.println("kein pfad gefunden 321321312");
        }
        else {
            if(currentStation.get_chargingStationInRange().size() > 0) {
                checkedChargingStations.add(currentStation.get_postalCode());
                for (ChargingStation station :
                        currentStation.get_chargingStationInRange()) {
                    if (station.get_chargingStationInRange().size() > 0) {
                        Optional<ChargingStation> destination =
                                station.get_chargingStationInRange().stream().filter(f -> f.get_postalCode() == postalCodeDestination)
                                        .findFirst();

                        if (destination.isPresent()) {
                            currentPath.add(destination.get());
                            return;
                        } else {
                            currentPath.add(station);
                            Optional<ChargingStation> nextStation =
                                    station.get_chargingStationInRange().stream().filter
                                                    (f -> !checkedChargingStations.contains(f))
                                            .findFirst();
                            if (nextStation.isPresent()) {
                                findPath(currentPath, nextStation.get(),
                                        checkedChargingStations, postalCodeDestination);
                            } else {
                                findPath(currentPath,
                                        currentPath.get(currentPath.size() - 1),
                                        checkedChargingStations, postalCodeDestination);
                            }

                        }
                    }
                }
            }
            else {
                if (currentPath.size() > 0) {
                    if (currentPath.size() > 1) {
                        ChargingStation previousStation =
                                currentPath.get(currentPath.size() - 2);
                    }
                    findPath(currentPath, currentPath.get(currentPath.size() - 1)
                            , checkedChargingStations, postalCodeDestination);
                } else {
                    System.out.println("kein pfad gefunden3");
                }
            }
        }
    }
}

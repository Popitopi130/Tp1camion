import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    private static double calculateDistance(float latitude1, float longitude1, float latitude2, float longitude2) {
        double earthRadius =  6371000 ;
        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (earthRadius * c);
    }

    public static void writeFile(ArrayList<Building> buildings, String outputFile){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            // Write truck position with location building (index 0)
            writer.write("Truck position: (" +
                    buildings.get(0).getLatitude() + "," + buildings.get(0).getLongitude() + ")");
            writer.write(String.format("\n%-30s%-30s%-30s",
                    "Distance:" + (int) buildings.get(0).getDistance(),
                    "Number of boxes:" + buildings.get(0).getAvailableBoxes(),
                    "Position:(" + buildings.get(0).getLatitude() + "," + buildings.get(0).getLongitude() + ")"));

            //Write other buildings of the list
            int n = buildings.size();
            for (int i = 1; i < n; i++) {
                writer.write(String.format("\n%-30s%-30s%-30s",
                        "Distance:%.1f".formatted(buildings.get(i).getDistance()),
                        "Number of boxes:" + buildings.get(i).getAvailableBoxes(),
                        "Position:(" + buildings.get(i).getLatitude() + "," + buildings.get(i).getLongitude() + ")"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Get the file names
        String info, writeOn;
        try {
            info = args[0];
            writeOn = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            info = args[0];
            writeOn = "output.txt";
        }

        // Read file and create list
        Truck truck = new Truck(0,0,0);
        ArrayList<Building> buildings = new ArrayList<>();
        int boxes, capacity;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(info));
            String line;

            String firtsLine = reader.readLine();
            String boxesS = firtsLine.split(" ")[0];
            String capacityS = firtsLine.split(" ")[1];
            boxes = Integer.parseInt(boxesS);
            capacity = Integer.parseInt(capacityS);

            if (boxes > capacity) {
                boxes = capacity;
            }

            float lat = 0;
            float lon = 0;
            int amounts = 0;
            int amount = 0;


            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                for (String part : parts) {
                    if (part.startsWith("(") && part.endsWith(")")) {
                        String coordinates = part.substring(1, part.length() - 1);
                        String[] coordinateParts = coordinates.split(",");
                        lat = Float.parseFloat(coordinateParts[0].trim());
                        lon = Float.parseFloat(coordinateParts[1].trim());
                        amount = amounts;
                    } else {
                        amounts = Integer.parseInt(part.trim());
                    }
                    if (amount != 0) {
                        Building building = new Building(lat, lon, amount);
                        buildings.add(building);
                        amount = 0;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            capacity = 0;
            boxes = 0;
        }

        // Start timing algorithm
        long startTime = System.nanoTime();

        // Find building with the most boxes and put truck in it
        sortBuildingsByBox(buildings);
        buildings.get(0).changeTruck();
        truck.setTruckLatitude(buildings.get(0).getLatitude());
        truck.setTruckLongitude(buildings.get(0).getLongitude());
        truck.setCapacity(boxes);

        // Do the distance travel optimization
        Building location = buildings.get(0);
        sortBuildingsByDistance(location, buildings);

        // Stop timing algorithm
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration + "ns");

        ArrayList<Building> usedBuildings = new ArrayList<>();
        truck.truckLoad(location.getAvailableBoxes());
        location.setAvailableBoxes(0);
        usedBuildings.add(location);

        int n = buildings.size();
        for (int i = 1; i < n; i++) {
            Building current = buildings.get(i);

            if (truck.getCapacity() > current.getAvailableBoxes()) {
                truck.truckLoad(current.getAvailableBoxes());
                current.setAvailableBoxes(0);
                usedBuildings.add(current);
            } else {
                current.remainingBoxes(truck.getCapacity());
                usedBuildings.add(current);
                break;
            }
        }

        // Write the output file
        writeFile(usedBuildings, writeOn);
    }

    public static void sortBuildingsByBox(ArrayList<Building> buildings) {
        int n = buildings.size();

        for (int i = 1; i < n; i++) {
            Building key = buildings.get(i);
            int j = i - 1;

            while (j >= 0 && buildings.get(j).getAvailableBoxes() < key.getAvailableBoxes()) {
                buildings.set(j + 1, buildings.get(j));
                j--;
            }
            buildings.set(j + 1, key);
        }
    }

    public static void sortBuildingsByDistance(Building location, ArrayList<Building> buildings) {
        int n = buildings.size();

        // Calculate distances from location
        for (int i = 1; i < n; i++) {
            double distance = calculateDistance(location.getLatitude(), location.getLongitude(),
                    buildings.get(i).getLatitude(), buildings.get(i).getLongitude());
            buildings.get(i).setDistance(distance);
        }

        // Sort buildings from closest to longest distance
        for (int i = 1; i < n; i++) {
            Building key = buildings.get(i);
            int j = i - 1;

            while (j >= 0 && buildings.get(j).getDistance() > key.getDistance()) {
                buildings.set(j + 1, buildings.get(j));
                j--;
            }
            buildings.set(j + 1, key);
        }
    }
}
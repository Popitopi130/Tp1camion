import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static double calculateDistance(int latitude1, int longitude1, int latitude2, int longitude2) {
        double earthRadius =  6371000 ;
        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }


    public static void main(String[] args) throws IOException {
        //ecrit le nouveau fichier
        String[] positions ={"ow","er","tt"};
        BufferedWriter writer = new BufferedWriter(new FileWriter("res+.txt"));
        writer.write("Truck position: (" + calculateDistance(9,9,9,9) + ")");
        for(String position: positions){
            writer.write("\n"+"Distance:"+position+"        Number of boxes:()"+"        Position:()");
        }
        writer.close();
        //lecture du fichier
        BufferedReader reader = new BufferedReader(new FileReader("camion_entrepot.txt"));
        String line;

        String firtsLine = reader.readLine();
        String capacity = firtsLine.split(" ")[0];
        String boxes = firtsLine.split(" ")[1];
        ArrayList<Building> buildings = new ArrayList<>();
        double lat = 0;
        double lon = 0;
        int amounts = 0;
        int amount = 0;


        while ( (line = reader.readLine()) != null) {

            String[] parts = line.split(" ");
            for (String part : parts) {
                if (part.startsWith("(") && part.endsWith(")")) {
                    String coordinates = part.substring(1, part.length() - 1);
                    String[] coordinateParts = coordinates.split(",");
                    lat = Double.parseDouble(coordinateParts[0].trim());
                    lon = Double.parseDouble(coordinateParts[1].trim());
                    amount = amounts;


                }
                else {
                    amounts = Integer.parseInt(part.trim());
                    //System.out.println(amount);
                }
                if (amount != 0){
                Building building = new Building(lat,lon,amount);
                buildings.add(building);
                amount = 0;}

            }

        }

        for (Building building : buildings) {
            System.out.println(building.getLatitude());
        }
        //sortBuildings(buildings);
       // System.out.println(buildings);

    }
    public static void sortBuildings(ArrayList<Building> buildings) {
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





}
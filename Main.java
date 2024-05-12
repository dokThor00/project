import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static ArrayList<ArrayList<Double>> locations = new ArrayList<>();
    public static Double minimumdistance = Double.MAX_VALUE;
    public static Integer[] shortestroute;


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int migrosIdx = 0;

        try {
            File file = new File("input01.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                ArrayList<Double> coordinates = new ArrayList<>();
                coordinates.add(Double.parseDouble(parts[0]));
                coordinates.add(Double.parseDouble(parts[1]));
                locations.add(coordinates);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();

        }

        Integer[] initialRoute = new Integer[locations.size() - 1];
        int index = 0;
        for (int i = 1; i < locations.size(); i++) {
            initialRoute[index++] = i;
        }

        permute(initialRoute, 0, migrosIdx);



        // Convert Integer[] to int[] for printing
        String shortestRouteText = Arrays.toString(Arrays.stream(shortestroute).map(i -> i + 1).toArray(Integer[]::new));

        System.out.println( shortestRouteText );
        System.out.println("Distance " + minimumdistance);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Execution time: " + duration + " milliseconds");
    }

    private static void permute(Integer[] arr, int k, int migrosIdx) {
        if (k == arr.length) {
            Integer[] thisroad = arr.clone();
            double distance = calculateRouteDistance(arr, migrosIdx);
            if (distance < minimumdistance) {
                minimumdistance = distance;
                shortestroute = thisroad.clone();
            }
        } else {
            for (int i = k; i < arr.length; i++) {
                Integer temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
                permute(arr, k + 1, migrosIdx);
                temp = arr[k];
                arr[k] = arr[i];
                arr[i] = temp;
            }
        }

    }

    private static double calculateRouteDistance(Integer[] route, int migrosIdx) {
        double distance = 0;
        int prevIdx = migrosIdx;
        for (int locationIdx : route) {
            distance += Math.sqrt(Math.pow(locations.get(locationIdx).get(0) - locations.get(prevIdx).get(0), 2) +
                    Math.pow(locations.get(locationIdx).get(1) - locations.get(prevIdx).get(1), 2));
            prevIdx = locationIdx;
        }
        distance += Math.sqrt(Math.pow(locations.get(migrosIdx).get(0) - locations.get(prevIdx).get(0), 2) +
                Math.pow(locations.get(migrosIdx).get(1) - locations.get(prevIdx).get(1), 2));
        return distance;
    }
}

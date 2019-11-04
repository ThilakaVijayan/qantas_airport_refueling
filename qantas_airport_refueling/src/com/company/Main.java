package com.company;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Situation:
 * - 9 aircraft are parked at an airport and need to be refuelled before they can take off.
 * - Fuel trucks are used to refuel the aircraft.
 * Task:
 * - Place fuel trucks in the grid.
 * - Each aircraft should have one fuel truck next to it (horizontally or vertically).
 * - Fuel trucks do not touch each other, not even diagonally.
 * - The numbers outside the grid show the total number of fuel trucks in the
 * corresponding row or column.
 */
public class Main {

    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.setLevel(Level.OFF);

        //Input - aircraft coordinates
        List<Location> inputList = new ArrayList<>();
        inputList.add(new Location(0, 0));
        inputList.add(new Location(6, 2));
        inputList.add(new Location(3, 1));
        inputList.add(new Location(1, 2));
        inputList.add(new Location(6, 4));
        inputList.add(new Location(0, 5));
        inputList.add(new Location(4, 5));
        inputList.add(new Location(6, 5));
        inputList.add(new Location(2, 6));

        List<Integer> xSum = new ArrayList<>();
        List<Integer> ySum = new ArrayList<>();

        //Input - total number of fuel trucks in each row and column
        xSum.add(2);
        xSum.add(1);
        xSum.add(1);
        xSum.add(1);
        xSum.add(1);
        xSum.add(1);
        xSum.add(2);

        ySum.add(1);
        ySum.add(2);
        ySum.add(0);
        ySum.add(1);
        ySum.add(1);
        ySum.add(1);
        ySum.add(3);

        ColumnRowSum columnRowSum = new ColumnRowSum();
        columnRowSum.setxSum(xSum);
        columnRowSum.setySum(ySum);

        int sizeOfGrid = xSum.size();

        Main main = new Main();
        main.entryMethod(inputList, sizeOfGrid, columnRowSum);
    }

    private void entryMethod(List<Location> inputList, int sizeOfGrid, ColumnRowSum columnRowSum) {
        Map<Integer, List<Location>> possibleOutputMap = new HashMap<Integer, List<Location>>();
        Set<Location> inputSet = new HashSet<>(inputList);
        int index = 0;

        printInput(inputList, sizeOfGrid, columnRowSum);

        log.info("Input Location List is :" + inputList);
        for (Location input : inputList) {
            List<Location> outputList = getAllPossibleLocations(input, inputSet, sizeOfGrid); //STEP1
            log.info("For the Input Location " + input +
                    " the possible output Locations are " + outputList);
            possibleOutputMap.put(index, outputList);
            index++;
        }
        List<Set<Location>> locationSetList = new ArrayList<>();
        for (int i = 0; i < possibleOutputMap.get(0).size(); i++) {
            Set<Location> locationSet = new HashSet<>();
            Location firstLocations = possibleOutputMap.get(0).get(i);
            locationSet.add(firstLocations);
            recursiveMethod(possibleOutputMap, locationSet, 1, locationSetList, inputList.size()); //STEP2
            log.info("** Filtered set " + locationSetList);
        }

        int solution =0;
        for (Set<Location> locationSet : locationSetList) {//STEP3: Final location set
            ColumnRowSum currColumnRowSum = getColumnRowSum(locationSet, sizeOfGrid);
            if (currColumnRowSum.equals(columnRowSum)) {
                solution++;
                System.out.println("================SOLUTION # "+ solution + "================");
                printOutput(inputList, sizeOfGrid, columnRowSum, locationSet);
            }
        }

        System.out.println("O - Aircraft.");
        System.out.println("X - Fuel truck.");
    }

    /**+
     * STEP4 - print output
     * @param inputList
     * @param sizeOfGrid
     * @param columnRowSum
     * @param locationSet
     */
    private void printOutput(List<Location> inputList, int sizeOfGrid, ColumnRowSum columnRowSum, Set<Location> locationSet) {
        List<Location> locationList = new ArrayList<>(locationSet);
        locationList.sort(Location::compareTo);

        List<Location> inputArrList = new ArrayList<>(inputList);
        inputArrList.sort(Location::compareTo);

        int k = 0;
        int l = 0;
        Location location = new Location(0, 0);
        Location aircraft = new Location(0, 0);

        for (int i = sizeOfGrid - 1; i >= 0; i--) {
            for (int j = 0; j < sizeOfGrid; j++) {
                System.out.print(" === ");
            }
            System.out.println();
            for (int j = 0; j < sizeOfGrid ; j++) {
                if (k < locationList.size()) {
                    location = locationList.get(k);
                }
                if(l<inputArrList.size()){
                    aircraft = inputArrList.get(l);
                }
                if (location.getX() == j && location.getY() == i) {
                    System.out.print("| X  ");
                    k++;
                } else if (aircraft.getX()==j && aircraft.getY()==i){
                    System.out.print("| O  ");
                    l++;
                }else {
                    System.out.print("|    ");
                }
            }
            System.out.println("| "+ columnRowSum.getySum().get(i));
        }
        for (int i = 0; i < sizeOfGrid; i++) {
            System.out.print(" === ");
        }
        System.out.println();
        for (int i = 0; i < sizeOfGrid; i++) {
            System.out.print("| "+columnRowSum.getxSum().get(i)+"  ");
        }
        System.out.println();
    }

    /**+
     * Print the input given as a grid for verification
     * @param inputList
     * @param sizeOfGrid
     * @param columnRowSum
     */
    private void printInput(List<Location> inputList, int sizeOfGrid,ColumnRowSum columnRowSum){
        int l = 0;
        Location aircraft = new Location(0, 0);
        List<Location> inputArrList = new ArrayList<>(inputList);
        inputArrList.sort(Location::compareTo);

        System.out.println("Verify the INPUT below");
        for (int i = sizeOfGrid - 1; i >= 0; i--) {
            for (int j = 0; j < sizeOfGrid; j++) {
                System.out.print(" === ");
            }
            System.out.println();
            for (int j = 0; j < sizeOfGrid ; j++) {

                if(l<inputArrList.size()){
                    aircraft = inputArrList.get(l);
                }
               if (aircraft.getX()==j && aircraft.getY()==i){
                    System.out.print("| O  ");
                    l++;
                }else {
                    System.out.print("|    ");
                }
            }
            System.out.println("| "+ columnRowSum.getySum().get(i));
        }
        for (int i = 0; i < sizeOfGrid; i++) {
            System.out.print(" === ");
        }
        System.out.println();
        for (int i = 0; i < sizeOfGrid; i++) {
            System.out.print("| "+columnRowSum.getxSum().get(i)+"  ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**+
     * STEP2 - get truck locations possible combinations
     * @param map
     * @param locationSet
     * @param index
     * @param locationSetList
     * @param inputSize
     * @return
     */
    private Set<Location> recursiveMethod(Map<Integer, List<Location>> map, Set<Location> locationSet,
                                          int index, List<Set<Location>> locationSetList, int inputSize) {
        log.info("Enter method recursiveMethod for index "+index);
        if (locationSet.isEmpty()) {
            return locationSet;
        }
        if (index == map.size()) {
            return locationSet;
        }
        for (int i = 0; i < map.get(index).size(); i++) {
            Location local = map.get(index).get(i);
            if (isValidLocationInSet(locationSet, local)) {
                locationSet.add(local);
                locationSet = recursiveMethod(map, locationSet, index + 1, locationSetList, inputSize);
                if (map.size() == index + 1) {
                    if (locationSet.isEmpty() || (locationSet.size() != inputSize)) {
                        locationSet.clear();
                        return locationSet;
                    }
                    locationSetList.add(new HashSet<>(locationSet));
                }
                locationSet.remove(local);

            } else {
                continue;
            }
        }
        return locationSet;
    }

    /**
     * Step1 - get all possible location of the truck for an aircraft
     *
     * @param input:      location of each aircraft
     * @param sizeOfGrid: size of the grid
     * @return: maximum 4 possible location of fuel truck
     */
    private List<Location> getAllPossibleLocations(Location input, Set<Location> set, int sizeOfGrid) {
        Location output;
        List<Location> outputList = new ArrayList<>();
        if (input.getX() - 1 != -1) {
            output = new Location(input.getX() - 1, input.getY());
            if (!set.contains(output)) {
                outputList.add(output);
            }
        }
        if (input.getX() + 1 < sizeOfGrid) {
            output = new Location(input.getX() + 1, input.getY());
            if (!set.contains(output)) {
                outputList.add(output);
            }
        }
        if (input.getY() - 1 != -1) {
            output = new Location(input.getX(), input.getY() - 1);
            if (!set.contains(output)) {
                outputList.add(output);
            }
        }
        if (input.getY() + 1 < sizeOfGrid) {
            output = new Location(input.getX(), input.getY() + 1);
            if (!set.contains(output)) {
                outputList.add(output);
            }
        }
        return outputList;
    }


    /**
     * Util method - to check a location is valid against a set of location
     *
     * @param locationSet
     * @param location
     * @return
     */
    private boolean isValidLocationInSet(Set<Location> locationSet, Location location) {
        log.info("Enter method isValidLocationInSet");
        for (Location local : locationSet) {
            if (location.equals(local) || location.areNeighbours(local)) {
                log.info(location + " is equal or neighbours with " + local);
                return false;
            }
        }
        log.info("Valid neighbours " + locationSet + " and " + location);
        return true;
    }

    /**
     * Util method - to get ColumnRowSum object from Set of locations.
     *
     * @param locationsSet
     */
    public ColumnRowSum getColumnRowSum(Set<Location> locationsSet, int sizeOfGrid) {
        //initialize
        ColumnRowSum columnRowSum = new ColumnRowSum();
        List<Integer> xSum = new ArrayList<>();
        List<Integer> ySum = new ArrayList<>();
        for (int i = 0; i < sizeOfGrid; i++) {
            xSum.add(0);
            ySum.add(0);
        }
        int x;
        int y;
        for (Location location : locationsSet) {
            x = location.getX();
            xSum.set(x, xSum.get(x) + 1);

            y = location.getY();
            ySum.set(y, ySum.get(y) + 1);
        }
        columnRowSum.setxSum(xSum);
        columnRowSum.setySum(ySum);

        return columnRowSum;
    }
}

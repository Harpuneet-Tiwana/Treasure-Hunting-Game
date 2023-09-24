import java.io.IOException; // import IOException

public class PathFinder {
    private Map pyramidMap; // declare private Map variable pyramidMap


    /**
     * @param fileName - initialized with the fileName String as parameter
     * @throws InvalidMapCharacterException - thrown if Map character is invalid
     * @throws IOException                  - throw if other type of exception
     */
    public PathFinder(String fileName) throws InvalidMapCharacterException, IOException { // constructor for Pathfinder
        pyramidMap = new Map(fileName);
    }


    /**
     * @return DLStack chamberDLStack - Stack that stores the proper chambers to travel during program execution
     */
    public DLStack path() {
        DLStack<Chamber> chamberDLStack = new DLStack<>(); // initialize the Stack

        Chamber bestChamberPath; // declare the bestChamberPath variable to represent the ideal chamber

        int numTreasures = pyramidMap.getNumTreasures(); // gets the number of treasure chambers stored in the map
        int N = 0; // initialize N, which keeps track of how many treasure chambers have been found

        chamberDLStack.push(pyramidMap.getEntrance()); // adds the entrance chamber to the Stack
        pyramidMap.getEntrance().markPushed(); // marks the entrance chamber

        Chamber currChamber;

        while (!chamberDLStack.isEmpty()) { // ensures the Stack has an entrance
            currChamber = chamberDLStack.peek();

            if (currChamber.isTreasure()) {
                N++; // increments N if a treasure chamber is found
                if (numTreasures == N) {
                    break; // break, since it means all treasure chambers were found
                }
            }

            bestChamberPath = bestChamber(currChamber); // sets bestChamberPath to the best chamber from bestChamber method

            if (bestChamberPath != null) { // ensures no NullPointerException
                chamberDLStack.push(bestChamberPath);
                bestChamberPath.markPushed(); // marks the pushed chamber
            } else {
                Chamber poppedChamber = chamberDLStack.pop();
                poppedChamber.markPopped(); // marks the popped chamber
            }
        }
        return chamberDLStack; // returns the stack
    }


    public Map getMap() {
        return pyramidMap; // returns pyramidMap variable
    }


    /**
     * @param currentChamber - the chamber of whose adjacent chambers we are checking
     * @return boolean - indicated if the chamber is dim or not
     */
    public boolean isDim(Chamber currentChamber) {

        if (currentChamber != null) {
            if (currentChamber.isSealed() || currentChamber.isLighted()) { // is chamber is sealed or lighted, means it is not dim
                return false;
            }
            for (int i = 0; i <= 5; i++) { // checks all adjacent chambers
                if (currentChamber.getNeighbour(i) != null && currentChamber.getNeighbour(i).isLighted()) {
                    return true;
                }
            }
        }
        return false; // returns false if neither above return statement is reached
    }


    /**
     * @param currentChamber - the current chamber of whose adjacent chambers we are looking at
     * @return the best chamber that the program should go to next
     */
    public Chamber bestChamber(Chamber currentChamber) {

        int litIndex = 6;
        int dimIndex = 6; // initialize 2 int variables which represent

        for (int i = 0; i <= 5; i++) {


            if (currentChamber.getNeighbour(i) != null && !currentChamber.getNeighbour(i).isMarked()) { // currentChamber.getNeighbour(i) != null
                // ensures no NullPointerException
                if (currentChamber.getNeighbour(i).isTreasure()) {
                    return currentChamber.getNeighbour(i); // return chamber immediately if treasure chamber is found

                } else if (currentChamber.getNeighbour(i).isLighted()) {
                    if (litIndex > i) {
                        litIndex = i; // ensures that the min index value is stored each time the loop iterates
                    }
                } else if (isDim(currentChamber.getNeighbour(i))) {
                    if (dimIndex > i) {
                        dimIndex = i; // ensures that the min index value is stored each time the loop iterates
                    }
                }
            }
        }


        if (litIndex == 6 && dimIndex == 6) {
            return null; // return null since no proper chamber was found
        } else {

            if (litIndex != 6) {
                return currentChamber.getNeighbour(litIndex); // return Chamber at certain index
            } else if (dimIndex != 6) {
                return currentChamber.getNeighbour(dimIndex); // return Chamber at certain index
            } else {
                return null; // return null if no other conditions satisfied
            }
        }
    }

}

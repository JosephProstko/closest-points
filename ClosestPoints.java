import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;

/**
 * The ClosestPoints() class creates an object which can scroll
 * through a list of points and return the two closest points.
 *
 * @author Joseph Prostko
 * @version April 2019
 */
public class ClosestPoints {

    // long starter holds the value where the first clock to determine
    // how long it takes to read in the file starts.
    long starter;

    // ArrayList XOrder holds the values of the array in order of x points.
    private static ArrayList XOrder;

    // ArrayList YOrder holds the values of the array in order of y points.
    private static ArrayList YOrder;

    /**
     * The inner class compareX implements Comparator and organizes the XOrder List
     * in order of x coordinates.
     */
    class compareX implements Comparator<Point.Double> {

        // The compare method in Comparator is overrode.
        @Override
        public int compare(Point.Double o1, Point.Double o2) {
            // If o1.x < o2.x, -1 is returned.
            if (o1.x < o2.x) {
                // -1 is returned.
                return -1;
                // Else if o1.x > o2.x, 1 is returned.
            } else if (o1.x > o2.x) {
                // 1 is returned.
                return 1;
                // else, 0 is returned.
            } else {
                // 0 is returned.
                return 0;
            }
        }
    }

    /**
     * The inner class compareY implements Comparator and organizes the YOrder List
     * in order of y coordinates.
     */

    class compareY implements Comparator<Point.Double> {

        // The compare method in Comparator is overrode.
        @Override
        public int compare(Point.Double o1, Point.Double o2) {
            // If o1.y < o2.y, -1 is returned.
            if (o1.y < o2.y) {
                // -1 is returned.
                return -1;
                // Else if o1.y > o2.y, 1 is returned.
            } else if (o1.y > o2.y) {
                // 1 is returned.
                return 1;
                // else, 0 is returned.
            } else {
                // 0 is returned.
                return 0;
            }
        }
    }

    /**
     * The constructor ClosestPoints() instantiates each of the two
     * ArrayLists and reads in the data from the assigned file, using
     * the designated string.
     */
    public ClosestPointsOld(String temp) {
        // XOrder is instantiated as a new ArrayList of Point.Doubles.
        XOrder = new ArrayList<Point.Double>();

        // YOrder is instantiated as a new ArrayList of Point.Doubles.
        YOrder = new ArrayList<Point.Double>();

        // readData is called searching for the specified file name.
        readData(temp);
    }

    /**
     * readData reads in the data from the specified file as a list of Points.
     *
     * @param tempTitle the name of the file being read from.
     */
    public void readData(String tempTitle) {

        // long start holds the time the reading in strats using nanoTime().
        long start = System.nanoTime();

        // Scanner file is declared.
        Scanner file = null;

        // The try catch statement searches for FileNotFoundException errors
        // and handles them accordingly.
        try {
            // file in instantiated with the designated file name from tempTitle.
            file = new Scanner(new File(tempTitle));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // The while loops reads in data from file as long as their is still data remianing.
        while (file.hasNext()) {

            // double x holds the next value from file as a double.
            double x = file.nextDouble();

            // double y holds the next value from file as a double.
            double y = file.nextDouble();

            // Point.Double temp is instantiated as a new Point.Double
            Point.Double temp = new Point.Double(x, y);

            // temp is added to XOrder.
            XOrder.add(temp);

            // temp is added to YOrder.
            YOrder.add(temp);

        }

        // XOrder is organized by order of x coordinate using compareX().
        XOrder.sort(new compareX());

        // YOrder is organized by order of y coordinate using compareY().
        YOrder.sort(new compareY());

        // long end holds the value that the file stopped being read in.
        long end = System.nanoTime();

        // starter holds the maximum number of time the file took to be read in.
        starter = end - start;

    }

    /**
     * The distance method returns the value of the distance between two points
     * in a line.
     *
     * @param line the line being scanned to find the distance between the two points.
     * @return the distance between the two points.
     */
    public static double distance(Line2D.Double line) {
        // total uses the standard distance formula to determine the distance between the two points.
        double total = Math.sqrt(Math.pow((line.x2 - line.x1), 2) + Math.pow((line.y2 - line.y1), 2));

        // The total distance between these points are returned.
        return total;
    }

    /**
     * toPointArray converts an ArrayList of points into an array of points.
     *
     * @param temp The ArrayList being converted into an array.
     * @return The value of temp as an array of points.
     */
    public static Point.Double[] toPointArray(ArrayList<Point.Double> temp) {
        // total is instantiated as a Point.Double() array with the size of temp.
        Point.Double[] total = new Point.Double[temp.size()];

        // Iterator it is instantiated as a new iterator of temp.
        Iterator it = temp.iterator();

        // int i is instantiated as 0.
        int i = 0;

        // The while loop reads in each point from it.
        while (it.hasNext()) {
            // temporary is instantiated as the value of it.next() cast as a Point.Double.
            Point.Double temporary = (Point.Double) it.next();

            // total[i] is instantiated as a new Point.Double with the x and y values of temporary.
            total[i] = new Point.Double(temporary.x, temporary.y);

            // i increments.
            i++;
        }

        // The finished total array is returned.
        return total;
    }

    /**
     * The helper method determineLength() calls upon determineLength using the designated
     * XOrder and YOrder values to search through points.
     *
     * @return The pair of points with the shortest distance apart.
     */
    public static Line2D.Double determineLength() {
        // determineLength is called upon using the values of XOrder and YOrder.
        return determineLength(toPointArray(XOrder), toPointArray(YOrder));
    }

    /**
     * The determineLength method uses recursion to break down to large arrays of
     * points and search for the closest pair of points.
     *
     * @param XArray the first copy of the array of points in order of x coordinate.
     * @param YArray the second copy of the array of points in order of y coordinate.
     * @return The closest pair of points in the two arrays as a 2D Line.
     */
    public static Line2D.Double determineLength(Point.Double[] XArray, Point.Double[] YArray) {
        // The line total holds the current pair of closest points.
        Line2D.Double total = new Line2D.Double();

        // If XArray.length > 5, it is recursively broken down into smaller arrays.
        if (XArray.length > 5) {
            // XLArray will contain each value less than the median coordinate in XArray.
            Point.Double[] XLArray = new Point.Double[(int) Math.ceil(XArray.length / 2.0)];

            // XRArray will contain each value greater than the median coordinate in XArray.
            Point.Double[] XRArray = new Point.Double[(int) Math.floor(XArray.length / 2.0)];

            // YLArray will contain each value lesser than the median coordinate in YArray.
            Point.Double[] YLArray = new Point.Double[XLArray.length];

            // YRArray will contain each value greater than the median coordinate in YArray.
            Point.Double[] YRArray = new Point.Double[XRArray.length];

            // This for loop reads in each value of XLArray from XArray..
            for (int i = 0; i < ((int) Math.ceil(XArray.length / 2.0)); i++) {
                XLArray[i] = XArray[i];
            }

            // This for loop reads in each value of XRArray from XArray.
            for (int i = 0; i < ((int) Math.floor(XArray.length / 2.0)); i++) {
                XRArray[i] = XArray[i + ((int) Math.floor(XArray.length / 2.0))];
            }

            // This for loop reads in each value of YLArray from XLArray.
            for (int i = 0; i < XLArray.length; i++) {
                YLArray[i] = XLArray[i];
            }

            // This for loop reads in each value of YRArray from XRArray.
            for (int i = 0; i < XRArray.length; i++) {
                YRArray[i] = XRArray[i];
            }

            // tempOne is set equal to the returned line from the recursive call determineLength(XLArray, YLArray).
            Line2D.Double tempOne = determineLength(XLArray, YLArray);

            // tempTwo is set equal to the returned line from the recursive call determineLength(XRArray, YRArray).
            Line2D.Double tempTwo = determineLength(XRArray, YRArray);

            // The if statement below determines if the new coordinate pair from tempOne has closer points than total or if
            // distance(total) is equal to 0.0.
            if (distance(tempOne) < distance(total) || distance(total) == 0.0) {
                // If so, total is set as a new line with the values of tempOne.
                total = new Line2D.Double(tempOne.getP1(), tempOne.getP2());
            }

            // The if statement below determines if the new coordinate pair from tempTwo has closer points than total.
            if (distance(tempTwo) < distance(total)) {
                total = new Line2D.Double(tempTwo.getP1(), tempTwo.getP2());
            }

            // If the XArray and YArray lengths are less than 5, then the else base case is used.
        } else {

            // The outer for loop reads across each point in XArray.
            for (int i = 0; i < XArray.length; i++) {

                // The inner for loop reads across each point in YArray.
                for (int j = 0; j < YArray.length; j++) {

                    // This if statement makes sure that two points are not compared that are the same point.
                    if (!XArray[i].equals(YArray[j])) {

                        // temporary holds the distance value of a line with the values of XArray[i]
                        // and YArray[j].
                        double temporary = distance(new Line2D.Double(XArray[i], YArray[j]));

                        // The if statement below determines if the value of temporary distance
                        // is less than distance(total) or if the distance of total equals 0.
                        if (temporary < distance(total) || distance(total) == 0.0) {

                            // If so, total is set equal to a new line with the values
                            // of XArray[i] and YArray[j].
                            total = new Line2D.Double(XArray[i], YArray[j]);
                        }
                    }
                }
            }
        }

        // The value of total is returned.
        return total;
    }

    /**
     * The main method creates a new ClosestPoints object and uses it to find
     * the closest pair of points from the specified file.
     * @param args the arguments needed for finding the closest points.
     */
    public static void main(String[] args) {
        // ClosestPoints totalOne holds the data from the specified file being read into ClosestPoints.
        ClosestPoints totalOne = new ClosestPoints("points.10000.dat");

        // long startOne holds the time which the determineLength process starts.
        long startOne = System.nanoTime();

        // totalLineOne holds the value of the closest pair of points from totalOne.
        Line2D.Double totalLineOne = new Line2D.Double(totalOne.determineLength().getP1(), totalOne.determineLength().getP2());

        // endOne holds the time which the determineLength process ends.
        long endOne = System.nanoTime();

        // totalTimeOne holds the total duration time of totalOne.determineLength.
        long totalTimeOne = endOne - startOne;

        // distanceOnce is set equal to the distance value of totalLineOne.
        double distanceOne = distance(totalLineOne);

        // The print statement below displays the total time to read the file in totalOne in seconds.
        System.out.println("Time to read file: " + totalOne.starter / 1000000000.0);

        // The print statement below displays the total time to execute totalOne.determineLength in seconds.
        System.out.println("Time: " + totalTimeOne / 1000000000.0);

        // The print statement below prints the first point in the pair of coordinates in totalLineOne.
        System.out.println("(" + totalLineOne.x1 + ", " + totalLineOne.y1 + ")");

        // The print statement below prints the second point in the pair of coordinates in totalLineOne.
        System.out.println("(" + totalLineOne.x2 + ", " + totalLineOne.y2 + ")");

        // The print statement below prints the distance of the two points in totalLineOne.
        System.out.println("Distance = " + distanceOne);
    }
}

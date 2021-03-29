package ml_6002b_coursework;

/**
 * Empty class for PArt 2.1 of the coursework
 *
 * Implement a class called AttributeMeasures in the package ml 6002b coursework that contains four static methods,
 * each of which measures the quality of an attribute split at a node.
 * They should all take a two dimensional array of integers as an argument and return a double.
 * You can assume that the rows represent different values of the attribute being assessed, and
 * the columns the class counts.
 *
 * The formal definitions for information gain, gini and chi-squared are given in the lecture on
 * Decision Trees, and I want you to follow these formula. The Yates correction is a very simple
 * modification. Your methods should handle all possible inputs without crashing. Comment the
 * code to indicate how you have dealt with any edge cases.
 */
public class AttributeMeasures {

    public static double measureInformationGain(int[][] split) {
        // measureInformationGain returns the information gain for the contingency table
        return 0;
    }

    public static double measureGini(int[][] split) {
        // measureGini returns the gini measure for the contingency table
        return 0;
    }

    public static double measureChiSquared(int[][] split) {
        // measureChiSquared returns the chi-squared statistic for the contingency table
        return 0;
    }

    public static double measureChiSquaredYates(int[][] split) {
        // measureChiSquaredYates returns the chi-squared statistic after applying the Yates correction.
        // We did not cover this feature in the lecture, so it is up to you to find out how to do it.
        return 0;
    }

    public void printHeadacheSplit(String cls){
        System.out.println("measure " + getClass().getSimpleName() + " for headache splitting diagnosis = " + cls);
    }

    public void printSplit(String attribute, String cls){
        System.out.println("measure " + getClass().getSimpleName() + " for attribute" + attribute + " splitting diagnosis = " + cls);
    }

    /**
     Include a main method test harness that tests each function by finding each measure
     for the attribute headache in terms of the diagnosis.
     Print each measure to the console, in the form
     “measure <insert> for headache splitting diagnosis = <insert>”.
     **/
    public static void main(String[] args) {
        /**
         * Include a main method test harness
         * that tests each function by finding each measure for the attribute headache in terms of the
         * diagnosis. Print each measure to the console, in the form
         * “measure <insert> for headache splitting diagnosis = <insert>”.
         * **/

    }

}

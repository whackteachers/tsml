package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static experiments.data.DatasetLoading.loadData;
import static java.lang.Double.max;

/**
 * Empty class for PArt 2.1 of the coursework
 *
 * Implement a class called AttributeMeasures in the package ml 6002b coursework that contains four static methods,
 * each of which measures the quality of an attribute split at a node.
 * They should all take a two dimensional array of integers as an argument and return a double.
 * You can assume that the rows represent different values of the attribute being assessed, and
 * the columns the class counts.
 *
 *             |   class 0 count   |   class 1 count   |
 *  att_val 1  |                   |                   |
 *         ... |                   |                   |
 *  att_val n  |                   |                   |
 *
 * The formal definitions for information gain, gini and chi-squared are given in the lecture on
 * Decision Trees, and I want you to follow these formula. The Yates correction is a very simple
 * modification. Your methods should handle all possible inputs without crashing. Comment the
 * code to indicate how you have dealt with any edge cases.
 */
public class AttributeMeasures {

    private static double entropy(int[][] split) throws Exception {
        // adapted and modified from weka id3 computeEntropy method
        double entropy = 0, count = 0;
        for (int[] attribute : split) {
            for (int classCount : attribute) {
                if (classCount > 0) {
                    entropy -= classCount * Utils.log2(classCount);
                    count += classCount;
                }
            }
        }

        entropy /= count;
        return entropy + Utils.log2(count);
    }

    public static double measureInformationGain(int[][] root, int[][] split) throws Exception {
        // measureInformationGain returns the information gain for the contingency table
        int[] temp = new int[split[0].length];
        for (int i = 0; i < split.length; i++) {
            for (int j = 0; j < split[0].length; j++) {
                temp[i] += split[i][j];
            }
        }
        double infoGain = entropy(root), count = 0, sum = 0;
//        Instances[] splitData = splitData(split, att);
        for (int[] attributeValue : split) {
            int attributeValueCount = Arrays.stream(attributeValue).sum();
            if (attributeValueCount > 0){
                sum += (double) attributeValueCount * entropy(new int[][]{attributeValue});
                count += attributeValueCount;
            }
        }
        infoGain -= sum / count;
        return infoGain;
    }

    private static double impurity(int[][] split) throws Exception {
        // adapted and modified from weka id3 computeEntropy method
        double impurity = 0, count = 0;
        for (int[] attribute : split) {
            for (int classCount : attribute) {
                if (classCount > 0) {
                    impurity += classCount * classCount;
                    count += classCount;
                }
            }
        }

        impurity = 1 - impurity/(count * count);
        return impurity;
    }

    public static double measureGini(int[][] root, int[][] split) throws Exception {
        // measureGini returns the gini measure for the contingency table
        double gini = impurity(root), count = 0, sum = 0;
//        Instances[] splitData = splitData(split, att);
        for (int[] attributeValue : split) {
            int attributeValueCount = Arrays.stream(attributeValue).sum();
            if (attributeValueCount > 0){
                sum += (double) attributeValueCount * impurity(new int[][]{attributeValue});
                count += attributeValueCount;
            }
        }
        gini -= sum / count;
        return gini;
    }
    private static double chiSquared(int[][] split, boolean yates){
        double chival = 0, n = 0;

        int nrows = split.length;
        int ncols = split[0].length;
        int df = (nrows - 1)*(ncols - 1);
        if (df <= 0) {
            return 0;
        }
        double[] rtotal = new double [nrows];
        double[] ctotal = new double [ncols];

        for (int row = 0; row < nrows; row++) {
            for (int col = 0; col < ncols; col++) {
                int classCount = split[row][col];
                rtotal[row] += classCount;
                ctotal[col] += classCount;
                n += classCount;
            }
        }

        for (int row = 0; row < nrows; row++) {
            if (rtotal[row] > 0) {
                for (int col = 0; col < ncols; col++) {
                    if (ctotal[col] > 0) {
                        double expected = rtotal[row] * (ctotal[col] / n);
                        // Compute difference between observed and expected value
                        double diff = Math.abs(split[row][col] - expected);
                        diff = yates ? max(diff - 0.5, 0) : diff;
                        chival += diff * diff / expected;
                    }
                }
            }
        }
        return chival;
    }
    public static double measureChiSquared(int[][] split) {
        // measureChiSquared returns the chi-squared statistic for the contingency table
        return chiSquared(split, false);
    }

    public static double measureChiSquaredYates(int[][] split) {
        // measureChiSquaredYates returns the chi-squared statistic after applying the Yates correction.
        // We did not cover this feature in the lecture, so it is up to you to find out how to do it.
        // Apply Yates' correction if wanted
        return chiSquared(split, true);
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
    public static void main(String[] args) throws Exception {
//        Instances data = loadData("./test_data/Diagnosis.arff");
//        Attribute headache = data.attribute("Headache");
//        Instances[] splitData = splitData(data, headache);
//        int[][] table = new int[headache.numValues()][data.numClasses()];
//        for (int i = 0; i < headache.numValues(); i++) {
//            for (Instance instance : splitData[i]) {
//                int value = (int) instance.classValue();
//                table[i][value]++;
//            }
//        }
        int[][] test = new int[][]{{6,6}};
        int[][] testSplit = new int[][]{{3,2}, {3,4}};
        double entropy = AttributeMeasures.entropy(test);
        HashMap<String, Double> measures = new HashMap<>();

        measures.put("information gain", measureInformationGain(test, testSplit));
        measures.put("gini", measureGini(test, testSplit));
        measures.put("chi squared", measureChiSquared(testSplit));
        measures.put("chi squared yates", measureChiSquaredYates(testSplit));
        System.out.println(entropy);
        measures.forEach(
            (measure, value) ->
            System.out.println("measure " + measure +
            " for headache splitting diagnosis = " + value)
        );
    }

}

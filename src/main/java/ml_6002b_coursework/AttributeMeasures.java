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

    private static double entropy(int[] split) throws Exception {
        // adapted and modified from weka id3 computeEntropy method
        double entropy = 0, count = 0;
        for (int classCount : split) {
            if (classCount > 0) {
                entropy -= classCount * Utils.log2(classCount);
                count += classCount;
            }
        }

        entropy /= count;
        return entropy + Utils.log2(count);
    }
// 2 5  - 7
// 3 3     - 6
    public static double measureInformationGain(int[][] split) throws Exception {
        // measureInformationGain returns the information gain for the contingency table
        if (split == null || split.length == 0 || split[0].length == 0)
            return 0;

        int[] root = new int[split[0].length];
        for (int[] attributeValue : split) {
            for (int j = 0; j < split[0].length; j++) {
                root[j] += attributeValue[j];
            }
        }
        double infoGain = entropy(root), count = 0, sum = 0;
        for (int[] attributeValue : split) {
            double attributeValueCount = Arrays.stream(attributeValue).sum();
            if (attributeValueCount > 0){
                sum += attributeValueCount * entropy(attributeValue);
                count += attributeValueCount;
            }
        }
        infoGain -= sum / count;
        return infoGain;
    }

    private static double impurity(int[] split) throws Exception {
        // adapted and modified from weka id3 computeEntropy method
        double impurity = 0, count = 0;
        for (int classCount : split) {
            if (classCount > 0) {
                impurity += classCount * classCount;
                count += classCount;
            }
        }

        impurity = 1 - impurity/(count * count);
        return impurity;
    }

    public static double measureGini(int[][] split) throws Exception {
        // measureGini returns the gini measure for the contingency table
        if (split == null || split.length == 0 || split[0].length == 0)
            return 0;

        int[] root = new int[split[0].length];
        for (int[] attributeValue : split) {
            for (int j = 0; j < split[0].length; j++) {
                root[j] += attributeValue[j];
            }
        }
        double gini = impurity(root), count = 0, sum = 0;
        for (int[] attributeValue : split) {
            double attributeValueCount = Arrays.stream(attributeValue).sum();
            if (attributeValueCount > 0){
                sum += attributeValueCount * impurity(attributeValue);
                count += attributeValueCount;
            }
        }
        gini -= sum / count;
        return gini;
    }
    private static double chiSquared(int[][] split, boolean yates){
        double chi = 0, n = 0;

        int attributeValues = split.length;
        int classes = split[0].length;
        if (attributeValues <= 1 && classes <= 1 || split[0].length == 0)
            return 0;

        double[] valueTotals = new double [attributeValues];
        double[] classTotals = new double [classes];

        for (int row = 0; row < attributeValues; row++) {
            for (int col = 0; col < classes; col++) {
                int classCount = split[row][col];
                valueTotals[row] += classCount;
                classTotals[col] += classCount;
                n += classCount;
            }
        }

        for (int row = 0; row < attributeValues; row++) {
            if (valueTotals[row] > 0) {
                for (int col = 0; col < classes; col++) {
                    if (classTotals[col] > 0) {
                        double expected = valueTotals[row] * (classTotals[col] / n);
                        double diff = Math.abs(split[row][col] - expected);
                        diff = yates ? max(diff - 0.5, 0) : diff;
                        chi += diff * diff / expected;
                    }
                }
            }
        }
        return chi;
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

    /**
     Include a main method test harness that tests each function by finding each measure
     for the attribute headache in terms of the diagnosis.
     Print each measure to the console, in the form
     “measure <insert> for headache splitting diagnosis = <insert>”.
     **/
    public static void main(String[] args) throws Exception {
        int[][] headacheSplit = new int[][]{{3,2}, {3,4}};
        HashMap<String, Double> measures = new HashMap<>();

        measures.put("information gain", measureInformationGain(headacheSplit));
        measures.put("gini", measureGini(headacheSplit));
        measures.put("chi squared", measureChiSquared(headacheSplit));
        measures.put("chi squared yates", measureChiSquaredYates(headacheSplit));
        measures.forEach(
            (measure, value) ->
            System.out.println("measure " + measure +
            " for headache splitting diagnosis = " + value)
        );
    }

}

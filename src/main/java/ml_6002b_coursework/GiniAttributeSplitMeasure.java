package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

public class GiniAttributeSplitMeasure implements AttributeSplitMeasure{
    /**
     * Implement and test a class GiniAttributeSplitMeasure that implements
     * AttributeSplitMeasure and measures the quality using the Gini index statistic.
     * **/

    private double computeImpurity(Instances data){
        double [] classCounts = new double[data.numClasses()];
        for (Instance inst : data)
            classCounts[(int) inst.classValue()]++;

        double impurity = 1, numInstances = data.numInstances();
        for (double classCount : classCounts) {
            if (classCount > 0) {
                double p = classCount / numInstances;
                impurity -= p * p;
            }
        }
        return impurity;
    }

    @Override
    public double computeAttributeQuality(Instances data, Attribute att){
        double gini = computeImpurity(data), numInstances = data.numInstances();
        Instances[] splitData = splitData(data, att);
        for (Instances split : splitData) {
            double splitInstances = split.numInstances();
            if (splitInstances > 0) {
                gini -= (splitInstances / numInstances) *
                        computeImpurity(split);
            }
        }
        return gini;
    }

    public static void main(String[] args) throws Exception {
        /**
         * Include a main method in all three AttributeSplitMeasure classes that prints out the split criteria
         * value for Headache, Spots and Stiff Neck for the data from Section 1 for the whole data. Print
         * in the form “measure <insert> for attribute <insert> splitting diagnosis = <insert>”.
         * **/
        GiniAttributeSplitMeasure gini = new GiniAttributeSplitMeasure();
        gini.testMain("gini");
    }
}

package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instances;

public class ChiSquaredAttributeSplitMeasure implements AttributeSplitMeasure{
    /**
     * Implement and test a class ChiSquaredAttributeSplitMeasure that implements
     * AttributeSplitMeasure and measures the quality using the chi-squared statistic. This
     * class should be configurable to use the Yates correction.
     * **/

    @Override
    public double computeAttributeQuality(Instances data, Attribute att) throws Exception {
        return 0;
    }

    public static void main(String[] args) {
        /**
         * Include a main method in all three AttributeSplitMeasure classes that prints out the split criteria
         * value for Headache, Spots and Stiff Neck for the data from Section 1 for the whole data. Print
         * in the form “measure <insert> for attribute <insert> splitting diagnosis = <insert>”.
         * **/

    }
}

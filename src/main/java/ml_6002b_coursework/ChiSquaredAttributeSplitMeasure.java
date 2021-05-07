package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import static ml_6002b_coursework.AttributeMeasures.measureChiSquared;
import static ml_6002b_coursework.AttributeMeasures.measureChiSquaredYates;

public class ChiSquaredAttributeSplitMeasure implements AttributeSplitMeasure{
    /**
     * Implement and test a class ChiSquaredAttributeSplitMeasure that implements
     * AttributeSplitMeasure and measures the quality using the chi-squared statistic. This
     * class should be configurable to use the Yates correction.
     * **/

    boolean yates = false;

    public ChiSquaredAttributeSplitMeasure(boolean yates) {
        this.yates = yates;
    }

    @Override
    public double computeAttributeQuality(Instances data, Attribute att) throws Exception {
        if (data.numInstances() == 0)
            return 0;
        Instances[] splitData = splitData(data, att);
        int numValues = att.isNominal() ? att.numValues() : splitData.length;
        int[][] table = new int[numValues][data.numClasses()];
        for (int i = 0; i < att.numValues(); i++) {
            for (Instance instance : splitData[i]) {
                int value = (int) instance.classValue();
                table[i][value]++;
            }
        }

        return yates ? measureChiSquaredYates(table) : measureChiSquared(table);
    }

    @Override
    public String toString() {
        return "chi squared" + (yates ? " yates" : "");
    }

    public static void main(String[] args) throws Exception {
        /**
         * Include a main method in all three AttributeSplitMeasure classes that prints out the split criteria
         * value for Headache, Spots and Stiff Neck for the data from Section 1 for the whole data. Print
         * in the form “measure <insert> for attribute <insert> splitting diagnosis = <insert>”.
         * **/

        ChiSquaredAttributeSplitMeasure chiSquared = new ChiSquaredAttributeSplitMeasure(false);
        chiSquared.testMain();
    }
}

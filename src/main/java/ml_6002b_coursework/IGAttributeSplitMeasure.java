package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import java.io.Serializable;

public class IGAttributeSplitMeasure implements AttributeSplitMeasure{
    /**
     * Implement and test the skeleton class IGAttributeSplitMeasure so that the split is
     * performed using information gain. I strongly advise you to look at how the original class
     * Id3 does this, and you have my permission to copy the code from Id3, but if you do
     * please attribute it in the comments.
     * **/

    private double computeEntropy(Instances data){
        // adapted and modified from Id3 computeEntropy method
        double [] classCounts = new double[data.numClasses()];
        for (Instance inst : data)
            classCounts[(int) inst.classValue()]++;

        double entropy = 0, numInstances = data.numInstances();
        for (double classCount : classCounts) {
            if (classCount > 0) {
                entropy -= classCount * Utils.log2(classCount);
            }
        }
        entropy /= numInstances;
        return entropy + Utils.log2(numInstances);
    }

    @Override
    public double computeAttributeQuality(Instances data, Attribute att){
        // adapted and modified from Id3 computeEntropy method
        double infoGain = computeEntropy(data);
//        Instances[] splitData = null;
//        if (att.isNominal())
//            splitData = splitData(data, att);
//        else if (att.isNumeric()){
//            splitData = splitDataOnNumeric(data, att).getKey();
//        }
        Instances[] splitData = splitData(data, att);
        for (Instances split : splitData) {
            double splitInstances = split.numInstances();
            if (splitInstances > 0) {
                infoGain -= (splitInstances / data.numInstances()) *
                            computeEntropy(split);
            }
        }
        return infoGain;
    }

    @Override
    public String toString() {
        return "information gain";
    }

    public static void main(String[] args) throws Exception {
        /**
         * Include a main method in all three AttributeSplitMeasure classes that prints out the split criteria
         * value for Headache, Spots and Stiff Neck for the data from Section 1 for the whole data. Print
         * in the form “measure <insert> for attribute <insert> splitting diagnosis = <insert>”.
         * **/
        IGAttributeSplitMeasure ig = new IGAttributeSplitMeasure();
        ig.testMain();
    }
}

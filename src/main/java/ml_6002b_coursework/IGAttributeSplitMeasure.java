package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

import java.util.Enumeration;

public class IGAttributeSplitMeasure implements AttributeSplitMeasure{
    /**
     * Implement and test the skeleton class IGAttributeSplitMeasure so that the split is
     * performed using information gain. I strongly advise you to look at how the original class
     * Id3 does this, and you have my permission to copy the code from Id3, but if you do
     * please attribute it in the comments.
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

package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.io.Serializable;
import java.util.*;

import static experiments.data.DatasetLoading.loadData;

/**
 * Interface for alternative attribute split measures for Part 2.2 of the coursework
 *
 */
public interface AttributeSplitMeasure extends Serializable {

    double computeAttributeQuality(Instances data, Attribute att) throws Exception;

    /**
     * Currently, ID3Coursework can only be used with nominal attributes. Implement a method
     * in AttributeSplitMeasure called splitDataOnNumeric that randomises the mechanism for
     * handling continuous attributes. This should involve selecting a random attribute
     * value between the minimum and maximum for that attribute, then making a binary split
     * of instances into those below the value and those above the value. This should be done
     * prior to measuring the attribute quality.
     *
     * @return*/
    default Map.Entry<Instances[], Double> splitDataOnNumeric(Instances data, Attribute att){
        // get the split value from the minimum and maximum for the attribute
        Random random= new Random();
        double min= data.kthSmallestValue(att, 1);
        double max= data.kthSmallestValue(att, data.size());
        double randomSplitValue = min >= max ? random.doubles(min, max).findFirst().getAsDouble() : min;

        // make binary split where 0 is below and 1 is above the split value
        Instances[] splitData = new Instances[2];
        for (int i = 0; i < 2; i++)
            splitData[i] = new Instances(data, data.numInstances());

        for (Instance inst : data)
            splitData[inst.value(att) < randomSplitValue ? 0 : 1].add(inst);

        return Collections.singletonMap(splitData, randomSplitValue).entrySet().iterator().next();
    }

    /**
     * Splits a dataset according to the values of a nominal attribute.
     *
     * @param data the data which is to be split
     * @param att the attribute to be used for splitting
     * @return the sets of instances produced by the split
     */
     default Instances[] splitData(Instances data, Attribute att){
        if (att.isNumeric())
            return splitDataOnNumeric(data, att).getKey();
        Instances[] splitData = new Instances[att.numValues()];
        for (int j = 0; j < att.numValues(); j++) {
            splitData[j] = new Instances(data, data.numInstances());
        }
        Enumeration instEnum = data.enumerateInstances();
        while (instEnum.hasMoreElements()) {
            Instance inst = (Instance) instEnum.nextElement();
            splitData[(int) inst.value(att)].add(inst);
        }
        for (int i = 0; i < splitData.length; i++) {
            splitData[i].compactify();
        }
        return splitData;
    }

    default void testMain() throws Exception {
        Instances data = loadData("./test_data/Diagnosis.arff");
        String[] attributes = {"Headache", "Spots", "StiffNeck"};
        for (String attribute : attributes) {
            System.out.println(
                "measure " + this +
                " for attribute " + attribute +
                " splitting diagnosis = " +
                this.computeAttributeQuality(data, data.attribute(attribute))
            );
        }
    }
}


package ml_6002b_coursework;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

public class TreeEnsemble extends AbstractClassifier {
    /**
     * The task is to implement an Ensemble classifier that can be used with variants of your enhanced
     * ID3 classifier. You must implement this classifier from scratch rather than use any existing
     * classifiers in tsml. Implement a classifier, TreeEnsemble, that extends AbstractClassifier and
     * consists of an ensemble of ID3Coursework classifiers stored in an array or List. Set the default
     * ensemble size to 50. TreeEnsemble should be in the package ml_6002b_coursework.
     * **/
    @Override
    public void buildClassifier(Instances data) throws Exception {
        /**
         * The method buildClassifier should construct a new set of instances for each element
         * of the ensemble by selecting a random subset (without replacement) of the attributes.
         * The proportion of attributes to select should be a parameter (defaults to 50%). It should
         * then build a separate classifier on each Instances object. The TreeEnsemble will need
         * to store which attributes are used with which classifier in order to recreate the attribute
         * selections in classifyInstance and distributionForInstance.
         *
         * Further diversity should be injected into the ensemble by randomising the decision tree
         * parameters. This diversification should include attribute selection mechanisms you have
         * implemented, but also can involve other tree parameters.
         *
         * Implement an option where rather than counting votes, the ensemble has the option of
         * averaging probability distributions of the base classifiers.
         * **/
    }

    @Override
    public double classifyInstance(Instance instance) throws Exception {
        /**
         * Implement classifyInstance so that it returns the majority vote of the ensemble. i.e.,
         * classify a new instance with each of the decision trees, count how many classifiers predict
         * each class value, then return the class that receives the largest number of votes.
         * **/
        return 0;
    }

    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        // Implement distributionForInstance so that it returns the proportion of votes for
        // each class.
        return new double[0];
    }

    @Override
    public Capabilities getCapabilities() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        /**
         * Implement a main method in the class TreeEnsemble that loads the problem optdigits,
         * prints out the test accuracy but also prints out the probability estimates for the first five
         * test cases.
         * **/
        TreeEnsemble ensemble = new TreeEnsemble();
    }


}

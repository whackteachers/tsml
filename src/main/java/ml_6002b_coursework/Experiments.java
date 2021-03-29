package ml_6002b_coursework;

public class Experiments {
    /**
     * Your task is to perform a series of classification experiments and write them up as a research
     * paper. Your experiments will address the question of whether the variations you have implemented
     * for ID3 improve performance over a range of problems and whether a your ensemble
     * is a good approach for a specific case study data set. You have been assigned a specific data
     * set (see blackboard) and a link to further information. Please note that the for this part of the
     * coursework we mark the paper itself, not the code used to generate the results. We advise you
     * reserve significant time for reading and checking your paper, and recommend you ask someone else
     * to read it before submission. Imagine you are writing a paper for a wide audience,
     * not just for the marker. Aim for a tight focus on the specific questions the paper addresses.
     * We have provided two sets of datasets to perform these experiments: these are in files
     * UCIDisrete.zip and UCIContinuous.zip. A list of the problems in these files is given in the otherwise
     * empty class DatasetLists.java. You will be assigned a completely different problem from
     * timeseriesclassification.com for the case study.
     * There are four issues to investigate:
     *
     * 1. Decision Trees: Test whether there is any difference in average accuracy for the attribute
     * selection methods on the classification problems we have provided.
     * Compare your versions of ID3 to the Weka ID3 and J48 classifiers.
     *
     * 2. Tree Ensemble vs Tree Tuning: Test whether tuning ID3Coursework, including choosing
     * attribute selection criteria method, is better than ensembling. It is up to you to select
     * ranges of values to tune and ensemble over, but you should ensure that the range of
     * parameters you tune over is the same as those you use to ensemble.
     * Perform this experiment with the proportion of attributes set to 100%, then repeat the experiment with the
     * proportion set to 50%. You can use any code available in tsml to help you do the tuning.
     *
     * 3. Compare your ensemble against a range of built in Weka classifiers,
     * including other ensembles, on the provided classification problems.
     * We recommend you include random forest and rotation forest in the list.
     *
     * 4. Perform a case study on your assigned data set to propose which classifier from those
     * you have used would be best for this particular problem.
     * **/
    public static void main(String[] args) {

    }
}

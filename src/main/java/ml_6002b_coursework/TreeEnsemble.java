package ml_6002b_coursework;

import evaluation.tuning.ParameterSpace;
import tsml.classifiers.Tuneable;
import weka.classifiers.AbstractClassifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.unsupervised.attribute.RandomSubset;
import weka.filters.unsupervised.attribute.Remove;

import java.util.*;
import java.util.stream.IntStream;

import static ml_6002b_coursework.Utility.loadTestData;
import static utilities.ClassifierTools.accuracy;
import static utilities.InstanceTools.resampleInstances;
import static utilities.Utilities.argMax;

public class TreeEnsemble extends AbstractClassifier implements Tuneable {
    /**
     * The task is to implement an Ensemble classifier that can be used with variants of your enhanced
     * ID3 classifier. You must implement this classifier from scratch rather than use any existing
     * classifiers in tsml. Implement a classifier, TreeEnsemble, that extends AbstractClassifier and
     * consists of an ensemble of ID3Coursework classifiers stored in an array or List. Set the default
     * ensemble size to 50. TreeEnsemble should be in the package ml_6002b_coursework.
     * **/
    private int seed = 0;
    private int numTrees = 50;
    private double proportion = 0.5;
    private boolean averaging = false;
    private AttributeMeasures splitMeasures = null;
    private ID3Coursework baseClassifier = new ID3Coursework();
    private final LinkedHashMap<ID3Coursework, RandomSubset> attributesUsed = new LinkedHashMap<>();

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getNumTrees() {
        return numTrees;
    }

    public void setNumTrees(int numTrees) {
        this.numTrees = numTrees;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public boolean isAveraging() {
        return averaging;
    }

    public void setAveraging(boolean averaging) {
        this.averaging = averaging;
    }

    public void setBaseClassifier(ID3Coursework baseClassifier) {
        this.baseClassifier = baseClassifier;
    }

    @Override
    public void setOptions(String[] options) throws Exception {
        String t = Utils.getOption('t', options);
        String p = Utils.getOption('p', options);
        String a = Utils.getOption('a', options);
        if (!t.equals(""))
            numTrees = Integer.parseInt(t);
        if (!p.equals(""))
            proportion = Double.parseDouble(p);
        if (!a.equals(""))
            averaging = Boolean.parseBoolean(a);
        baseClassifier.setOptions(options);
    }

    @Override
    public ParameterSpace getDefaultParameterSearchSpace() {
        ParameterSpace ps = new ParameterSpace();
        String[] numTrees={"50","100","300","500"};
        ps.addParameter("t", numTrees);
        String[] proportion={"0.5","0.75","1"};
        ps.addParameter("p", proportion);
        String[] averaging={"true","false"};
        ps.addParameter("a", averaging);
        ps.parameterLists.putAll(baseClassifier.getDefaultParameterSearchSpace().parameterLists);
        return ps;
    }

    private int[] asArray(List<Integer> list){
        return list.stream().mapToInt(a->a).toArray();
    }

    private Remove getFilter(int[] att){
        Remove attributeGetter = new Remove();
        attributeGetter.setInvertSelection(true);
        attributeGetter.setAttributeIndicesArray(att);
        return attributeGetter;
    }

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
         * implemented, but also can involve other tree parameters. stop criteria
         *
         * Implement an option where rather than counting votes, the ensemble has the option of
         * averaging probability distributions of the base classifiers.
         * **/
        System.out.println("building Tree ensemble \n" +
                numTrees + " trees\n" +
                proportion + " attributes\n" +
                (averaging? "averaging\n":""));
//        int numAttributes = (int) (data.numAttributes() * proportion);
        String[][] measures = baseClassifier.allMeasures();
        for (int i = 0; i < numTrees; i++) {
            Random random= new Random(seed);
//            List<Integer> attributes =
//                    IntStream.range(0, data.numAttributes()-1)
//                    .boxed().collect(Collectors.toList());
////            Instances inst = new Instances(data);
//            Collections.shuffle(attributes);
//            List<Integer> attIndices = attributes.subList(0, numAttributes);
//            attIndices.add(data.classIndex());
//            int[] att = asArray(attIndices);
//            Remove attributeGetter = getFilter(att);
//            attributeGetter.setInputFormat(data);
//            Instances inst = Filter.useFilter(data, attributeGetter);
////            inst.setClassIndex(inst.numAttributes()-1);
            RandomSubset attIndices = new RandomSubset();
            attIndices.setNumAttributes(proportion);
            attIndices.setSeed(seed + i);
            attIndices.setInputFormat(data);
            Instances inst = attIndices.process(data);

            ID3Coursework id3 = (ID3Coursework) makeCopy(baseClassifier);
            id3.setOptions(measures[random.nextInt(measures.length)]);
            id3.buildClassifier(inst);
            attributesUsed.put(id3, attIndices);
        }
        System.out.println("built success\n");

    }

    @Override
    public double classifyInstance(Instance instance) throws Exception {
        /**
         * Implement classifyInstance so that it returns the majority vote of the ensemble. i.e.,
         * classify a new instance with each of the decision trees, count how many classifiers predict
         * each class value, then return the class that receives the largest number of votes.
         * **/
        return argMax(distributionForInstance(instance), new Random());
    }

    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        // Implement distributionForInstance so that it returns the proportion of votes for
        // each class.
        double[] distribution = new double[instance.numClasses()];
        attributesUsed.forEach((id3, attributes) -> {
            try {
//                Remove attributeGetter = getFilter(attributes);
//                attributeGetter.setInputFormat(instance.dataset());
//                attributeGetter.input(instance);
//                Instance inst = attributeGetter.output();
                attributes.setInputFormat(instance.dataset());
                attributes.input(instance);
                Instance inst = attributes.output();
                if (averaging){
                    double[] dist = id3.distributionForInstance(inst);
                    IntStream.range(0, dist.length).forEach(i -> distribution[i] += dist[i]);
                }
                else{
                    distribution[(int) id3.classifyInstance(inst)]++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        IntStream.range(0, distribution.length).forEach(i -> distribution[i] /= numTrees);
        return distribution;
    }

    @Override
    public Capabilities getCapabilities() {
        return baseClassifier.getCapabilities();
    }

    public static void main(String[] args) throws Exception {
        /**
         * Implement a main method in the class TreeEnsemble that loads the problem optdigits,
         * prints out the test accuracy but also prints out the probability estimates for the first five
         * test cases.
         * **/
        TreeEnsemble ensemble = new TreeEnsemble();
        Instances optdigits = loadTestData("optdigits");
        Instances[] trainTest = resampleInstances(optdigits, 0, 0.7);
        Instances train = trainTest[0];
        Instances test = trainTest[1];
        ensemble.setAveraging(true);
        ensemble.buildClassifier(train);

        System.out.println("optdigits test accuracy: " + accuracy(test, ensemble));
        System.out.println("probability estimates for the first five test cases: ");
        for (int i = 0; i < 5; i++)
            System.out.println(Arrays.toString(ensemble.distributionForInstance(test.get(i))));
    }
}

package ml_6002b_coursework;

import weka.core.Instances;
import java.io.IOException;
import static experiments.data.DatasetLoading.loadData;

public class Utility {
    public static Instances loadTestData(String dataset) throws IOException {
        return loadData("src/main/java/ml_6002b_coursework/test_data/" + dataset + ".arff");
    }
}

package rf;

import java.util.List;

public class Dataset {
    public List<List<Double>> trainFeatures;
    public List<Integer> trainLabels;
    public List<List<Double>> valFeatures;
    public List<Integer> valLabels;
    public List<List<Double>> testFeatures;
    public List<Integer> testLabels;
    public List<String> featureNames;

    public int numFeatures() {
        return featureNames.size();
    }
}
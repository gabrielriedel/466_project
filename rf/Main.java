package rf;

public class Main {
    public static void main(String[] args) throws Exception {
        Dataset ds = DataPreprocessor.loadAndPreprocess("./data/updated_pitches.csv");
        RandomForest rf = new RandomForest(100, 15, 2, (int) Math.sqrt(ds.numFeatures()));
        rf.train(ds.trainFeatures, ds.trainLabels);

        System.out.println("Test Accuracy: " + Utils.accuracy(rf.predict(ds.testFeatures), ds.testLabels));

        Utils.printConfusionMatrix(rf.predict(ds.testFeatures), ds.testLabels);

        Utils.printFeatureImportances(rf.getFeatureImportances(), ds.featureNames);
    }
}
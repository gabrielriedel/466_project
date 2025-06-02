package rf;

public class HyperparameterTuning {
    public static void main(String[] args) throws Exception {
        Dataset ds = DataPreprocessor.loadAndPreprocess("./data/updated_pitches.csv");
        double bestAccuracy = 0;
        RandomForest bestModel = null;

        for (int trees : new int[]{10, 50, 100}) {
            for (int depth : new int[]{5, 10, 15}) {
                for (int minSplit : new int[]{2, 5}) {
                    RandomForest rf = new RandomForest(trees, depth, minSplit, (int) Math.sqrt(ds.numFeatures()));
                    rf.train(ds.trainFeatures, ds.trainLabels);

                    double acc = Utils.accuracy(rf.predict(ds.valFeatures), ds.valLabels);
                    System.out.printf("Trees=%d, Depth=%d, MinSplit=%d -> Val Acc=%.4f\n",
                            trees, depth, minSplit, acc);

                    if (acc > bestAccuracy) {
                        bestAccuracy = acc;
                        bestModel = rf;
                    }
                }
            }
        }

        System.out.println("\nBest Validation Accuracy: " + bestAccuracy);
        var testPredictions = bestModel.predict(ds.testFeatures);
        double testAcc = Utils.accuracy(testPredictions, ds.testLabels);
        System.out.println("Test Accuracy with best model: " + testAcc);
        Utils.printConfusionMatrix(testPredictions, ds.testLabels);
    }
}


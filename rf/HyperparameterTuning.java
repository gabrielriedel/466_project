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

// Results: 
// Trees=10, Depth=5, MinSplit=2 -> Val Acc=0.9079
// Trees=10, Depth=5, MinSplit=5 -> Val Acc=0.9126
// Trees=10, Depth=10, MinSplit=2 -> Val Acc=0.9265
// Trees=10, Depth=10, MinSplit=5 -> Val Acc=0.9321
// Trees=10, Depth=15, MinSplit=2 -> Val Acc=0.9284
// Trees=10, Depth=15, MinSplit=5 -> Val Acc=0.9330
// Trees=50, Depth=5, MinSplit=2 -> Val Acc=0.9172
// Trees=50, Depth=5, MinSplit=5 -> Val Acc=0.9079
// Trees=50, Depth=10, MinSplit=2 -> Val Acc=0.9349
// Trees=50, Depth=10, MinSplit=5 -> Val Acc=0.9321
// Trees=50, Depth=15, MinSplit=2 -> Val Acc=0.9312
// Trees=50, Depth=15, MinSplit=5 -> Val Acc=0.9312
// Trees=100, Depth=5, MinSplit=2 -> Val Acc=0.9079
// Trees=100, Depth=5, MinSplit=5 -> Val Acc=0.9153
// Trees=100, Depth=10, MinSplit=2 -> Val Acc=0.9321
// Trees=100, Depth=10, MinSplit=5 -> Val Acc=0.9312
// Trees=100, Depth=15, MinSplit=2 -> Val Acc=0.9349 <- best one 
// Trees=100, Depth=15, MinSplit=5 -> Val Acc=0.9321

// Best Validation Accuracy: 0.9348837209302325
// Test Accuracy with best model: 0.9452690166975881

// Confusion Matrix:
// Actual\Pred     0       1       2       3       4
// 0               598     5       3       0       0
// 1               7       106     1       0       10
// 2               5       1       157     0       0
// 3               0       0       0       74      3
// 4               2       21      0       1       84


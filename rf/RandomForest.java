package rf; 

import java.util.*;

public class RandomForest {
    private List<DecisionTree> trees;
    private int numTrees, maxDepth, minSamplesSplit, numFeaturesToConsider;
    private Random rand;

    public RandomForest(int numTrees, int maxDepth, int minSamplesSplit, int numFeaturesToConsider) {
        this(numTrees, maxDepth, minSamplesSplit, numFeaturesToConsider, 466); // default seed
    }

    public RandomForest(int numTrees, int maxDepth, int minSamplesSplit, int numFeaturesToConsider, int seed) {
        this.numTrees = numTrees;
        this.maxDepth = maxDepth;
        this.minSamplesSplit = minSamplesSplit;
        this.numFeaturesToConsider = numFeaturesToConsider;
        this.rand = new Random(seed);
        trees = new ArrayList<>();
    }

    public void train(List<List<Double>> X, List<Integer> y) {
        for (int i = 0; i < numTrees; i++) {
            List<List<Double>> Xs = new ArrayList<>();
            List<Integer> ys = new ArrayList<>();
            for (int j = 0; j < X.size(); j++) {
                int idx = rand.nextInt(X.size());
                Xs.add(X.get(idx));
                ys.add(y.get(idx));
            }
            DecisionTree tree = new DecisionTree(maxDepth, minSamplesSplit, numFeaturesToConsider);
            tree.fit(Xs, ys);
            trees.add(tree);
        }
    }

    public List<Integer> predict(List<List<Double>> X) {
        List<Integer> preds = new ArrayList<>();
        for (List<Double> x : X) {
            Map<Integer, Integer> votes = new HashMap<>();
            for (DecisionTree tree : trees) {
                int pred = tree.predict(x);
                votes.put(pred, votes.getOrDefault(pred, 0) + 1);
            }
            preds.add(Collections.max(votes.entrySet(), Map.Entry.comparingByValue()).getKey());
        }
        return preds;
    }

    public Map<String, Double> getFeatureImportances() {
        Map<String, Double> importance = new HashMap<>();
        for (DecisionTree tree : trees) {
            Map<Integer, Double> treeImportance = tree.featureImportances();
            for (Map.Entry<Integer, Double> entry : treeImportance.entrySet()) {
                String fname = String.valueOf(entry.getKey());
                importance.put(fname, importance.getOrDefault(fname, 0.0) + entry.getValue());
            }
        }
        return importance;
    }
}
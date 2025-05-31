package rf;

import java.util.*;

public class DecisionTree {
    static class TreeNode {
        boolean isLeaf;
        int prediction;
        int feature;
        double threshold;
        TreeNode left, right;

        TreeNode(int prediction) {
            this.isLeaf = true;
            this.prediction = prediction;
        }

        TreeNode(int feature, double threshold, TreeNode left, TreeNode right) {
            this.isLeaf = false;
            this.feature = feature;
            this.threshold = threshold;
            this.left = left;
            this.right = right;
        }

        int predict(List<Double> x) {
            if (isLeaf) return prediction;
            return x.get(feature) <= threshold ? left.predict(x) : right.predict(x);
        }

        void accumulateImportances(Map<Integer, Double> importance) {
            if (!isLeaf) {
                importance.put(feature, importance.getOrDefault(feature, 0.0) + 1.0);
                left.accumulateImportances(importance);
                right.accumulateImportances(importance);
            }
        }
    }

    private int maxDepth, minSamplesSplit, numFeaturesToConsider;
    private TreeNode root;

    public DecisionTree(int maxDepth, int minSamplesSplit, int numFeaturesToConsider) {
        this.maxDepth = maxDepth;
        this.minSamplesSplit = minSamplesSplit;
        this.numFeaturesToConsider = numFeaturesToConsider;
    }

    public void fit(List<List<Double>> X, List<Integer> y) {
        root = buildTree(X, y, 0);
    }

    private TreeNode buildTree(List<List<Double>> X, List<Integer> y, int depth) {
        if (X.size() < minSamplesSplit || depth >= maxDepth || isPure(y)) {
            return new TreeNode(getMajorityLabel(y));
        }

        List<Integer> featureIndices = getRandomFeatures(X.get(0).size());
        double bestGain = 0;
        int bestFeature = -1;
        double bestThreshold = 0;
        List<List<Double>> leftX = null, rightX = null;
        List<Integer> leftY = null, rightY = null;

        for (int feature : featureIndices) {
            Set<Double> values = new HashSet<>();
            for (List<Double> row : X) values.add(row.get(feature));
            for (double threshold : values) {
                List<List<Double>> lX = new ArrayList<>();
                List<List<Double>> rX = new ArrayList<>();
                List<Integer> lY = new ArrayList<>();
                List<Integer> rY = new ArrayList<>();
                for (int i = 0; i < X.size(); i++) {
                    if (X.get(i).get(feature) <= threshold) {
                        lX.add(X.get(i)); lY.add(y.get(i));
                    } else {
                        rX.add(X.get(i)); rY.add(y.get(i));
                    }
                }
                double gain = giniGain(y, lY, rY);
                if (gain > bestGain) {
                    bestGain = gain;
                    bestFeature = feature;
                    bestThreshold = threshold;
                    leftX = lX; 
                    rightX = rX;
                    leftY = lY; 
                    rightY = rY;
                }
            }
        }

        if (bestGain == 0) return new TreeNode(getMajorityLabel(y));

        TreeNode left = buildTree(leftX, leftY, depth + 1);
        TreeNode right = buildTree(rightX, rightY, depth + 1);
        return new TreeNode(bestFeature, bestThreshold, left, right);
    }

    private List<Integer> getRandomFeatures(int totalFeatures) {
        List<Integer> features = new ArrayList<>();
        for (int i = 0; i < totalFeatures; i++) features.add(i);
        Collections.shuffle(features);
        return features.subList(0, numFeaturesToConsider);
    }

    private double gini(List<Integer> y) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int label : y) freq.put(label, freq.getOrDefault(label, 0) + 1);
        double impurity = 1.0;
        int total = y.size();
        for (int count : freq.values()) {
            double p = count / (double) total;
            impurity -= p*p;
        }
        return impurity;
    }

    private double giniGain(List<Integer> y, List<Integer> left, List<Integer> right) {
        double giniLeft = gini(left);
        double giniRight = gini(right);
        double weighted = (left.size() * giniLeft + right.size() * giniRight) / y.size();
        return gini(y) - weighted;
    }

    private boolean isPure(List<Integer> y) {
        int first = y.get(0);
        for (int label : y) if (label != first) return false;
        return true;
    }

    private int getMajorityLabel(List<Integer> y) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int label : y) count.put(label, count.getOrDefault(label, 0) + 1);
        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public int predict(List<Double> x) {
        return root.predict(x);
    }

    public Map<Integer, Double> featureImportances() {
        Map<Integer, Double> importances = new HashMap<>();
        root.accumulateImportances(importances);
        return importances;
    }
}

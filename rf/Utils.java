package rf;

import java.util.*;

public class Utils {
    public static double accuracy(List<Integer> predictions, List<Integer> labels) {
        int correct = 0;
        for (int i = 0; i < predictions.size(); i++) {
            if (predictions.get(i).equals(labels.get(i))) {
                correct++;
            }
        }
        return correct / (double) predictions.size();
    }

    public static void printFeatureImportances(Map<String, Double> importance, List<String> featureNames) {
        System.out.println("Feature Importances:");
        importance.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .forEach(entry -> {
                String key = entry.getKey();
                String featureName = key;
                try {
                    int index = Integer.parseInt(key);
                    if (index >= 0 && index < featureNames.size()) {
                        featureName = featureNames.get(index);
                    }
                } catch (NumberFormatException ignored) {}
                System.out.printf("%s: %.4f%n", featureName, entry.getValue());
            });
    }


    public static void printConfusionMatrix(List<Integer> predictions, List<Integer> labels) {
        Set<Integer> classes = new HashSet<>(labels);
        classes.addAll(predictions);
        List<Integer> sortedClasses = new ArrayList<>(classes);
        Collections.sort(sortedClasses);

        Map<Integer, Integer> classToIndex = new HashMap<>();
        for (int i = 0; i < sortedClasses.size(); i++) {
            classToIndex.put(sortedClasses.get(i), i);
        }

        int[][] matrix = new int[sortedClasses.size()][sortedClasses.size()];

        for (int i = 0; i < predictions.size(); i++) {
            int actual = labels.get(i);
            int predicted = predictions.get(i);
            if (classToIndex.containsKey(actual) && classToIndex.containsKey(predicted)) {
                matrix[classToIndex.get(actual)][classToIndex.get(predicted)]++;
            }
        }

        System.out.println("\nConfusion Matrix:");
        System.out.print("Actual\\Pred\t");
        for (int label : sortedClasses) {
            System.out.print(label + "\t");
        }
        System.out.println();

        for (int i = 0; i < sortedClasses.size(); i++) {
            System.out.print(sortedClasses.get(i) + "\t\t");
            for (int j = 0; j < sortedClasses.size(); j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
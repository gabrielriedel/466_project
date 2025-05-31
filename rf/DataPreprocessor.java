package rf;

import java.io.*;
import java.util.*;

public class DataPreprocessor {
    public static Dataset loadAndPreprocess(String filePath) throws IOException {
        return loadAndPreprocess(filePath, 466); // default seed
    }

    public static Dataset loadAndPreprocess(String filePath, int seed) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String header = reader.readLine();
        String[] cols = header.split(",");

        List<List<String>> rawRows = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            rawRows.add(Arrays.asList(line.split(",")));
        }
        reader.close();

        int labelIndex = cols.length - 1;
        Map<String, Integer> labelMap = new HashMap<>();

        List<List<Double>> allFeatures = new ArrayList<>();
        List<Integer> allLabels = new ArrayList<>();

        for (List<String> row : rawRows) {
            List<Double> feat = new ArrayList<>();
            for (int i = 0; i < cols.length - 1; i++) {
                feat.add(Double.parseDouble(row.get(i)));
            }
            allFeatures.add(feat);
            String rawLabel = row.get(labelIndex);
            labelMap.putIfAbsent(rawLabel, labelMap.size());
            allLabels.add(labelMap.get(rawLabel));
        }

        Map<Integer, List<Integer>> classIndices = new HashMap<>();
        for (int i = 0; i < allLabels.size(); i++) {
            classIndices.computeIfAbsent(allLabels.get(i), k -> new ArrayList<>()).add(i);
        }

        List<List<Double>> trainX = new ArrayList<>(), valX = new ArrayList<>(), testX = new ArrayList<>();
        List<Integer> trainY = new ArrayList<>(), valY = new ArrayList<>(), testY = new ArrayList<>();
        Random rand = new Random(seed);

        for (List<Integer> indices : classIndices.values()) {
            Collections.shuffle(indices, rand);
            int total = indices.size();
            int nTrain = (int) (total * 0.6);
            int nVal = (int) (total * 0.2);
            for (int i = 0; i < total; i++) {
                int idx = indices.get(i);
                if (i < nTrain) {
                    trainX.add(allFeatures.get(idx));
                    trainY.add(allLabels.get(idx));
                } else if (i < nTrain + nVal) {
                    valX.add(allFeatures.get(idx));
                    valY.add(allLabels.get(idx));
                } else {
                    testX.add(allFeatures.get(idx));
                    testY.add(allLabels.get(idx));
                }
            }
        }

        Dataset ds = new Dataset();
        ds.trainFeatures = trainX; 
        ds.trainLabels = trainY;
        ds.valFeatures = valX; 
        ds.valLabels = valY;
        ds.testFeatures = testX; 
        ds.testLabels = testY;
        ds.featureNames = new ArrayList<>(Arrays.asList(cols)); 
        return ds;
    }
}
package RandomForestClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Matrix {

    public int[][] matrix;

    public Matrix(int[][] other){
        matrix = other;
    }

    private int findFrequency(int attribute, int value, ArrayList<Integer> rows){
        int count = 0;
        for(int row : rows){
            if(matrix[row][attribute] == value){
                count++;
            }
        }
        return count;
    }

    private HashSet<Integer> findDifferentValues(int attribute, ArrayList<Integer> rows){
        HashSet<Integer> uniqueVals = new HashSet<>();
        for(int row : rows){
            uniqueVals.add(matrix[row][attribute]);
        }
        return uniqueVals;
    }

    private ArrayList<Integer> findRows(int attribute, int value, ArrayList<Integer> rows){
        ArrayList<Integer> validRows = new ArrayList<>();
        for(int row : rows){
            if(matrix[row][attribute] == value){
                validRows.add(row);
            }
        }
        return validRows;
    }

    private double log2(double number){
        return Math.log(number)/Math.log(2.0);
    }

    private double findEntropy(ArrayList<Integer> rows){
        double class1;
        double class2;
        double class3;
        int n = rows.size();
        int freq1 = findFrequency(4, 1, rows);
        int freq2 = findFrequency(4, 2, rows);
        int freq3 = findFrequency(4, 3, rows);
        if(freq1 == 0){
            class1 = 0;
        }
        else{
            class1 = -1.0*freq1/n * log2(1.0*freq1/n);
        } 

        if(freq2 == 0){
            class2 = 0.0;
        }
        else{
            class2 = -1.0*freq2/n * log2(1.0*freq2/n);
        } 

        if(freq3 == 0){
            class3 = 0.0;
        }
        else{
            class3 = -1.0*freq3/n * log2(1.0*freq3/n);
        } 
        return class1+class2+class3;
    }

    private double findEntropy(int attribute, ArrayList<Integer> rows){
        HashSet<Integer> values = findDifferentValues(attribute, rows);
        double entropy = 0.0;
        int n = rows.size();
        for(int value : values){
            ArrayList<Integer> newRows = findRows(attribute, value, rows);
            int valueCount = findFrequency(attribute, value, newRows);
            entropy += findEntropy(newRows)*(1.0 * valueCount/n);
        }
        return entropy;
    }

    private double findGain(int attribute, ArrayList<Integer> rows){
        return findEntropy(rows) - findEntropy(attribute, rows);
    }
    
    public double computeIGR(int attribute, ArrayList<Integer> rows){
        double gain = findGain(attribute, rows);
        double normalize = 0.0;
        HashSet<Integer> values = findDifferentValues(attribute, rows);
        int n = rows.size();
        for(int value : values){
            int freq = findFrequency(attribute, value, rows);
            if(freq != 0){
                normalize += (-1.0*freq)/n *log2(1.0*freq/n);
            }
        }
        return gain/normalize;
    }

    public int findMostCommonValue(ArrayList<Integer> rows){
        HashMap<Integer, Integer> categories = new HashMap<>();
        categories.put(1, findFrequency(4,1,rows));
        categories.put(2, findFrequency(4,2,rows));
        categories.put(3, findFrequency(4,3,rows));
        int max_val = 0;
        int max_key = 0;
        for(Map.Entry<Integer, Integer> entry : categories.entrySet()){
            if(entry.getValue() > max_val){
                max_key = entry.getKey();
                max_val = entry.getValue();
            }
        }
        return max_key;

    }

    public HashMap<Integer, ArrayList<Integer>> split(int attribute, ArrayList<Integer> rows){
        HashSet<Integer> values = findDifferentValues(attribute, rows);
        HashMap<Integer, ArrayList<Integer>> splitData = new HashMap<>();
        for(int value : values){
            splitData.put(value, findRows(attribute, value, rows));
        }
        return splitData;
    }
}

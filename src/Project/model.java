package Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RandomForestClasses.DataRecord;

public class model {
    
    public static void main(String args[]){

        List<DataRecord> pitchData = readCSV("./files/cp_all_pitches.csv");
        int i = 0;
        for(DataRecord row : pitchData){
            if(i > 5){break;}
            System.out.println(row.velo);
            System.out.println(row.ivb);
            System.out.println(row.hb);
            System.out.println(row.tilt);
            System.out.println(row.spin);
            System.out.println(row.pitch);
            i++;
        }
    }

    public static List<DataRecord> readCSV(String filePath) {
        List<DataRecord> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                double velo = Double.parseDouble(values[0]);
                double ivb = Double.parseDouble(values[1]);
                double hb = Double.parseDouble(values[2]);
                double spin = Double.parseDouble(values[3]);
                double tilt = Double.parseDouble(values[4]);
                String pitch = values[5];

                data.add(new DataRecord(velo, ivb, hb, spin, tilt, pitch));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}

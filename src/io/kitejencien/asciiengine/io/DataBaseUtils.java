package io.kitejencien.asciiengine.io;

import io.kitejencien.asciiengine.database.CharData;

import java.io.*;
import java.util.ArrayList;

/**
 * @author kitejencien
 */
public class DataBaseUtils {

    public static final String DATASET_PATHNAME = "AllSet.txt";

    public static ArrayList<CharData> readLines(int[] lines) throws IOException {

        ArrayList<CharData> data = new ArrayList<>();
        File dataset = new File(DATASET_PATHNAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataset)));

        String current;
        while((current = reader.readLine())!= null){
            for (int i : lines){
                if(current.startsWith(Integer.toString(i))){
                    data.add(new CharData(current));
                }
            }
        }

        return data;
    }

    public static ArrayList<CharData> readLines(char... chars) throws IOException {

        ArrayList<CharData> data = new ArrayList<>();
        File dataset = new File(DATASET_PATHNAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataset)));

        String current;
        while((current = reader.readLine())!= null){
            for (char i : chars){
                if(current.split(" ")[1].equals(String.valueOf(i))){
                    data.add(new CharData(current));
                }
            }
        }

        return data;
    }

    public static ArrayList<CharData> readLines(int startInd, int endInd) throws IOException {

        ArrayList<CharData> data = new ArrayList<>();
        File dataset = new File(DATASET_PATHNAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataset)));

        String current;
        while((current = reader.readLine())!= null){
            if(Integer.parseInt(current.split(" ")[0]) >= startInd
            && Integer.parseInt(current.split(" ")[0]) < endInd){
                    data.add(new CharData(current));
            }
        }

        return data;
    }
}

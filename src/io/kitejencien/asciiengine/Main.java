package io.kitejencien.asciiengine;

import io.kitejencien.asciiengine.database.DataGenerator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        DataGenerator data = new DataGenerator();
        data.generateData(0,65535, "AllSet.txt");
    }
}

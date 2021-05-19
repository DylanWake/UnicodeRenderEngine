package io.kitejencien.asciiengine;

import io.kitejencien.asciiengine.database.DataGenerator;
import io.kitejencien.asciiengine.io.ImageUtils;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        DataGenerator generator = new DataGenerator();
        generator.generateData(0,65535, "UnicodeSet.txt");
    }
}

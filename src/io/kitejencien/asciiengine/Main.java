package io.kitejencien.asciiengine;

import io.kitejencien.asciiengine.database.DataGenerator;
import io.kitejencien.asciiengine.io.ImageUtils;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedImage image = ImageUtils.readImage("abc.png");
        image = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(image, null);
        System.out.println(image.getRGB(0,0));
    }
}

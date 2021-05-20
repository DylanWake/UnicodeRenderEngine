package io.kitejencien.asciiengine;

import io.kitejencien.asciiengine.database.DataGenerator;
import io.kitejencien.asciiengine.display.Display;
import io.kitejencien.asciiengine.display.TerminalDisplay;
import io.kitejencien.asciiengine.display.WindowDisplay;
import io.kitejencien.asciiengine.io.ImageUtils;
import io.kitejencien.asciiengine.renderer.*;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        CharRenderer renderer = new BrightnessRendererUnicode();
        renderer.onLoadData();
        Display display = new WindowDisplay();

        BufferedImage input = ImageIO.read(new File("frisk.jpg"));
        renderer.onRender(input);
        display.onUpdate(renderer.canvas);
        System.out.println("calculate done");
    }
}

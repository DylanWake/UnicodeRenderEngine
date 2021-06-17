import io.kitejencien.asciiengine.database.DataGenerator;
import io.kitejencien.asciiengine.display.*;
import io.kitejencien.asciiengine.io.ImageUtils;
import io.kitejencien.asciiengine.renderer.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        renderText();
    }

    public static void renderText(){
        CharRenderer renderer = new AdvancedTextRenderer();
        renderer.onLoadData();
        ColoredImageOutputs outputs = new ColoredImageOutputs(renderer);
        String outputPath = "C:\\Users\\10200\\Desktop\\Temp\\outputs";
        int frames = 1600;

        for (int i = 0; i < frames; i++) {
            try {
                File outputfile = new File(outputPath + "/" + "TextFrame_" + i + ".jpg");
                ImageIO.write(outputs.renderImage(null), "jpg", outputfile);
                System.out.println("Processed: " + "TextFrame_" + i + ".jpg");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void renderImages(){
        CharRenderer renderer = new BrightnessRendererUnicode();
        renderer.onLoadData();
        ImageOutputs outputs = new ImageOutputs(renderer);

        String inputPath = "C:\\Users\\10200\\Desktop\\Temp\\inputs";
        // String inputPath = "inputs";
        String outputPath = "C:\\Users\\10200\\Desktop\\Temp\\outputs";
        //String outputPath = "outputs";
        new File(outputPath).mkdir();
        ArrayList<File> files = new ArrayList<>(Arrays.asList(Objects.requireNonNull(getFiles(inputPath))));

        files.sort(Comparator.comparingInt(a -> Integer.parseInt(a.getName().split("_")[1].split("\\.")[0])));
        files.forEach(file -> {
            try {
                //  if (Integer.parseInt(file.getName().split("_")[3].split("\\.")[0]) < 1192) return;
                BufferedImage input = ImageIO.read(file);
                File outputfile = new File(outputPath + "/" + file.getName());
                ImageIO.write(outputs.renderImage(input), "jpg", outputfile);
                System.out.println("Processed: " + file.getName());
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public static File[] getFiles(String path) {

        File file = new File(path);
        if (file.isDirectory()) {
            return file.listFiles();
        }
        else return null;
    }

}


package io.kitejencien.asciiengine.database;

import io.kitejencien.asciiengine.io.ImageUtils;
import io.kitejencien.asciiengine.renderer.CharRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author kiteJencien
 */

public class DataGenerator {

    public final int DISPLAY_WIDTH = 100;
    public final int DISPLAY_HEIGHT = 100;
    public final int FONT_SIZE = 50;
    public final int SAMPLE_MAP_SIZE = CharRenderer.SAMPLE_SIZE;

    public void generateData(int min, int max, String pathname) throws IOException {

        ArrayList<String> output = new ArrayList<>();

        BufferedImage img = new BufferedImage(DISPLAY_WIDTH, DISPLAY_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        FontMetrics metrics = g.getFontMetrics(new Font("黑体",Font.PLAIN,FONT_SIZE));

        for(int i = min; i < max; i++) {

            StringBuilder result = new StringBuilder();
            result.append(i).append(" ").append((char) (i + '0'));
            int xShift = metrics.stringWidth(String.valueOf((char) (i + '0')));
            int yShift = metrics.getHeight();

            generateGraphics(g, (char) (i + '0'), (DISPLAY_WIDTH - xShift)/2,
                    (DISPLAY_HEIGHT- yShift)/2 + metrics.getAscent());

            int yMax = (DISPLAY_HEIGHT + yShift)/2;
            int yMin = (DISPLAY_HEIGHT - yShift)/2;
            int xMax = (DISPLAY_WIDTH + xShift)/2;
            int xMin = (DISPLAY_WIDTH - xShift)/2;

            int xStepLength = (xMax - xMin) / SAMPLE_MAP_SIZE;
            int yStepLength = (yMax - yMin) / SAMPLE_MAP_SIZE;
            result.append(" ").append(calculateBrightness(img, xMin, yMin, (xMax - xMin), (yMax - yMin)));
            double[][] sampleMap = new double[SAMPLE_MAP_SIZE][SAMPLE_MAP_SIZE];

            double maxBrightness = 0;
            for (int xStep = 0; xStep < SAMPLE_MAP_SIZE; xStep++) {
                for (int yStep = 0; yStep < SAMPLE_MAP_SIZE; yStep++) {
                    sampleMap[xStep][yStep] = calculateBrightness(img, xMin + xStep * xStepLength,
                            yMin + yStep * yStepLength, xStepLength, yStepLength);
                    maxBrightness = Math.max(maxBrightness, sampleMap[xStep][yStep]);
                }
            }

            for (int xStep = 0; xStep < SAMPLE_MAP_SIZE; xStep++) {
                for (int yStep = 0; yStep < SAMPLE_MAP_SIZE; yStep++) {
                    sampleMap[xStep][yStep] = sampleMap[xStep][yStep] / maxBrightness > 1
                            ? 1 : sampleMap[xStep][yStep] / maxBrightness;
                    result.append(" ").append(sampleMap[xStep][yStep]);
                }
            }
            if (result.toString().contains("0.10482758620689656 0.9583333333333334 0.7916666666666667 " +
                    "0.7083333333333334 0.6666666666666666 0.0 0.6666666666666666 1.0 0.7916666666666667 0.75") ||
               result.toString().contains("NaN")) continue;

            System.out.println(result.toString());
            output.add(result.toString());
        }

        File file = new File(pathname);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        output.forEach(s -> {try {
            fileWriter.write(s + "\n");
        }catch (IOException e){
            e.printStackTrace();
        }});
        fileWriter.close();
    }

    public void generateGraphics(Graphics g, char c, int xp, int yp){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体",Font.PLAIN,FONT_SIZE));
        g.drawString(String.valueOf(c),xp,yp);
    }

    public double calculateBrightness(BufferedImage img, int x0, int y0, int xStepSize, int yStepSize){
        int pixelSum = xStepSize*yStepSize;
        int active = 0;

        for(int x = x0; x < x0 + xStepSize; x++){
            for(int y = y0; y < y0 + yStepSize; y++){
                active += ImageUtils.convertRGB(img.getRGB(x, y))[0] > 0 ? 1 : 0;
            }
        }
        return (double) active / (double) pixelSum;
    }

}

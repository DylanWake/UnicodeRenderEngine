package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.io.ImageUtils;
import io.kitejencien.asciiengine.renderer.text.CharParams;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * core renderer interface
 * @author kitejencien
 */
public abstract class CharRenderer {

    public static final int CANVAS_WIDTH = 150;
    public static final int CANVAS_HEIGHT_STANDARD = (int)(CANVAS_WIDTH* 9d / 16);
    public static final int STANDARD_CANVAS_CC = CharRenderer.CANVAS_WIDTH * CharRenderer.CANVAS_HEIGHT_STANDARD;
    public static final int SAMPLE_SIZE = 3;

    int max_brightness = 0;
    int min_brightness = 255;

    public ArrayList<CharData> dataSet;
    public BufferedImage currentTarget;
    public int canvasHeight;
    public char[][] canvas;
    public CharParams[][] frame;

    /**
     * load the data into the container
     */
    public abstract void onLoadData();

    /**
     * calculate cost
     * @param pixels pixels sampled ( 3 x 3 )
     * @param current the char iterated from the render method
     * @return cost
     */
    public abstract double calculateCost(int[][] pixels, CharData current);


    /**
     * renderer
     */
    public void onRender(BufferedImage img){

        // preprocess image for better sampling efficiency
        int w = img.getWidth();
        int h = img.getHeight();
        int procW = CANVAS_WIDTH*SAMPLE_SIZE;
        int procH = (int)(h * (((double)CANVAS_WIDTH*SAMPLE_SIZE)/w));

        this.canvasHeight = procH / SAMPLE_SIZE;

        // resize
        BufferedImage toProcess = new BufferedImage(procW, procH, img.getType());
        Graphics2D g = toProcess.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, procW, procH, null);
        g.dispose();

        onRender(ImageUtils.constructImageArray(toProcess));
    }

    /**
     * renderer
     */
    public void onRender(int[][] pixels){

        this.dataSet.add(new CharData(65536,' ',0, new double[][]{{0,0,0},{0,0,0},{0,0,0}}));
        char[][] out = new char[CANVAS_WIDTH][this.canvasHeight];

        for(int[] line : pixels){
            for(int i : line){
                this.max_brightness = Math.max(i, max_brightness);
                this.min_brightness = Math.min(i, min_brightness);
            }
        }

        for(int x = 0; x < CANVAS_WIDTH; x++){
            for(int y = 0; y < this.canvasHeight; y++){

                int[][] samples = new int[SAMPLE_SIZE][SAMPLE_SIZE];

                for (int i = 0; i < SAMPLE_SIZE; i++) {
                    System.arraycopy(pixels[SAMPLE_SIZE * x + i], 3 * y + 0, samples[i], 0, SAMPLE_SIZE);
                }

                double cost = Double.MAX_VALUE;
                char selected = '0';
                for(CharData data : dataSet) {
                    double o = cost;
                    cost = Math.min(cost, calculateCost(samples, data));
                    selected = cost < o ? data.getContent() : selected;
                }
                //System.out.println(cost);
                out[x][y] = selected;
            }
        }
        this.canvas = out;
    }

    public double calcBrightness(int[][] samples){
        double brightness = 0;
        for(int[] i : samples){
            for(int j : i){
                brightness += j;
            }
        }
        return brightness / (255*SAMPLE_SIZE*SAMPLE_SIZE);
    }

    public double calcBrightnessComplete(int[][] samples){
        double brightness = 0;
        for(int[] i : samples){
            for(int j : i){
                brightness += j;
            }
        }
        return brightness / (SAMPLE_SIZE*SAMPLE_SIZE);
    }
}

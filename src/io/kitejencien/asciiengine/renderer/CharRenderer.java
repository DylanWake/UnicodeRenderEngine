package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.io.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * core renderer interface
 * @author kitejencien
 */
public abstract class CharRenderer {

    public static final int CANVAS_WIDTH = 100;
    public static final int SAMPLE_SIZE = 3;

    public ArrayList<CharData> dataSet;
    public BufferedImage currentTarget;
    public int canvasHeight;
    public char[][] canvas;

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
        int procH = h * ((CANVAS_WIDTH*SAMPLE_SIZE)/w);
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

        for(int x = 0; x < CANVAS_WIDTH; x++){
            for(int y = 0; y < this.canvasHeight; y++){

                int[][] samples = new int[SAMPLE_SIZE][SAMPLE_SIZE];

                for (int i = 0; i < SAMPLE_SIZE; i++) {
                    System.arraycopy(pixels[3 * x + i], 3 * y, samples[i], 0, SAMPLE_SIZE);
                }

                double cost = Double.MAX_VALUE;
                char selected = '0';
                for(CharData data : dataSet) {
                    double o = cost;
                    cost = Math.min(cost, calculateCost(pixels, data));
                    selected = cost < o ? data.getContent() : selected;
                }
                out[x][y] = selected;
            }
        }
        this.canvas = out;
    }
}

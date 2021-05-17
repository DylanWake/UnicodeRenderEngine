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

    public final int CANVAS_WIDTH = 100;
    public final int SAMPLE_SIZE = 3;
    public ArrayList<CharData> dataSet;
    public BufferedImage currentTarget;
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
    public abstract double calculateCost(int[][] pixels, char current);

    /**
     * what to do when the results are displayed
     * ( terminal , separated window , separated full screen , font , size ...)
     */
    public abstract void onDisplay();

    /**
     * main life cycle
     */
    public abstract void mainCycle();

    public CharRenderer(){

        onLoadData();
        while(true){
            mainCycle();
        }
    }

    /**
     * renderer
     * @return the returned sample map
     */
    public char[][] onRender(BufferedImage img){

        // preprocess image for better sampling efficiency
        int w = img.getWidth();
        int h = img.getHeight();
        int procW = CANVAS_WIDTH*SAMPLE_SIZE;
        int procH = h * ((CANVAS_WIDTH*SAMPLE_SIZE)/w);

        // resize
        BufferedImage toProcess = new BufferedImage(procW, procH, img.getType());
        Graphics2D g = toProcess.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, procW, procH, null);
        g.dispose();

        return onRender(ImageUtils.constructImageArray(toProcess));
    }

    /**
     * renderer
     * @return the returned sample map
     */
    public char[][] onRender(int[][][] input){
        return new char[CANVAS_WIDTH][CANVAS_WIDTH];
    }

}

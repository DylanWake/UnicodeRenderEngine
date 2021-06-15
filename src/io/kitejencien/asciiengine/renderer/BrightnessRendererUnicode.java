package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.io.DataBaseUtils;
import io.kitejencien.asciiengine.tree.Tree;

import javax.xml.crypto.Data;
import java.io.IOException;

/**
 * @author KiteJencien
 */
public class BrightnessRendererUnicode extends BrightnessRenderer{

    public Tree tree;

    @Override
    public void onLoadData() {
        try {
            //this.dataSet = DataBaseUtils.readLines(0, 65535);

            this.dataSet = DataBaseUtils.readLines(12260, 65535);
            this.dataSet.addAll(DataBaseUtils.readLines(0,128));
            this.dataSet.addAll(DataBaseUtils.readLines(9571,9572));


        } catch (IOException e){
            e.printStackTrace();
        }
        this.dataSet.add(new CharData(65536,' ',0, new double[][]{{0,0,0},{0,0,0},{0,0,0}}));
        tree = new Tree();
        tree.initialSet = dataSet;
        tree.root = tree.build(null, tree.initialSet);
    }

    @Override
    public void onRender(int[][] pixels) {
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
                    System.arraycopy(pixels[SAMPLE_SIZE * x + i], 3 * y, samples[i], 0, SAMPLE_SIZE);
                }
                out[x][y] = tree.search((calcBrightnessComplete(samples) - min_brightness)
                        /(max_brightness - min_brightness)).getContent();
            }
        }
        this.canvas = out;
    }
}

package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.io.DataBaseUtils;

import java.io.IOException;

/**
 * @author Kitejencien
 */
public class BrightnessRenderer extends CharRenderer {
    @Override
    public void onLoadData() {
        try {
            this.dataSet = DataBaseUtils.readLines(0, 77);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public double calculateCost(int[][] pixels, CharData current) {
        return Math.abs(current.getBrightness() - calcBrightness(pixels));
    }
}

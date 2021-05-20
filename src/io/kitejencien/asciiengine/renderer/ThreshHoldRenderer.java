package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.io.DataBaseUtils;

import java.io.IOException;

/**
 * @author KiteJencien
 */
public class ThreshHoldRenderer extends CharRenderer{

    double THRESH_HOLD = 0.5;

    @Override
    public void onLoadData() {
        try {
            this.dataSet = DataBaseUtils.readLines('█');
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public double calculateCost(int[][] pixels, CharData current) {
        if(current.getContent() == ' '){
            return (calcBrightnessComplete(pixels) - min_brightness)/(max_brightness - min_brightness)
                    > THRESH_HOLD ? 1 : 0;
        }
        return (calcBrightnessComplete(pixels) - min_brightness)/(max_brightness - min_brightness)
                > THRESH_HOLD ? 0 : 1;
    }
}

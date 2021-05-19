package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.io.DataBaseUtils;

import java.io.IOException;

/**
 * @author KiteJencien
 */
public class ThreshHoldRenderer extends CharRenderer{
    @Override
    public void onLoadData() {
        try {
            this.dataSet = DataBaseUtils.readLines('■');
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public double calculateCost(int[][] pixels, CharData current) {
        return pixels[0][0] > 0 ? 1 : 0;
    }
}

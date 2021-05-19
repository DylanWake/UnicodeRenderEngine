package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;

/**
 * @author KiteJencien
 */
public class SampleMapRenderer extends CharRenderer{
    @Override
    public void onLoadData() {

    }

    @Override
    public double calculateCost(int[][] pixels, CharData current) {
        return 0;
    }
}

package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.io.DataBaseUtils;

import java.io.IOException;

/**
 * @author KiteJencien
 */
public class SampleMapRendererUnicode extends SampleMapRenderer{
    @Override
    public void onLoadData() {
        try {
            this.dataSet = DataBaseUtils.readLines(0, 65535);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

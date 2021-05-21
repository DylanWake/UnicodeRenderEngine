package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.io.DataBaseUtils;

import java.io.IOException;

public class BrightnessMinimalRender extends BrightnessRenderer{
    @Override
    public void onLoadData() {
        try {
            this.dataSet = DataBaseUtils.readLines('.',',','-','~',':',';','=','!','*','#','$','@');
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

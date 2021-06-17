package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.renderer.text.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author KiteJencien
 */


public class AdvancedTextRenderer extends CharRenderer{

    String[] keyWords = new String[]{"互联网大厂","内卷","后厂村","程序员","躺平"};
    ArrayList<String> allDataBase = new ArrayList<>();
    TextEffects[] effects;
    TextEffects runningEffect;

    @Override
    public void onLoadData() {
        try {
            for (String keyword: keyWords) {
                File file = new File("textData/" + keyword +"_OUTPUTS.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String current;
                while ((current = reader.readLine()) != null) {
                    this.allDataBase.add(keyword + "_" + current);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        effects = new TextEffects[]{
                new WAVE(),
                new CONTROL(),
                new INFECTION()
        };
    }

    @Override
    public double calculateCost(int[][] pixels, CharData current) {
        return 0;
    }

    public ArrayList<String> getAllElementsKey(String key){
        ArrayList<String> out = new ArrayList<>();
        this.allDataBase.forEach(s -> {if (s.startsWith(key)) out.add(s);});
        return out;
    }

    @Override
    public void onRender(BufferedImage input) {
        // randomly select a new effect to start with
        if (runningEffect == null){
            String key = keyWords[(int)(Math.random() * keyWords.length)];
            this.runningEffect = effects[2];
            runningEffect.setKeyWord(key);
            runningEffect.onReset();
            runningEffect.onLoadData(getAllElementsKey(key));
            runningEffect.onFillUp();
        }

        this.frame = runningEffect.onFrame(new CharParams[CANVAS_WIDTH][CANVAS_HEIGHT_STANDARD]);

        if (runningEffect.isRenderComplete()){
            this.runningEffect = null;
        }
    }
}

package io.kitejencien.asciiengine.renderer.text;

import io.kitejencien.asciiengine.renderer.CharRenderer;

import java.awt.*;
import java.util.ArrayList;

class InfectionCenter{
    int x,y, affectionRadius, decayRadius;

    public InfectionCenter(int x, int y){
        this.x = x;
        this.y = y;
        this.affectionRadius = 0;
        this.decayRadius = -5;
    }
}

public abstract class TextEffects{

    //these are for some special effects
    public ArrayList<String> data;
    public CharParams[][] durationMap;
    public int frameCount = 0;
    public int neededChars;
    boolean renderComplete = false;
    public String keyWord;

    TextEffects(){
        this.durationMap = new CharParams[CharRenderer.CANVAS_WIDTH][CharRenderer.CANVAS_HEIGHT_STANDARD];
        this.neededChars = CharRenderer.STANDARD_CANVAS_CC;
    }

    public void onLoadData(ArrayList<String> data){
        this.data = data;
    }

    public void setKeyWord(String keyWord){
        if (keyWord.length() > 5){
            this.keyWord = keyWord.substring(0,5);
            return;
        }
        while (keyWord.length() < 5){
            keyWord +=  "*";
        }
        this.keyWord = keyWord;
    }

    public void onReset(){
        this.durationMap = new CharParams[CharRenderer.CANVAS_WIDTH][CharRenderer.CANVAS_HEIGHT_STANDARD];
        this.frameCount = 0;
        this.renderComplete = false;
        onResetVariables();
    }

    /**
     * fill up the prebuild text map if needed
     */
    public abstract void onFillUp();

    /**
     * what will happen durring the rendering
     */
    public abstract CharParams[][] onFrame(CharParams[][] canvas);

    public abstract void onResetVariables();

    public ArrayList<String> selectiveSearch(){
        ArrayList<String> selected = new ArrayList<>();
        int charCount = 0;
        while (charCount < this.neededChars){
            String choice = this.data.get((int)(Math.random()*data.size()));
            if(selected.contains(choice)) continue;
            selected.add(choice);
            charCount += choice.length();
        }
        System.out.println(charCount);
        System.out.println(CharRenderer.STANDARD_CANVAS_CC);
        return selected;
    }

    public static double distanceTo2D(int x1, int y1, InfectionCenter center){
        return Math.sqrt(Math.pow(center.x - x1,2) + Math.pow(center.y - y1,2));
    }

    public boolean isRenderComplete() {
        return renderComplete;
    }


}
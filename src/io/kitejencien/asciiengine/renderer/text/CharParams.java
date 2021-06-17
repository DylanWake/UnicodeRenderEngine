package io.kitejencien.asciiengine.renderer.text;

import java.awt.*;

public class CharParams{
    //options
    public double chanceOfLiving;
    public char content;
    public Color color;

    public CharParams(char content, double chanceOfLiving){
        this.content = content;
        this.chanceOfLiving = chanceOfLiving;
    }

    @Override
    public String toString() {
        return "CharParams{" +
                ", chanceOfLiving=" + chanceOfLiving +
                ", content=" + content +
                ", color=" + color +
                '}';
    }
}

package io.kitejencien.asciiengine.database;

import io.kitejencien.asciiengine.renderer.CharRenderer;

import java.util.Arrays;

/**
 * @author kitejencien
 */
public class CharData {

    private final int id;
    private final char content;
    private final double brightness;
    private final double[][] sampleMap;

    public CharData(int id, char content, double brightness, double[][] sampleMap){
        this.id = id;
        this.content = content;
        this.brightness = brightness;
        this.sampleMap = sampleMap;
    }

    public CharData(String input){

        String[] parts = input.split(" ");

        this.id = Integer.parseInt(parts[0]);
        this.content = parts[1].charAt(0);
        this.brightness = Double.parseDouble(parts[2]);

        double[][] samples = new double[CharRenderer.SAMPLE_SIZE][CharRenderer.SAMPLE_SIZE];
        for(int x=0; x < CharRenderer.SAMPLE_SIZE; x++){
            for(int y=0; y < CharRenderer.SAMPLE_SIZE; y++){
                samples[x][y] = Double.parseDouble(parts[3 + x*CharRenderer.SAMPLE_SIZE + y]);
            }
        }
        this.sampleMap = samples;
    }

    public int getId() {
        return id;
    }

    public char getContent() {
        return content;
    }

    public double getBrightness() {
        return brightness;
    }

    public double[][] getSampleMap() {
        return sampleMap;
    }

    @Override
    public String toString() {
        return String.valueOf(content);
    }
}

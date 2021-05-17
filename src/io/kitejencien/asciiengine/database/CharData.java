package io.kitejencien.asciiengine.database;

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
}

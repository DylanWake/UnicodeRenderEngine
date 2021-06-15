package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class TextStream{
    public int x;
    public int y;
    public int stepPerSecond;
    public int length;
    public String content;

    public TextStream( int stepPerSecond, int length, String content) {
        this.stepPerSecond = stepPerSecond;
        this.length = length;
        this.content = content;
    }
}

public class TextRenderer extends CharRenderer{

    public static final double GENERATION_PROBABILITY = 0.35;
    public static final int FRAME_UPDATE_RATE = 2;

    public String[] keywords;
    public int counter = 0;
    public ArrayList<TextStream> streams = new ArrayList<>();
    public ArrayList<TextStream> runningStreams = new ArrayList<>();

    public TextRenderer(String... keywords){
        this.keywords = keywords;
    }

    public void onLoadData(){
        try {
            for (String keyword: keywords) {
                File file = new File("textData/" + keyword +"_OUTPUTS.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String current;
                while ((current = reader.readLine()) != null) {
                    this.streams.add(new TextStream((int) (new Random().nextDouble() * 10), current.length(), current));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public double calculateCost(int[][] pixels, CharData current) {
        return 0;
    }

    @Override
    public void onRender(BufferedImage input) {

        if (canvas == null){
            canvasHeight = CANVAS_WIDTH*9 / 16;
            canvas = new char[CANVAS_WIDTH][CANVAS_WIDTH*9 / 16];
            for (int i = 0; i < CANVAS_WIDTH; i++) {
                for (int j = 0; j < canvasHeight; j++) {
                    canvas[i][j] = ' ';
                }
            }
        }

        // random generating
        if(counter % FRAME_UPDATE_RATE == 0) {
            for (int i = 1; i < canvas[0].length - 1; i++) {
                if (canvas[canvas.length-1][i-1] == ' ' && canvas[canvas.length - 1][i] == ' '
                        && canvas[canvas.length - 1][i + 1] == ' ') {
                    if (new Random().nextDouble() < GENERATION_PROBABILITY) {
                        TextStream selected = streams.get(new Random().nextInt(streams.size()));
                        streams.remove(selected);
                        selected.x = canvas.length;
                        selected.y = i;
                        runningStreams.add(selected);
                    }
                }
            }

            ArrayList<TextStream> toRemove = new ArrayList<>();
            //update streams
            runningStreams.forEach(stream -> {
                stream.x -= stream.stepPerSecond;
                if (stream.x + stream.length < 0) {
                    streams.add(stream);
                    toRemove.add(stream);
                }
            });

            toRemove.forEach(textStream -> {
                runningStreams.remove(textStream);
            });
        }

        //render streams
        runningStreams.forEach(stream -> {
            for (int i = 0; i < stream.length; i++) {
                if (stream.x + i > 0 && stream.x + i < canvas.length){
                    canvas[stream.x + i][stream.y] = stream.content.charAt(i);
                    for (int j = 1; j <= stream.stepPerSecond; j++) {
                        if (stream.x + i+j < canvas.length) canvas[stream.x+ i + j][stream.y] = ' ';
                    }
                }
            }
        });
        counter++;
    }
}
package io.kitejencien.asciiengine.renderer;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.io.DataBaseUtils;

import java.io.IOException;

/**
 * @author KiteJencien
 *
 * 1:  What story you want to talk about?
 * 2:  What idea you want to convey?
 * 3:  Why this makes you unique?
 *
 * Presonal Statement Ideas :
 *
 *     1: maybe stories about my oberland trips, experiences about art, experiences about using
 *        technological means to realize art approaches ...
 *
 *     2:
 *        1) : My point about art as the method of creating resonances betweent the artists and
 *        the audiences, and a successful artwork should not be a nobel and faraway "masterpiece"
 *        that requires pages of documentations to comprehend. An artwork can be complex in expressions,
 *        but all the feelings or ideas it conveys should be straightforward or be leading to the
 *        comprehendable outcome. Many artworks today are simply constructed with the aim to be at an unreachable level
 *        of expression that seems profound but actually gives no bullshit.
 *        DON'T CREATE ART FOR CREATING ART
 *
 *        In games or movies, there is only the experiences, the interactions, there is no space
 *        for a long FUCKING DOCUMENTATION
 *
 *     3:
 *        The point itself, this is against the mainstream of the comprehension of art today
 */
public class SampleMapRenderer extends CharRenderer{
    @Override
    public void onLoadData() {
        try {
            this.dataSet = DataBaseUtils.readLines(0, 77);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public double calculateCost(int[][] pixels, CharData current) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(int[] i : pixels){
            for(int j : i){
               max = Math.max(max, j);
               min = Math.min(min, j);
            }
        }

        double cost = 0;
        for(int i = 0; i < SAMPLE_SIZE; i++){
            for(int j = 0; j < SAMPLE_SIZE; j++){
                cost += Math.pow(((double) (pixels[i][j] - min)/(max-min))
                        - current.getSampleMap()[i][j],2);
            }
        }
        return cost * Math.abs(current.getBrightness() - calcBrightness(pixels));
    }
}

package io.kitejencien.asciiengine.display;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.renderer.BrightnessRendererUnicode;
import io.kitejencien.asciiengine.renderer.CharRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

/**
 * @author KiteJencien
 */
public class ImageOutputs {

    CharRenderer renderer;
    public static final int FONT_SIZE = 20;
    public static final int MARGIN = 10;

    public ImageOutputs(CharRenderer renderer){
        this.renderer = renderer;
    }

    public BufferedImage renderImage(BufferedImage in){
        renderer.onRender(in);

        BufferedImage image = new BufferedImage(CharRenderer.CANVAS_WIDTH * FONT_SIZE  + MARGIN * 2,
                renderer.canvasHeight * FONT_SIZE + MARGIN * 2, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, image.getWidth(), image.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体",Font.BOLD,FONT_SIZE));

        for(int y = 0; y < renderer.canvas[0].length; y++){
            for (int x = 0; x < renderer.canvas.length; x++) {
                g.drawString(String.valueOf(renderer.canvas[x][y]),
                        MARGIN + x*FONT_SIZE,
                        MARGIN + (int)(y*FONT_SIZE));
            }
        }

        return image;
    }
}

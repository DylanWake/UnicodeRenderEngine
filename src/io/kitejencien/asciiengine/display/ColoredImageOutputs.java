package io.kitejencien.asciiengine.display;

import io.kitejencien.asciiengine.renderer.CharRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author KiteJencien
 */
public class ColoredImageOutputs extends ImageOutputs{

    public ColoredImageOutputs(CharRenderer renderer) {
        super(renderer);
    }

    @Override
    public BufferedImage renderImage(BufferedImage in) {
        renderer.onRender(in);

        BufferedImage image = new BufferedImage(CharRenderer.CANVAS_WIDTH * FONT_SIZE  + MARGIN * 2,
                CharRenderer.CANVAS_HEIGHT_STANDARD * FONT_SIZE + MARGIN * 2, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, image.getWidth(), image.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体",Font.BOLD,FONT_SIZE));

        for(int y = 0; y < renderer.frame[0].length; y++){
            for (int x = 0; x < renderer.frame.length; x++) {
                g.setColor(renderer.frame[x][y].color == null ? Color.white : renderer.frame[x][y].color);
                g.drawString(String.valueOf(renderer.frame[x][y].content),
                        MARGIN + x*FONT_SIZE,
                        MARGIN + y*FONT_SIZE);
            }
        }
        return image;
    }
}

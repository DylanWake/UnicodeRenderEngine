package io.kitejencien.asciiengine.io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author KiteJencien
 */
public class ImageUtils {

    public static BufferedImage readImage(String pathName) throws IOException {
        return ImageIO.read(new File(pathName));
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        return new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
    }

    public static int[][][] constructImageArray(BufferedImage image){

        int[][][] output = new int[image.getWidth()][image.getHeight()][];
        for (int x = 0; x < image.getWidth(); x++){
            for(int y=0; y < image.getHeight(); y++){
                output[x][y] = convertRGB(image.getRGB(x, y));
            }
        }
        return output;
    }

    public static int[] convertRGB(int clr){
        int r =   (clr & 0x00ff0000) >> 16;
        int g = (clr & 0x0000ff00) >> 8;
        int b =   clr & 0x000000ff;
        return new int[]{r,g,b};
    }
}

package io.kitejencien.asciiengine.display;

/**
 * @author KiteJencien
 */
public class TerminalDisplay implements Display{

    @Override
    public void onUpdate(char[][] canvas) {
        System.out.println("\33[2J");

        for(int y = 0; y < canvas[0].length; y++){
            for (char[] canva : canvas) {
                System.out.print(canva[y]);
            }
            System.out.println();
        }
    }
}

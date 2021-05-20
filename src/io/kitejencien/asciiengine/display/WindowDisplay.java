package io.kitejencien.asciiengine.display;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;

/**
 * @author KiteJencien
 */
class DisplayFrame extends JFrame{
    DisplayFrame(WindowDisplay display){
        super("Renderer");
        setLayout(null);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setBackground(Color.black);
    }
}

class DisplayPanel extends JPanel{

    WindowDisplay display;
    int FONT_SIZE = 10;
    int PIXEL_CHARACTER_SEPATATION = 10;
    int MARGIN = 15;

    public DisplayPanel(WindowDisplay display){
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setBackground(Color.black);
        this.display = display;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体",Font.PLAIN,FONT_SIZE));

        if(display.canvas == null) return;

        for(int y = 0; y < display.canvas[0].length; y++){
            for (int x = 0; x < display.canvas.length; x++) {
                g.drawString(String.valueOf(display.canvas[x][y]),
                        MARGIN + x*PIXEL_CHARACTER_SEPATATION,
                        MARGIN + (int)(y*PIXEL_CHARACTER_SEPATATION*1.4));
            }
        }
    }
}

public class WindowDisplay implements Display{

    public char[][] canvas;
    public DisplayFrame frame;

    public WindowDisplay(){
        this.frame = new DisplayFrame(this);
        this.frame.add(new DisplayPanel(this));
        this.frame.setVisible(true);
        new Thread(() -> {
            while (true){
                frame.repaint();
                try {
                    Thread.sleep(20);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onUpdate(char[][] canvas) {
         this.canvas = canvas;
    }
}

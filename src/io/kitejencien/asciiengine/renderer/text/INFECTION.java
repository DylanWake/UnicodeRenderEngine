package io.kitejencien.asciiengine.renderer.text;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author KiteJencien
 */
public class INFECTION extends TextEffects{
    public ArrayList<InfectionCenter> infectionCenters = new ArrayList<>();
    public final int infectionBufferRange = 10;
    int count = 0;

    public INFECTION(){
        super();
    }

    @Override
    public void onFillUp() {
        StringBuilder contentB = new StringBuilder();
        selectiveSearch().forEach(contentB::append);
        String content = contentB.toString();
        content += content;
        for (int i = 0; i < durationMap.length; i++) {
            for (int j = 0; j < durationMap[0].length; j++) {
                durationMap[i][j] = new CharParams(content.charAt(i*durationMap.length + j), Math.random());
                durationMap[i][j].color = Color.WHITE;
            }
        }
        infectionCenters.add(new InfectionCenter((int)(Math.random()*durationMap.length),
                (int)(Math.random()*durationMap[0].length)));
        count++;
    }

    @Override
    public CharParams[][] onFrame(CharParams[][] canvas) {
        for (int i = 0; i < durationMap.length; i++) {
            for (int j = 0; j < durationMap[0].length; j++) {
                canvas[i][j] = durationMap[i][j];
                for (InfectionCenter infectionCenter : infectionCenters) {
                    double distance = distanceTo2D(i,j,infectionCenter);
                    if(distance < infectionCenter.affectionRadius + infectionBufferRange
                            && distance >= infectionCenter.affectionRadius){

                        if(canvas[i][j].content != ' ' && !canvas[i][j].color.equals(Color.RED)
                                && (canvas[i][j].chanceOfLiving > (-(infectionCenter.affectionRadius - distance))/(double)infectionBufferRange)){
                            canvas[i][j].color = Color.RED;
                        }
                    }

                    if (distance < infectionCenter.affectionRadius - infectionBufferRange
                            && distance >= infectionCenter.decayRadius){
                        if(canvas[i][j].content != ' ' && !canvas[i][j].color.equals(Color.RED)){
                            canvas[i][j].color = Color.RED;
                        }
                    }

                    if (distance < infectionCenter.decayRadius && distance >= infectionCenter.decayRadius - infectionBufferRange){
                        if(canvas[i][j].content != ' '){
                            canvas[i][j] = canvas[i][j].chanceOfLiving > ((
                                    infectionCenter.decayRadius - distance)/(double)infectionBufferRange) ? canvas[i][j] : new CharParams(' ',1);
                        }
                    }

                    if ( distance < infectionCenter.decayRadius - infectionBufferRange){
                        if(canvas[i][j].content != ' '){
                            canvas[i][j] = new CharParams(' ',1);
                        }
                    }
                }
            }
        }
        infectionCenters.forEach(e -> {
            e.affectionRadius++;
            e.decayRadius++;
            if(e.decayRadius > canvas.length){
                renderComplete = true;
            }
        });
        count++;

        if (count%15 == 0) infectionCenters.add(new InfectionCenter((int)(Math.random()*durationMap.length),
                (int)(Math.random()*durationMap[0].length)));
        return canvas;
    }

    @Override
    public void onResetVariables() {
        this.infectionCenters = new ArrayList<>();
        this.count = 0;
    }
}

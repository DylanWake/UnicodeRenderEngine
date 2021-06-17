package io.kitejencien.asciiengine.renderer.text;


import java.awt.*;
/**
 * @author KiteJencien
 */
public class CONTROL extends TextEffects {
    private int _generationFront = 0;
    private final int _normalDuration = 80;
    private final int _decayDuration = 50;
    private final int _postDecay = 40;

    public CONTROL(){
        super();
    }

    @Override
    public void onFillUp() {
        StringBuilder contentB = new StringBuilder();
        selectiveSearch().forEach(contentB::append);
        String content = contentB.toString();
        content += content;
        //left wing:
        for (int i = durationMap.length/2 -1; i > -1; i--) {
            for (int j = 0; j < durationMap[0].length; j++) {
                if (durationMap.length/2 - i <= 2){
                    durationMap[i][j] =
                            new CharParams(keyWord.charAt(2 - (durationMap.length/2 - i)), Math.random());
                }
                if((durationMap.length/2 - i -3) %6 == 0 ){
                    durationMap[i][j] = new CharParams(' ', 1);
                    for (int k = 1; k < 6; k++) {
                        if (i-k > 0)
                            durationMap[i-k][j] = new CharParams(content.charAt((i-k)*durationMap.length + j), Math.random());
                    }
                    if (i-1 > 0)
                        durationMap[i-1][j].color = Color.GRAY;
                    if (i-2 > 0)
                        durationMap[i-2][j].color = Color.GRAY;
                    if (i-3 > 0)
                        durationMap[i-3][j].color = Color.WHITE;
                    if (i-4 > 0)
                        durationMap[i-4][j].color = Color.RED;
                    if (i-5 > 0)
                        durationMap[i-5][j].color = Color.RED;
                }
            }
        }
        for (int j = 0; j < durationMap[0].length; j++) {
            durationMap[durationMap.length/2][j] = new CharParams(keyWord.charAt(2), Math.random());
        }
        for (int i = durationMap.length/2 +1; i < durationMap.length; i++) {
            for (int j = 0; j < durationMap[0].length; j++) {
                if (i - durationMap.length/2 <= 2){
                    durationMap[i][j] = new CharParams(keyWord.charAt(2 - (i - durationMap.length/2)), Math.random());
                }
                if(( i +3 - durationMap.length/2) %6 == 0 ){
                    durationMap[i][j] = new CharParams(' ', 1);
                    for (int k = 1; k < 6; k++) {
                        if (i+k < durationMap.length)
                            durationMap[i+k][j] = new CharParams(content.charAt((i+k)*durationMap.length + j), Math.random());
                    }
                    if (i+1 < durationMap.length)
                        durationMap[i+1][j].color = Color.GRAY;
                    if (i+2 < durationMap.length)
                        durationMap[i+2][j].color = Color.GRAY;
                    if (i+3 < durationMap.length)
                        durationMap[i+3][j].color = Color.RED;
                    if (i+4 < durationMap.length)
                        durationMap[i+4][j].color = Color.WHITE;
                    if (i+5 < durationMap.length)
                        durationMap[i+5][j].color = Color.WHITE;
                }
            }
        }
    }

    @Override
    public CharParams[][] onFrame(CharParams[][] canvas) {
        for (int i = 0; i < durationMap.length; i++) {
            for (int j = 0; j < durationMap[0].length; j++) {
                canvas[i][j] = new CharParams(' ',1);
                if (durationMap[i][j].color == null){
                    canvas[i][j] = new CharParams(' ',1);
                    continue;
                }
                if((durationMap[i][j].color.equals(Color.RED) && j <= _generationFront + 30 )
                        || (durationMap[i][j].color.equals(Color.WHITE) && j <= _generationFront + 15 )
                        || (durationMap[i][j].color.equals(Color.GRAY) && j <= _generationFront)){
                    canvas[i][j] = durationMap[i][j];
                }
                if (j <= _generationFront - _normalDuration - i/3){
                    canvas[i][j].color = Color.RED;
                }
                int decayIndex =  _generationFront - _normalDuration - _decayDuration;
                if (j <= decayIndex && j > decayIndex - _postDecay ){
                    canvas[i][j] = canvas[i][j].chanceOfLiving > (-(j-decayIndex))/(double)_postDecay
                            ? canvas[i][j] : new CharParams(' ',1);
                }
                if (j <= decayIndex - _postDecay){
                    canvas[i][j] = new CharParams(' ',1);
                }
            }
        }
        _generationFront++;
        if (_generationFront - _normalDuration - _decayDuration - _postDecay > durationMap[0].length) this.renderComplete = true;
        return canvas;
    }

    @Override
    public void onResetVariables() {
        this._generationFront = 0;
    }
}

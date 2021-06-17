package io.kitejencien.asciiengine.renderer.text;

import java.awt.*;

/**
 * @author KiteJencien
 */
public class WAVE extends TextEffects {
    private final int _waveDuration = 30;
    private final int _enterDistance = 2;
    private final int _exitDistance = 20;
    private int _waveFront = 0;

    public WAVE(){
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
                durationMap[i][j] = new CharParams(content.charAt(i * durationMap.length + j), Math.random());
                durationMap[i][j].color = Color.WHITE;
            }
        }
    }

    @Override
    public CharParams[][] onFrame(CharParams[][] canvas) {
        for (int i = 0; i < durationMap.length; i++) {
            for (int j = 0; j < durationMap[0].length; j++) {
                if (j <= _waveFront + _enterDistance && j >= _waveFront) {
                    canvas[i][j] = durationMap[i][j].chanceOfLiving > (
                            (j - _waveFront)) / (double) _enterDistance ? durationMap[i][j] : new CharParams(' ', 1);
                } else if (j < _waveFront && j >= _waveFront - _waveDuration) {
                    canvas[i][j] = durationMap[i][j];
                } else if (j < _waveFront - _waveDuration && j >= _waveFront - _waveDuration - _exitDistance) {
                    canvas[i][j] = durationMap[i][j].chanceOfLiving > ((_waveFront - _waveDuration - j))
                            / (double) _exitDistance ? durationMap[i][j] : new CharParams(' ', 1);
                } else canvas[i][j] = new CharParams(' ', 1);
            }
        }
        _waveFront++;
        if (_waveFront - _waveDuration - _exitDistance > durationMap[0].length) this.renderComplete = true;
        return canvas;
    }

    @Override
    public void onResetVariables() {
        this._waveFront = 0;
    }
}

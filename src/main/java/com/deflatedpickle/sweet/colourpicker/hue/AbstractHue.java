package com.deflatedpickle.sweet.colourpicker.hue;

import com.deflatedpickle.sweet.colourpicker.AbstractCanvasHandle;
import com.deflatedpickle.sweet.colourpicker.brightness.AbstractBrightness;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractHue extends AbstractCanvasHandle {
    public AbstractBrightness brightness;

    public static float[][] colourList = {
            {1f, 0f, 0f},
            {1f, 0f, 1f},
            {0f, 0f, 1f},
            {0f, 1f, 1f},
            {0f, 1f, 0f},
            {1f, 1f, 0f}
    };

    public AbstractHue(Composite parent, int style) {
        super(parent, style);
    }
}

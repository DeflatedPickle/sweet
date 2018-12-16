package com.deflatedpickle.sweet.colourpicker.brightness;

import com.deflatedpickle.sweet.colourpicker.AbstractCanvasHandle;
import com.deflatedpickle.sweet.colourpicker.hue.AbstractHue;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractBrightness extends AbstractCanvasHandle {
    public AbstractHue hue;

    public AbstractBrightness(Composite parent, int style) {
        super(parent, style);
    }
}

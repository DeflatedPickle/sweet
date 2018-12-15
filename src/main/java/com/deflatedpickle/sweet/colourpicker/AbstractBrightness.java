package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.widgets.Composite;

abstract class AbstractBrightness extends AbstractCanvasHandle {
    public AbstractHue hueBar;

    public AbstractBrightness(Composite parent, int style) {
        super(parent, style);
    }
}

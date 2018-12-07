package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;

public class HueBar extends GLCanvas {
    private static GLData glData = new GLData();

    public HueBar(Composite parent, int style) {
        super(parent, style | SWT.NO_REDRAW_RESIZE, glData);
    }
}

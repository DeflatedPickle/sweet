package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ColourPicker extends Composite {
    BrightnessBox brightnessBox = new BrightnessBox(this, SWT.BORDER);
    // private HueBar hueBar = new HueBar(this, SWT.BORDER);

    public ColourPicker(Composite parent, int style) {
        super(parent, style);
        this.setLayout(new GridLayout());

        GridData gridData = new GridData();
        gridData.widthHint = 180;
        gridData.heightHint = 180;
        brightnessBox.setLayoutData(gridData);
    }

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);

        ColourPicker colourPicker = new ColourPicker(shell, SWT.NONE);
        colourPicker.pack();

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}

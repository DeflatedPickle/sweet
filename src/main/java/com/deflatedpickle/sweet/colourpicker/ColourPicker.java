package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ColourPicker extends Composite {
    private AbstractBrightness brightness;
    private AbstractHue hue;

    public enum BrightnessType {
        BOX
    }

    public enum HueType {
        BAR
    }

    public ColourPicker(Composite parent, int style, BrightnessType brightnessType, HueType hueType) {
        super(parent, style);
        this.setLayout(new GridLayout(2, false));

        switch (brightnessType) {
            case BOX:
                brightness = new BrightnessBox(this, SWT.BORDER);
                break;
        }

        switch (hueType) {
            case BAR:
                hue = new HueBar(this, SWT.BORDER);
                break;
        }

        brightness.hueBar = hue;

        GridData brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        brightnessGridData.widthHint = 180;
        brightnessGridData.heightHint = 180;
        brightness.setLayoutData(brightnessGridData);

        GridData hueGridData = new GridData(SWT.CENTER, SWT.FILL, false, true);
        hueGridData.widthHint = 20;
        hueGridData.heightHint = 180;
        hue.setLayoutData(hueGridData);
    }

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

        ColourPicker colourPicker = new ColourPicker(shell, SWT.NONE, BrightnessType.BOX, HueType.BAR);
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

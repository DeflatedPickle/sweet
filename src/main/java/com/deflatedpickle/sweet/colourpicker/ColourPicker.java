package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ColourPicker extends Composite {
    BrightnessBox brightnessBox = new BrightnessBox(this, SWT.BORDER);
    HueBar hueBar = new HueBar(this, SWT.BORDER);

    public ColourPicker(Composite parent, int style) {
        super(parent, style);
        this.setLayout(new GridLayout(2, false));

        brightnessBox.hueBar = hueBar;

        GridData brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        brightnessGridData.widthHint = 180;
        brightnessGridData.heightHint = 180;
        brightnessBox.setLayoutData(brightnessGridData);

        GridData hueGridData = new GridData(SWT.CENTER, SWT.FILL, false, true);
        hueGridData.widthHint = 20;
        hueGridData.heightHint = 180;
        hueBar.setLayoutData(hueGridData);
    }

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

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

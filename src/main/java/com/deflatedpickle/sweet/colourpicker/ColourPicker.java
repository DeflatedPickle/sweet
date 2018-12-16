package com.deflatedpickle.sweet.colourpicker;

import com.deflatedpickle.sweet.colourpicker.brightness.AbstractBrightness;
import com.deflatedpickle.sweet.colourpicker.brightness.BrightnessBox;
import com.deflatedpickle.sweet.colourpicker.brightness.BrightnessTriangle;
import com.deflatedpickle.sweet.colourpicker.hue.AbstractHue;
import com.deflatedpickle.sweet.colourpicker.hue.HueBar;
import com.deflatedpickle.sweet.colourpicker.hue.HueWheel;
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

    public enum BrightnessShape {
        BOX,
        RIGHT_ANGLE_TRIANGLE,
        ISOSCELES_TRIANGLE
    }

    public enum HueShape {
        BAR,
        HEXAGON,
        CIRCLE,
        SAW,
        WHEEL
    }

    public ColourPicker(Composite parent, int style, BrightnessShape brightnessShape, HueShape hueShape) {
        super(parent, style);
        this.setLayout(new GridLayout(2, false));

        GridData brightnessGridData;
        switch (brightnessShape) {
            case BOX:
                brightness = new BrightnessBox(this, SWT.BORDER);

                brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                brightnessGridData.widthHint = 180;
                brightnessGridData.heightHint = 180;
                break;

            case RIGHT_ANGLE_TRIANGLE:
                brightness = new BrightnessTriangle(this, SWT.BORDER, BrightnessTriangle.Location.LEFT);

                brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                brightnessGridData.widthHint = 180;
                brightnessGridData.heightHint = 180;
                break;

            case ISOSCELES_TRIANGLE:
                brightness = new BrightnessTriangle(this, SWT.BORDER, BrightnessTriangle.Location.CENTRE);

                brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                brightnessGridData.widthHint = 180;
                brightnessGridData.heightHint = 180;
                break;

            default:
                brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                break;
        }

        GridData hueGridData;
        switch (hueShape) {
            case BAR:
                hue = new HueBar(this, SWT.BORDER);

                hueGridData = new GridData(SWT.CENTER, SWT.FILL, false, true);
                hueGridData.widthHint = 20;
                hueGridData.heightHint = 180;
                break;

            case HEXAGON:
                hue = new HueWheel(this, SWT.BORDER, 6, false, false);

                hueGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                hueGridData.widthHint = 180;
                hueGridData.heightHint = 180;
                break;

            case CIRCLE:
                hue = new HueWheel(this, SWT.BORDER, 12, false, false);

                hueGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                hueGridData.widthHint = 180;
                hueGridData.heightHint = 180;
                break;

            case SAW:
                hue = new HueWheel(this, SWT.BORDER, 24, false, true);

                hueGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                hueGridData.widthHint = 180;
                hueGridData.heightHint = 180;
                break;

            case WHEEL:
                hue = new HueWheel(this, SWT.BORDER, 24, true, false);

                hueGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                hueGridData.widthHint = 180;
                hueGridData.heightHint = 180;
                break;

            default:
                hueGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                break;
        }

        brightness.hue = hue;
        brightness.setLayoutData(brightnessGridData);

        hue.setLayoutData(hueGridData);
    }

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

        ColourPicker colourPicker = new ColourPicker(shell, SWT.NONE, BrightnessShape.ISOSCELES_TRIANGLE, HueShape.WHEEL);
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

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
import org.lwjgl.opengl.GL11;

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

    public enum Location {
        LEFT,
        CENTRE,
        RIGHT
    }

    public ColourPicker(Composite parent, int style, BrightnessShape brightnessShape, HueShape hueShape, boolean combine) {
        super(parent, style);

        AbstractCanvasHandle combineCanvas;
        if (combine) {
            this.setLayout(new GridLayout(1, false));

            GridData combinedGridData;
            combineCanvas = new AbstractCanvasHandle(this, SWT.BORDER) {
                @Override
                public boolean isOverHandle() {
                    return super.isOverHandle();
                }

                @Override
                public void drawCanvas() {
                    GL11.glPushMatrix();
                    float scale;

                    switch (brightnessShape) {
                        case BOX:
                            scale = 0.55f;
                            GL11.glScalef(scale, scale, scale);
                            BrightnessBox.drawCanvas(null, 1.0f, 0f, 0f);
                            break;

                        case RIGHT_ANGLE_TRIANGLE:
                            scale = 0.55f;
                            GL11.glScalef(scale, scale, scale);
                            BrightnessTriangle.drawCanvas(Location.LEFT, 1.0f, 0f, 0f);
                            break;

                        case ISOSCELES_TRIANGLE:
                            GL11.glTranslatef(0, 0.17f, 0);
                            scale = 0.65f;
                            GL11.glScalef(scale, scale, scale);

                            BrightnessTriangle.drawCanvas(Location.CENTRE, 1.0f, 0f, 0f);
                            break;
                    }
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    switch (hueShape) {
                        case BAR:
                            HueBar.drawCanvas(20, AbstractHue.colourList);
                            break;

                        case HEXAGON:
                            HueWheel.drawCanvas(6, AbstractHue.colourList, false, false);
                            break;

                        case CIRCLE:
                            HueWheel.drawCanvas(24, AbstractHue.colourList, false, false);
                            break;

                        case SAW:
                            HueWheel.drawCanvas(24, AbstractHue.colourList, true, false);
                            break;

                        case WHEEL:
                            HueWheel.drawCanvas(72, AbstractHue.colourList, false, true);
                            break;
                    }
                    GL11.glPopMatrix();
                }

                @Override
                public void drawHandle() {
                    super.drawHandle();
                }
            };

            combinedGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
            combinedGridData.widthHint = 180;
            combinedGridData.heightHint = 180;

            combineCanvas.setLayoutData(combinedGridData);

            return;
        }
        else {
            this.setLayout(new GridLayout(2, false));
        }

        GridData brightnessGridData;
        switch (brightnessShape) {
            case BOX:
                brightness = new BrightnessBox(this, SWT.BORDER);

                brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                brightnessGridData.widthHint = 180;
                brightnessGridData.heightHint = 180;
                break;

            case RIGHT_ANGLE_TRIANGLE:
                brightness = new BrightnessTriangle(this, SWT.BORDER, Location.LEFT);

                brightnessGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
                brightnessGridData.widthHint = 180;
                brightnessGridData.heightHint = 180;
                break;

            case ISOSCELES_TRIANGLE:
                brightness = new BrightnessTriangle(this, SWT.BORDER, Location.CENTRE);

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
                hue = new HueWheel(this, SWT.BORDER, 72, true, false);

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

        ColourPicker colourPicker = new ColourPicker(shell, SWT.NONE, BrightnessShape.ISOSCELES_TRIANGLE, HueShape.WHEEL, true);
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

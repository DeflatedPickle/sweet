package com.deflatedpickle.sweet.colourpicker.brightness;

import com.deflatedpickle.sweet.colourpicker.ColourPicker;
import com.deflatedpickle.sweet.colourpicker.hue.AbstractHue;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.lwjgl.opengl.*;

import java.util.Arrays;

public class BrightnessBox extends AbstractBrightness {
    public BrightnessBox(Composite parent, int style) {
        super(parent, style);

        handleLocation = new float[]{1f, -1.25f};
        handleSize = new float[]{0.05f, 0.05f};
    }

    @Override
    public boolean isOverHandle() {
        float[] cursorLocation = this.cursorToGL();

        return (cursorLocation[0] + 0.05f > this.handleLocation[0] - this.handleSize[0] && cursorLocation[0] + 0.05f < this.handleLocation[0] + this.handleSize[0])
                && (cursorLocation[1] - 0.3f > this.handleLocation[1] - this.handleSize[1] && cursorLocation[1] - 0.3f < this.handleLocation[1] + this.handleSize[1]);
    }

    @Override
    public void drawCanvas() {
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex2i(-1, -1);
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex2i(1, -1);
        GL11.glColor3f(hue.red, hue.green, hue.blue);
        GL11.glVertex2i(1, 1);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex2i(-1, 1);
        GL11.glEnd();
    }

    @Override
    public void drawHandle() {
        ColourPicker.squareHandle(this);
    }

    @Override
    public void boundingBox() {
        path.addRectangle(-0.5f, -0.5f, 1.5f, 1.5f);
    }
}

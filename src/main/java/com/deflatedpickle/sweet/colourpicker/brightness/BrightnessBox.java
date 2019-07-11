package com.deflatedpickle.sweet.colourpicker.brightness;

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
        // float scalingFixtureX = getClientArea().width / 180f;
        // float scalingFixtureY = getClientArea().height / 180f;
        float scalingFixtureX = 1;
        float scalingFixtureY = 1;

        GL11.glBegin(GL11.GL_LINE_LOOP);
        // GL11.glColor3i(1, 1, 1);
        this.invertHandleColour();
        GL11.glVertex2f(handleLocation[0], -handleLocation[1] - 0.35f / scalingFixtureY); // Bottom Left
        GL11.glVertex2f(handleLocation[0] + -0.09f / scalingFixtureX, -handleLocation[1] - 0.35f / scalingFixtureY); // Bottom Right
        GL11.glVertex2f(handleLocation[0] + -0.09f / scalingFixtureX, -handleLocation[1] - 0.25f / scalingFixtureY); // Top Right
        GL11.glVertex2f(handleLocation[0], -handleLocation[1] - 0.25f / scalingFixtureY); // Top Left
        GL11.glEnd();
    }

    @Override
    public void boundingBox() {
        super.boundingBox();

        Point location = this.getLocation();
        Point size = this.getSize();
        float[] newLocation = deviceToGL(location.x, location.y, size.x, size.y);
        float[] newSize = deviceToGL(size.x, size.y, size.x, size.y);
        path.addRectangle(newLocation[0], newLocation[1], newSize[0] * 2, newSize[1] * 2);
    }
}

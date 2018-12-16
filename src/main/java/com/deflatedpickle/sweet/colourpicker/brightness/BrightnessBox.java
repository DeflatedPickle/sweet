package com.deflatedpickle.sweet.colourpicker.brightness;

import com.deflatedpickle.sweet.colourpicker.hue.AbstractHue;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.lwjgl.opengl.*;

public class BrightnessBox extends AbstractBrightness {
    // Bottom = 0.65f | Top   = -1.25f (OpenGL)
    // Left   = -0.9f | Right = 1f
    // Bottom = 180   | Top   = 0      (Canvas)
    private float[] pointerLocation = new float[] {1f, -1.25f};

    public BrightnessBox(Composite parent, int style) {
        super(parent, style);
    }

    @Override
    public boolean isOverHandle() {
        Rectangle clientArea = this.getClientArea();
        Point location = this.getLocation();
        Rectangle newLocation = new Rectangle(location.x + clientArea.width - 15, location.y - 5, 10, 10);

        Point cursorLocation = Display.getCurrent().getFocusControl().toControl(Display.getCurrent().getCursorLocation());

        return newLocation.contains(cursorLocation.x, cursorLocation.y);
    }

    public static void drawCanvas(AbstractHue hue, float red, float green, float blue) {
        if (hue != null) {
            red = hue.red;
            green = hue.green;
            blue = hue.blue;
        }

        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex2i(-1, -1);
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex2i(1, -1);
        GL11.glColor3f(red, green, blue);
        GL11.glVertex2i(1, 1);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex2i(-1, 1);
        GL11.glEnd();
    }

    @Override
    public void drawCanvas() {
        drawCanvas(this.hue, this.red, this.green, this.blue);
    }

    @Override
    public void drawHandle() {
        float scalingFixtureX = getClientArea().width / 180f;
        float scalingFixtureY = getClientArea().height / 180f;

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor3i(1, 1, 1);
        GL11.glVertex2f(pointerLocation[0], -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Left
        GL11.glVertex2f(pointerLocation[0] + -0.09f / scalingFixtureX, -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Right
        GL11.glVertex2f(pointerLocation[0] + -0.09f / scalingFixtureX, -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Right
        GL11.glVertex2f(pointerLocation[0], -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Left
        GL11.glEnd();
    }
}

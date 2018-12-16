package com.deflatedpickle.sweet.colourpicker.hue;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class HueBar extends AbstractHue {
    // Bottom = 0.65f | Top = -1.25f (OpenGL)
    // Bottom = 180   | Top = 0      (Canvas)
    private float[] pointerLocation = new float[]{0, -1.25f};

    public HueBar(Composite parent, int style) {
        super(parent, style);

        this.red = 1f;
    }

    @Override
    public boolean isOverHandle() {
        Rectangle clientArea = this.getClientArea();
        Point location = this.getLocation();
        Rectangle newLocation = new Rectangle(location.x - (clientArea.width / 4), location.y - 5, clientArea.width, 10);

        Point cursorLocation = Display.getCurrent().getFocusControl().toControl(Display.getCurrent().getCursorLocation());

        return newLocation.contains(cursorLocation.x, cursorLocation.y);
    }

    @Override
    public void drawCanvas() {
        float flo = 0f;

        GL11.glLineWidth(getClientArea().width);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
        GL11.glVertex2f(flo, 1); // Top Left

        GL11.glColor3f(1.0f, 0.0f, 1.0f); // Purple
        GL11.glVertex2f(flo, 0.8f);

        GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        GL11.glVertex2f(flo, 0.4f);

        GL11.glColor3f(0.0f, 1.0f, 1.0f); // Cyan
        GL11.glVertex2f(flo, 0.2f);

        GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
        GL11.glVertex2f(flo, -0.0f);

        GL11.glColor3f(1.0f, 1.0f, 0.0f); // Yellow
        GL11.glVertex2f(flo, -0.4f);

        GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
        GL11.glVertex2f(flo, -1f); // Bottom Left

        GL11.glEnd();
    }

    @Override
    public void drawHandle() {
        float scalingFixtureY = getClientArea().height / 180f;

        GL11.glLineWidth(1f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor3i(1, 1, 1);
        GL11.glVertex2f(pointerLocation[0] - 1f, -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Left
        GL11.glVertex2f(pointerLocation[0] + 1f, -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Right
        GL11.glVertex2f(pointerLocation[0] + 1f, -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Right
        GL11.glVertex2f(pointerLocation[0] - 1f, -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Left
        GL11.glEnd();
    }
}

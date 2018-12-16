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

    public static void drawCanvas(int width, float[][] colourList) {
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        float[] elevationList = {1, 0.8f, 0.4f, 0.2f, 0.0f, -0.4f, -1};
        for (int i = 0; i < 7; i++) {
            if (i == 6) {
                GL11.glColor3f(colourList[0][0], colourList[0][1], colourList[0][2]);
            }
            else {
                GL11.glColor3f(colourList[i][0], colourList[i][1], colourList[i][2]);
            }
            GL11.glVertex2f(0, elevationList[i]);
        }

        GL11.glEnd();
    }

    @Override
    public void drawCanvas() {
        drawCanvas(this.getClientArea().width, this.colourList);
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

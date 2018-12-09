package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL;

public class BrightnessBox extends ScalingGLCanvas {
    private float red;
    private float green;
    private float blue;

    private boolean isFirst = true;

    public HueBar hueBar;

    private Point pointerLocation = new Point(0, 0);

    public BrightnessBox(Composite parent, int style) {
        super(parent, style);

        this.setCurrent();
        GL.createCapabilities();

        this.addListener(SWT.Resize, event -> resize());

        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        this.getDisplay().asyncExec(new Runnable() {
            public void run() {
                if (!canvas.isDisposed()) {
                    canvas.setCurrent();

                    if (isFirst) {
                        isFirst = false;

                        resize();
                    }

                    if (hueBar != null) {
                        red = hueBar.red;
                        green = hueBar.green;
                        blue = hueBar.blue;
                    }

                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

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

                    // Pointer
                    float scalingFixtureX = getClientArea().width / 180f;
                    float scalingFixtureY = getClientArea().height / 180f;

                    GL11.glBegin(GL11.GL_LINE_LOOP);
                    GL11.glColor3i(1, 1, 1);
                    GL11.glVertex2f(pointerLocation.x, -pointerLocation.y - 0.35f / scalingFixtureY); // Bottom Left
                    GL11.glVertex2f(pointerLocation.x + -0.09f / scalingFixtureX, -pointerLocation.y - 0.35f / scalingFixtureY); // Bottom Right
                    GL11.glVertex2f(pointerLocation.x + -0.09f / scalingFixtureX, -pointerLocation.y - 0.25f / scalingFixtureY); // Top Right
                    GL11.glVertex2f(pointerLocation.x, -pointerLocation.y - 0.25f / scalingFixtureY); // Top Left
                    GL11.glEnd();

                    canvas.swapBuffers();
                    getDisplay().asyncExec(this);
                }
            }
        });
    }
}

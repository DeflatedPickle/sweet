package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.stream.IntStream;

public class HueBar extends ScalingGLCanvas {
    private float[] pointerLocation = new float[] {0, 0};

    float red = 1f;
    float green = 0f;
    float blue = 0f;

    public HueBar(Composite parent, int style) {
        super(parent, style);

        this.setCurrent();
        GL.createCapabilities();

        // min - 0.65f, max = -1.25f

        this.addListener(SWT.Resize, event -> resize());

        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        this.getDisplay().asyncExec(new Runnable() {
            public void run() {
                if (!canvas.isDisposed()) {
                    canvas.setCurrent();

                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

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

                    // Pointer
                    float scalingFixtureY = getClientArea().height / 180f;

                    GL11.glLineWidth(1f);
                    GL11.glBegin(GL11.GL_LINE_LOOP);
                    GL11.glColor3i(1, 1, 1);
                    GL11.glVertex2f(pointerLocation[0] - 1f, -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Left
                    GL11.glVertex2f(pointerLocation[0] + 1f, -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Right
                    GL11.glVertex2f(pointerLocation[0] + 1f, -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Right
                    GL11.glVertex2f(pointerLocation[0] - 1f, -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Left
                    GL11.glEnd();

                    canvas.swapBuffers();
                    getDisplay().asyncExec(this);
                }
            }
        });
    }
}

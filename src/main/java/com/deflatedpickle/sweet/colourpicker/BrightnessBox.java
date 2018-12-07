package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL;

public class BrightnessBox extends GLCanvas {
    private BrightnessBox canvas = this;
    private static GLData glData = new GLData();

    public BrightnessBox(Composite parent, int style) {
        super(parent, style | SWT.NO_REDRAW_RESIZE, glData);

        this.setCurrent();
        GL.createCapabilities();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GL11.glViewport(0, 0, 180, 180);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        float red = 0.0f;
        float green = 1.0f;
        float blue = 0.2f;

        this.getDisplay().asyncExec(new Runnable() {
            public void run() {
                if (!canvas.isDisposed()) {
                    canvas.setCurrent();

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

                    canvas.swapBuffers();
                    getDisplay().asyncExec(this);
                }
            }
        });
    }
}

package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.stream.IntStream;

public class HueBar extends GLCanvas {
    private HueBar canvas = this;
    private static GLData glData = new GLData();

    public HueBar(Composite parent, int style) {
        super(parent, style | SWT.NO_REDRAW_RESIZE, glData);

        this.setCurrent();
        GL.createCapabilities();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GL11.glViewport(0, 0, 38, 180);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        this.getDisplay().asyncExec(new Runnable() {
            public void run() {
                if (!canvas.isDisposed()) {
                    canvas.setCurrent();

                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                    // TODO: Maybe only one line can be drawn, and the view can be stretched horizontally?
                    IntStream.range(0, 30).boxed().sorted(Collections.reverseOrder()).forEachOrdered(i -> {
                        float flo = -i.floatValue() / 30;

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
                    });

                    canvas.swapBuffers();
                    getDisplay().asyncExec(this);
                }
            }
        });
    }
}

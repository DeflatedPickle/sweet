package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL;

public class BrightnessBox extends ScalingGLCanvas {
    // Bottom = 0.65f | Top   = -1.25f (OpenGL)
    // Left   = -0.9f | Right = 1f
    // Bottom = 180   | Top   = 0      (Canvas)
    private float[] pointerLocation = new float[] {1f, -1.25f};

    private float red;
    private float green;
    private float blue;

    private boolean followMouse = false;

    private boolean isFirst = true;

    public HueBar hueBar;

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
                    GL11.glVertex2f(pointerLocation[0], -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Left
                    GL11.glVertex2f(pointerLocation[0] + -0.09f / scalingFixtureX, -pointerLocation[1] - 0.35f / scalingFixtureY); // Bottom Right
                    GL11.glVertex2f(pointerLocation[0] + -0.09f / scalingFixtureX, -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Right
                    GL11.glVertex2f(pointerLocation[0], -pointerLocation[1] - 0.25f / scalingFixtureY); // Top Left
                    GL11.glEnd();

                    canvas.swapBuffers();
                    getDisplay().asyncExec(this);
                }
            }
        });

        this.addMouseMoveListener(e -> {
            if (this.isOverHandle()) {
                this.setCursor(new Cursor(this.getDisplay(), SWT.CURSOR_HAND));
            }
            else {
                this.setCursor(new Cursor(this.getDisplay(), SWT.CURSOR_ARROW));
            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
            }

            @Override
            public void mouseDown(MouseEvent e) {
                // TODO: Make the slider follow the mouse
                if (isOverHandle()) {
                    followMouse = true;
                }
            }

            @Override
            public void mouseUp(MouseEvent e) {
                // Make the slider stop following the mouse
                followMouse = false;
            }
        });
    }

    private boolean isOverHandle() {
        Rectangle clientArea = this.getClientArea();
        Point location = this.getLocation();
        Rectangle newLocation = new Rectangle(location.x + clientArea.width - 15, location.y - 5, 10, 10);

        Point cursorLocation = Display.getCurrent().getFocusControl().toControl(Display.getCurrent().getCursorLocation());

        return newLocation.contains(cursorLocation.x, cursorLocation.y);
    }
}

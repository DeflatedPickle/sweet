package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class HueBar extends ScalingGLCanvas {
    // Bottom = 0.65f | Top = -1.25f (OpenGL)
    // Bottom = 180   | Top = 0      (Canvas)
    private float[] pointerLocation = new float[] {0, -1.25f};

    float red = 1f;
    float green = 0f;
    float blue = 0f;

    private boolean followMouse = false;

    public HueBar(Composite parent, int style) {
        super(parent, style);

        this.setCurrent();
        GL.createCapabilities();

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
        Rectangle newLocation = new Rectangle(location.x - (clientArea.width / 4), location.y - 5, clientArea.width, 10);

        Point cursorLocation = Display.getCurrent().getFocusControl().toControl(Display.getCurrent().getCursorLocation());

        return newLocation.contains(cursorLocation.x, cursorLocation.y);
    }
}

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

public abstract class AbstractCanvasHandle extends ScalingGLCanvas {
    protected float[] handleLocation = new float[2];
    protected float[] handleSize = new float[2];
    private float[] tempLoc = new float[2];

    private boolean isFirst = true;

    private boolean followMouse = false;

    public float red;
    public float green;
    public float blue;

    public AbstractCanvasHandle(Composite parent, int style) {
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

                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                    drawCanvas();

                    drawHandle();

                    canvas.swapBuffers();
                    getDisplay().asyncExec(this);
                }
            }
        });

        this.addMouseMoveListener(e -> {
            moveHandle();
            if (this.followMouse) {
                handleLocation[0] = tempLoc[0];
                handleLocation[1] = tempLoc[1];
            }

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
                followMouse = true;

                moveHandle();
                if (followMouse) {
                    handleLocation[0] = tempLoc[0];
                    handleLocation[1] = tempLoc[1];
                }
            }

            @Override
            public void mouseUp(MouseEvent e) {
                // Make the slider stop following the mouse
                followMouse = false;
            }
        });

    }

    protected boolean isOverHandle() {
        return false;
    }

    protected float[] cursorToGL() {
        Rectangle clientArea = this.getClientArea();

        Point cursorLocation = Display.getCurrent().getFocusControl().toControl(Display.getCurrent().getCursorLocation());
        return this.convertDeviceCoords((float) cursorLocation.x, (float) cursorLocation.y, clientArea.width, clientArea.height);
    }

    private void moveHandle() {
        float[] localLocation = cursorToGL();

        tempLoc[0] = handleLocation[0];
        tempLoc[1] = handleLocation[1];

        if (localLocation[0] > -0.95f && localLocation[0] < 0.95f) {
            tempLoc[0] = localLocation[0] + 0.05f;
        }

        if (localLocation[1] > -0.95f && localLocation[1] < 0.95f) {
            tempLoc[1] = localLocation[1] - 0.3f;
        }
    }

    public void drawCanvas() {
    }

    public void drawHandle() {
    }

    private float[] convertDeviceCoords(float x, float y, int width, int height) {
        return new float[]{(2f * x) / width - 1f, (2f * y) / height - 1f};
    }
}

package com.deflatedpickle.sweet.colourpicker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class AbstractCanvasHandle extends ScalingGLCanvas {
    private float[] pointerLocation = new float[] {0, 0};

    private boolean isFirst = true;

    private boolean followMouse = false;

    float red;
    float green;
    float blue;

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

    public boolean isOverHandle() {
        return false;
    }

    public void drawCanvas() {
    }

    public void drawHandle() {
    }
}

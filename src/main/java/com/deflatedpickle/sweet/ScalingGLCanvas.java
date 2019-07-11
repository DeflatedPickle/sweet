package com.deflatedpickle.sweet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.lwjgl.opengl.GL11;

public abstract class ScalingGLCanvas extends GLCanvas {
    public ScalingGLCanvas canvas = this;
    private static GLData glData = new GLData();

    public ScalingGLCanvas(Composite parent, int style) {
        super(parent, style, glData);
    }

    public void resize() {
        this.setCurrent();

        Rectangle area = this.getClientArea();

        GL11.glViewport(0, 0, area.width, area.height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
    }
}

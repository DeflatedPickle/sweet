package com.deflatedpickle.sweet.colourpicker.hue;

import org.eclipse.swt.widgets.Composite;
import org.lwjgl.opengl.GL11;

public class HueWheel extends AbstractHue {
    private int segments;
    private boolean hollow;
    private boolean saw;
    private float innerCircleSize;

    public HueWheel(Composite parent, int style, int segments, boolean hollow, boolean saw, float innerCircleSize) {
        super(parent, style);

        this.segments = segments;
        this.hollow = hollow;
        this.saw = saw;
        this.innerCircleSize = innerCircleSize;
    }

    public void drawCanvas() {
        if (hollow) {
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        }
        else {
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        }

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex2f(0f, 0f);

        for (int i = 0; i < segments + 1; i++) {
            drawPoint(i, segments);
        }

        GL11.glEnd();
    }

    private void drawPoint(int i, int segments) {
        if (segments < colourList.length + 1) {
            changeColour(i);
        }
        else {
            if (segments % colourList.length == 0) {
                // This, somehow, works out how many times the index needs to be repeated for the circle
                // I have no idea why it works, but it does
                // FIXME: Anything divisible by 6 above 12 doesn't interpolate well -- fix this
                int index = ((i * (segments / colourList.length)) / (segments / 3)) / (segments / 12);
                changeColour(index);
            }
            else {
                GL11.glColor3f(0f, 0f, 0f);
            }
        }

        // Draw the vertex
        if (saw || hollow) {
            GL11.glVertex2f(innerCircleSize * (float) Math.cos(i * (2f * Math.PI) / segments), innerCircleSize * (float) Math.sin(i * (2f * Math.PI) / segments));
        }

        GL11.glVertex2f((float) Math.cos(i * (2f * Math.PI) / segments), (float) Math.sin(i * (2f * Math.PI) / segments));
    }

    private void changeColour(int i) {
        if (i % 6 < colourList.length) {
            GL11.glColor3f(colourList[i % 6][0], colourList[i % 6][1], colourList[i % 6][2]);
        }
    }
}

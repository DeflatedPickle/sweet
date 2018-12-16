package com.deflatedpickle.sweet.colourpicker.hue;

import org.eclipse.swt.widgets.Composite;
import org.lwjgl.opengl.GL11;

public class HueWheel extends AbstractHue {
    private int segments;
    private boolean hollow;
    private boolean saw;

    public HueWheel(Composite parent, int style, int segments, boolean hollow, boolean saw) {
        super(parent, style);

        this.segments = segments;
        this.hollow = hollow;
        this.saw = saw;
    }

    public static void drawCanvas(int segments, float[][] colourList, boolean saw, boolean hollow) {
        if (hollow) {
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        }
        else {
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        }

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex2f(0f, 0f);

        for (int i = 0; i < segments + 1; i++) {
            drawPoint(i, segments, colourList, saw, hollow);
        }

        GL11.glEnd();
    }

    @Override
    public void drawCanvas() {
        drawCanvas(this.segments, this.colourList, this.saw, this.hollow);
    }

    private static void drawPoint(int i, int segments, float[][] colourList, boolean saw, boolean hollow) {
        if (segments < colourList.length + 1) {
            changeColour(i, colourList);
        }
        else {
            if (segments % colourList.length == 0) {
                // This, somehow, works out how many times the index needs to be repeated for the circle
                // FIXME: Anything divisible by 6 above 12 doesn't interpolate well -- fix this
                int index = ((i * (segments / colourList.length)) / (segments / 3)) / (segments / 12);
                changeColour(index, colourList);
            }
            else {
                GL11.glColor3f(0f, 0f, 0f);
            }
        }

        // Draw the vertex
        if (saw || hollow) {
            GL11.glVertex2f(0.8f * (float) Math.cos(i * (2f * Math.PI) / segments), 0.8f * (float) Math.sin(i * (2f * Math.PI) / segments));
        }

        GL11.glVertex2f((float) Math.cos(i * (2f * Math.PI) / segments), (float) Math.sin(i * (2f * Math.PI) / segments));
    }

    private static void changeColour(int i, float[][] colourList) {
        if (i % 6 < colourList.length) {
            GL11.glColor3f(colourList[i % 6][0], colourList[i % 6][1], colourList[i % 6][2]);
        }
    }
}

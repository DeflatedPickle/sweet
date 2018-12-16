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

    @Override
    public void drawCanvas() {
        if (this.hollow) {
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        }
        else {
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        }

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex2f(0f, 0f);

        for (int i = 0; i < this.segments + 1; i++) {
            this.drawPoint(i);
        }

        GL11.glEnd();
    }

    private void drawPoint(int i) {
        if (this.segments < this.colourList.length + 1) {
            this.changeColour(i);
        }
        else {
            if (this.segments % this.colourList.length == 0) {
                // This, somehow, works out how many times the index needs to be repeated for the circle
                // FIXME: Anything divisible by 6 above 12 doesn't interpolate well -- fix this
                int index = ((i * (this.segments / this.colourList.length)) / (this.segments / 3)) / (this.segments / 12);
                this.changeColour(index);
            }
            else {
                GL11.glColor3f(0f, 0f, 0f);
            }
        }

        // Draw the vertex
        if (this.saw || this.hollow) {
            GL11.glVertex2f(0.8f * (float) Math.cos(i * (2f * Math.PI) / this.segments), 0.8f * (float) Math.sin(i * (2f * Math.PI) / this.segments));
        }

        GL11.glVertex2f((float) Math.cos(i * (2f * Math.PI) / this.segments), (float) Math.sin(i * (2f * Math.PI) / this.segments));
    }

    private void changeColour(int i) {
        if (i % 6 < this.colourList.length) {
            GL11.glColor3f(colourList[i % 6][0], colourList[i % 6][1], colourList[i % 6][2]);
        }
    }
}

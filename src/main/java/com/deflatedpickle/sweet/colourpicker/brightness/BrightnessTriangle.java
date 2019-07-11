package com.deflatedpickle.sweet.colourpicker.brightness;

import com.deflatedpickle.sweet.colourpicker.ColourPicker;
import org.eclipse.swt.widgets.Composite;
import org.lwjgl.opengl.GL11;

public class BrightnessTriangle extends AbstractBrightness {
    private ColourPicker.Location tipLocation;

    public BrightnessTriangle(Composite parent, int style, ColourPicker.Location tipLocation) {
        super(parent, style);

        this.tipLocation = tipLocation;
        this.red = 1f;
    }

    @Override
    public void drawCanvas() {
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex2i(-1, -1); // Bottom

        float[][] colourPoint = new float[2][2];
        switch (tipLocation) {
            case LEFT:
                // White
                colourPoint[0][0] = 1f;
                colourPoint[0][1] = 1f;
                // Colour
                colourPoint[1][0] = -1f;
                colourPoint[1][1] = 1f;
                break;

            case CENTRE:
                // White
                colourPoint[0][0] = 1f;
                colourPoint[0][1] = -1f;
                // Colour
                colourPoint[1][0] = 0.1f;
                colourPoint[1][1] = 1f;
                break;

            case RIGHT:
                // White
                colourPoint[0][0] = -1f;
                colourPoint[0][1] = 1f;
                // Colour
                colourPoint[1][0] = 1f;
                colourPoint[1][1] = 1f;
        }

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex2f(colourPoint[0][0], colourPoint[0][1]); // Top Right
        GL11.glColor3f(red, green, blue);
        GL11.glVertex2f(colourPoint[1][0], colourPoint[1][1]); // Top Left

        GL11.glEnd();
    }
}

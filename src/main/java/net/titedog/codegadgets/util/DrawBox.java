package net.titedog.codegadgets.util;

import org.lwjgl.opengl.GL11;

public class DrawBox {
    public static void renderBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x1, y2, z1);

        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y2, z1);

        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x1, y2, z2);

        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x2, y2, z2);

        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y1, z1);

        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x2, y2, z1);

        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x2, y1, z2);

        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x2, y2, z2);

        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x1, y1, z2);

        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y1, z2);

        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x1, y2, z2);

        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x2, y2, z2);;
    }
}

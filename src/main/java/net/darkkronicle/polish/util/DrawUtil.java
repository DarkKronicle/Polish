package net.darkkronicle.polish.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringRenderable;
import net.minecraft.util.math.Matrix4f;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public abstract class DrawUtil {
    public DrawUtil() { }
    // Thanks Minecraft for not having any support for anything that isn't square <3
    // Thanks so much (for real) to LibGui for showing off some of their super cool code.

    // https://github.com/CottonMC/LibGui/blob/65ff902aaf7025f90efb739ab0cca985f2f59c59/src/main/java/io/github/cottonmc/cotton/gui/client/ScreenDrawing.java#L32

    public static void rect(MatrixStack matrices, int x, int y, int width, int height, int color) {
        rect(matrices.peek().getModel(), x, y, width, height, color);
    }

    public static void rect(Matrix4f matrix, int x, int y, int width, int height, int color) {
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)x, (float)y + height, 0.0F).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)x + width, (float)y + height, 0.0F).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)x + width, (float)y, 0.0F).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)x, (float)y, 0.0F).color(r, g, b, a).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void pixel(MatrixStack matrixStack, int x, int y, int color) {
        rect(matrixStack, x, y, 1, 1, color);
    }

    public static void outlineRect(MatrixStack matrices, int x, int y, int width, int height, int color) {
        /*
        1: |
        2:  |
        3: -
        4: _
         */
        rect(matrices, x, y, 1, height, color);
        rect(matrices, x + width - 1, y, 1, height, color);
        rect(matrices, x, y, width, 1, color);
        rect(matrices, x, y + height - 1, width, 1, color);
    }

    public static void outlineCircle(MatrixStack matrices, int x, int y, int r, int color) {
        // Should never be 0,0
        outlineCircleAngle(matrices, x, y, r, 0, 360, color);
    }

    public static void circle(MatrixStack matrices, int x, int y, int r, int color) {
        fillCircleAngle(matrices, x, y, r, 0, 360, color);
    }

    // Probably garbage for not adding some sort of cache...
    public static void outlineCircleAngle(MatrixStack matrices, int x, int y, int r, int start, int end, int color) {
        ArrayList<DrawPosition> pos = new ArrayList<>();
        int relativeY, relativeX;
        double x1, y1;
        for (int angle = start; angle < end; angle++) {
            x1 = r * Math.cos(angle * Math.PI / 180);
            y1 = r * Math.sin(angle * Math.PI / 180);
            relativeX = (int) Math.round(x1 + x);
            relativeY = (int) Math.round(y1 + y);
            DrawPosition position = new DrawPosition(relativeX, relativeY);
            if (!pos.contains(position)) {
                pos.add(position);
                pixel(matrices, relativeX, relativeY, color);
            }
        }
    }

    public static void fillCircleAngle(MatrixStack matrices, int x, int y, int r, int start, int end, int color) {
        ArrayList<DrawPosition> pos = new ArrayList<>();
        int relativeY;
        double x1, y1;
        for (int angle = start; angle < end; angle++) {
            x1 = r * Math.cos(angle * Math.PI / 180);
            y1 = r * Math.sin(angle * Math.PI / 180);
            relativeY = (int) Math.round(y1 + y);
            if (x1 >= 0) {
                for (int fillX = (int) Math.round(x1); fillX >= 0; ) {
                    DrawPosition position = new DrawPosition(fillX + x, relativeY);
                    if (!pos.contains(position)) {
                        pos.add(position);
                        pixel(matrices, fillX + x, relativeY, color);
                    }
                    fillX--;
                }
            } else {
                for (int fillX = (int) Math.round(x1); fillX <= 0; ) {
                    DrawPosition position = new DrawPosition(fillX + x, relativeY);
                    if (!pos.contains(position)) {
                        pos.add(position);
                        pixel(matrices, fillX + x, relativeY, color);
                    }
                    fillX++;
                }
            }
        }
    }

    public static void fillRoundedRect(MatrixStack matrices, int x, int y, int width, int height, int r, int color) {
        rect(matrices, x + r + 1, y, width - r - r - 2, height, color);
        rect(matrices, x, y + r + 1, r + 1, height - r - r - 2, color);
        rect(matrices, x + width - 1 - r, y + r + 1, r + 1, height - r - r - 2, color);
        fillCircleAngle(matrices, x + r, y + r, r, 180, 270, color);
        fillCircleAngle(matrices, x + width - r - 1, y + r, r, 270, 360, color);
        fillCircleAngle(matrices, x + width - r - 1, y + height - r - 1, r, 0, 90, color);
        fillCircleAngle(matrices, x + r, y + height - r - 1, r, 90, 180, color);
    }

    public static void fillLeftRoundedRect(MatrixStack matrices, int x, int y, int width, int height, int r, int color) {
        rect(matrices, x + r + 1, y, width - r - 2, height, color);
        rect(matrices, x, y + r + 1, r + 1, height - r - r - 2, color);
        fillCircleAngle(matrices, x + r, y + r, r, 180, 270, color);
        fillCircleAngle(matrices, x + r, y + height - r - 1, r, 90, 180, color);
    }

    public static void fillRightRoundedRect(MatrixStack matrices, int x, int y, int width, int height, int r, int color) {
        rect(matrices, x + 1, y, width - r - 2, height, color);
        rect(matrices, x + width - 1 - r, y + r + 1, r + 1, height - r - r - 2, color);
        fillCircleAngle(matrices, x + width - r - 1, y + r, r, 270, 360, color);
        fillCircleAngle(matrices, x + width - r - 1, y + height - r - 1, r, 0, 90, color);
    }


    public static void outlineRoundedRect(MatrixStack matrices, int x, int y, int width, int height, int r, int color) {
        rect(matrices, x + r + 1, y, width - r - r - 2, 1, color);
        rect(matrices, x, y + r + 1, 1, height - r - r - 2, color);
        rect(matrices, x + r + 1, y + height - 1, width - r - r - 2, 1, color);
        rect(matrices, x + width - 1, y + r + 1, 1, height - r - r - 2, color);
        outlineCircleAngle(matrices, x + r, y + r, r, 180, 270, color);
        outlineCircleAngle(matrices, x + width - r - 1, y + r, r, 270, 360, color);
        outlineCircleAngle(matrices, x + width - r - 1, y + height - r - 1, r, 0, 90, color);
        outlineCircleAngle(matrices, x + r, y + height - r - 1, r, 90, 180, color);
    }

    public static void drawCheckmark(MatrixStack matrices, int x, int y, int size, int color) {
        int lasty = 0;
        for (int curx = size; curx > 0; curx--) {
            if (lasty > size) {
                lasty--;
            }
            if (curx < ((float) size / 3)) {
                pixel(matrices, x + curx, y + lasty, color);
                lasty--;
            } else {
                pixel(matrices, x + curx, y + lasty, color);
                lasty++;
            }
        }
    }

    public static void drawLeftArrow(MatrixStack matrices, int x, int y, int size, int color) {
        int width = (int) Math.ceil((double) size / 2);
        int lastx = width;
        for (int cury = size; cury > 0; cury--) {
            pixel(matrices, x + lastx, y + cury - 1, color);
            if (cury <= width) {
                lastx++;
            } else {
                lastx--;
            }
        }
    }

    public static void drawRightArrow(MatrixStack matrices, int x, int y, int size, int color) {
        int width = (int) Math.ceil((double) size / 2);
        int lastx = 0;
        for (int cury = size; cury > 0; cury--) {
            pixel(matrices, x + lastx, y + cury - 1, color);
            if (cury > width) {
                lastx++;
            } else {
                lastx--;
            }
        }
    }


    public static void drawCenteredString(MatrixStack matrices, TextRenderer textRenderer, String text, int x, int y, int color) {
        textRenderer.drawWithShadow(matrices, text, (float)(x - textRenderer.getWidth(text) / 2), (float)y, color);
    }

    public static void drawCenteredText(MatrixStack matrices, TextRenderer textRenderer, StringRenderable stringRenderable, int x, int y, int color) {
        textRenderer.drawWithShadow(matrices, stringRenderable, (float)(x - textRenderer.getWidth(stringRenderable) / 2), (float)y, color);
    }

    public static void drawText(MatrixStack matrices, TextRenderer textRenderer, StringRenderable stringRenderable, int x, int y, int color) {
        textRenderer.drawWithShadow(matrices, stringRenderable, x, (float)y, color);
    }

    public static void drawRightText(MatrixStack matrices, TextRenderer textRenderer, StringRenderable stringRenderable, int x, int y, int color) {
        textRenderer.drawWithShadow(matrices, stringRenderable, (float) (x - textRenderer.getWidth(stringRenderable)), (float)y, color);
    }

}

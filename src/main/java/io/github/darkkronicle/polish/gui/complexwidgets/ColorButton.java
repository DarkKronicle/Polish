package io.github.darkkronicle.polish.gui.complexwidgets;

import io.github.darkkronicle.polish.gui.widgets.IntSliderButton;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

/**
 * The type Color button.
 */
public class ColorButton extends AbstractPComplexWidget {

    /**
     * The red slider button.
     */
    private IntSliderButton r;
    /**
     * The green slider button.
     */
    private IntSliderButton g;
    /**
     * The blue slider button.
     */
    private IntSliderButton b;
    /**
     * The alpha slider button.
     */
    private IntSliderButton a;

    private MinecraftClient client;
    /**
     * The background color.
     */
    private SimpleColor backgroundColor;

    /**
     * Instantiates a new Color button.
     *
     * @param x               the x
     * @param y               the y
     * @param backgroundColor the background color
     */
    public ColorButton(int x, int y, SimpleColor backgroundColor) {
        super(x, y, 120, 50);
        this.client = MinecraftClient.getInstance();
        r = new IntSliderButton(getAbsoluteX() , getAbsoluteY(), 80, 100, 0, 255);
        r.setRelativePos(30, 2);
        g = new IntSliderButton(getAbsoluteX() , getAbsoluteY(), 80, 100, 0, 255);
        g.setRelativePos(30, 14);
        b = new IntSliderButton(getAbsoluteX() , getAbsoluteY(), 80, 100, 0, 255);
        b.setRelativePos(30, 26);
        a = new IntSliderButton(getAbsoluteX() , getAbsoluteY(), 80, 100, 0, 255);
        a.setRelativePos(30, 38);
        add(r).add(g).add(b).add(a);
        this.backgroundColor = backgroundColor;
    }

    /**
     * Gets color from all the sliders.
     *
     * @return the color
     */
    public SimpleColor getColor() {
        int rNum = r.getRawValue();
        int gNum = g.getRawValue();
        int bNum = b.getRawValue();
        int aNum = a.getRawValue();
        return new SimpleColor(rNum, gNum, bNum, aNum);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        fillRoundedRect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, 9, backgroundColor.color());
        rect(matrices, getAbsoluteX() + 1, getAbsoluteY() + 11, 20, 28, getColor().color());
        outlineRect(matrices, getAbsoluteX() + 1, getAbsoluteY() + 11, 20, 28, Colors.BLACK.color().color());
        drawCenteredString(matrices, client.textRenderer, "R", getAbsoluteX() + 25, getAbsoluteY() + 3, Colors.WHITE.color().color());
        drawCenteredString(matrices, client.textRenderer, "G", getAbsoluteX() + 25, getAbsoluteY() + 15, Colors.WHITE.color().color());
        drawCenteredString(matrices, client.textRenderer, "B", getAbsoluteX() + 25, getAbsoluteY() + 27, Colors.WHITE.color().color());
        drawCenteredString(matrices, client.textRenderer, "A", getAbsoluteX() + 25, getAbsoluteY() + 39, Colors.WHITE.color().color());
        matrices.pop();
    }
}

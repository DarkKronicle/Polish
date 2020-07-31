package net.darkkronicle.polish.gui.complexwidgets;

import net.darkkronicle.polish.gui.widgets.IntSliderButton;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class ColorButton extends AbstractPComplexWidget {

    private IntSliderButton r;
    private IntSliderButton g;
    private IntSliderButton b;
    private IntSliderButton a;
    private MinecraftClient client;
    private SimpleColor backgroundColor;

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

    public SimpleColor getColor() {
        int rNum = r.getRawValue();
        int gNum = g.getRawValue();
        int bNum = b.getRawValue();
        int aNum = a.getRawValue();
        return new SimpleColor(rNum, gNum, bNum, aNum);
    }

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

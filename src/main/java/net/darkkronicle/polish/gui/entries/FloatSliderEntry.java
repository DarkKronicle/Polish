package net.darkkronicle.polish.gui.entries;

import net.darkkronicle.polish.api.AbstractPEntry;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.widgets.FloatSliderButton;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.DrawUtil;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class FloatSliderEntry extends AbstractPEntry<Float, FloatSliderButton> {

    private FloatSliderEntry(int relativeX, int relativeY, int width, int height, FloatSliderButton widget, Text name, EntryButtonList list) {
        super(relativeX, relativeY, width, height, widget, name, list);
    }

    @Override
    public void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scrolled, boolean hovered) {
        widget.setRelativePos(originalRelativeX + 2, originalRelativeY + scrolled);
        if (hovered) {
            widget.render(matrices, mouseX, mouseY, tickDelta);
        } else {
            widget.render(matrices, mouseX, mouseY, tickDelta, false);
        }
        DrawUtil.drawRightText(matrices, client.textRenderer, name, list.getAbsoluteX() + this.width - 6, widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
    }

    public static void addToList(EntryButtonList list, FloatSliderButton button, Text name) {
        FloatSliderEntry check = new FloatSliderEntry(0, list.lastY, list.getWidth(), button.getHeight(), new FloatSliderButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getRawValue(), button.getMin(), button.getMax()), name, list);
        list.addEntry(check);
        list.lastY = list.lastY + check.getHeight();
    }

    @Override
    public int getHeight() {
        return height + 3;
    }


    @Override
    public Float getValue() {
        return widget.getRawValue();
    }

    @Override
    public void setValue(Float value) {
        widget.setRawValue(value);
    }
}
package net.darkkronicle.polish.gui.entries;

import net.darkkronicle.polish.api.AbstractPEntry;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.widgets.CheckboxButton;
import net.darkkronicle.polish.gui.widgets.IntSliderButton;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.DrawUtil;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class IntSliderEntry extends AbstractPEntry<Integer, IntSliderButton> {

    private IntSliderEntry(int relativeX, int relativeY, int width, int height, IntSliderButton widget, Text name, EntryButtonList list) {
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

    public static void addToList(EntryButtonList list, IntSliderButton button, Text name) {
        IntSliderEntry check = new IntSliderEntry(0, list.lastY, list.getWidth(), button.getHeight(), new IntSliderButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getRawValue(), button.getMin(), button.getMax()), name, list);
        list.addEntry(check);
        list.lastY = list.lastY + check.getHeight();
    }

    @Override
    public int getHeight() {
        return height + 3;
    }


    @Override
    public Integer getValue() {
        return widget.getRawValue();
    }

    @Override
    public void setValue(Integer value) {
        widget.setRawValue(value);
    }
}

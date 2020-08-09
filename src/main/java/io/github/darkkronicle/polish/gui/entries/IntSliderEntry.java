package io.github.darkkronicle.polish.gui.entries;

import io.github.darkkronicle.polish.gui.widgets.IntSliderButton;
import io.github.darkkronicle.polish.util.SimpleRectangle;
import io.github.darkkronicle.polish.api.AbstractPEntry;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.DrawUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class IntSliderEntry extends AbstractPEntry<Integer, IntSliderButton> {

    private final boolean right = true;

    protected IntSliderEntry(int relativeX, int relativeY, int width, int height, IntSliderButton widget, Text name, EntryButtonList list) {
        super(relativeX, relativeY, width, height, widget, name, list);
    }

    @Override
    public void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scrolled, boolean hovered) {
        widget.setRelativePos(originalRelativeX + 2, originalRelativeY + scrolled);
        relativeX = originalRelativeX + 2;
        relativeY = originalRelativeY + scrolled;
        if (hovered) {
            widget.render(matrices, mouseX, mouseY, tickDelta);
        } else {
            widget.render(matrices, mouseX, mouseY, tickDelta, false);
        }
        if (right) {
            DrawUtil.drawRightText(matrices, client.textRenderer, name, parentList.getAbsoluteX() + relativeX + this.width - 6, widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
        } else {
            DrawUtil.drawText(matrices, client.textRenderer, name, parentList.getAbsoluteX() + relativeX + 20 + widget.getWidth(), widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
        }
    }

    public static IntSliderEntry createEntry(EntryButtonList list, IntSliderButton button, Text name) {
        return createEntry(list, button, name, 0);
    }

    public static IntSliderEntry createEntry(EntryButtonList list, IntSliderButton button, Text name, int column) {
        IntSliderEntry check;
        int col = column;
        if (list.getColumnCount() <= 1) {
            check = new IntSliderEntry(0, list.lastY, list.getWidth(), button.getHeight(), new IntSliderButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getRawValue(), button.getMin(), button.getMax()), name, list);
        } else {
            if (col == 0) {
                col = list.incrementColumn();
            }
            int last = col - 1;
            int start;
            if (last == 0) {
                start = 0;
            } else {
                start = Math.round((float) list.getWidth() / list.getColumnCount() * last);
            }
            int endWidth = Math.round((float) list.getWidth() / list.getColumnCount() * col) - start;
            check = new IntSliderEntry(start, list.lastY, endWidth, button.getHeight(), new IntSliderButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getRawValue(), button.getMin(), button.getMax()), name, list);
        }
     //   list.addEntry(check);
        if (list.getColumnCount() == 1 || col == 1) {
            list.lastY = list.lastY + check.getHeight();
        }
        return check;
    }

    @Override
    public int getHeight() {
        return height + 5;
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

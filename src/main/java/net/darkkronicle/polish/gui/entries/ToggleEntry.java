package net.darkkronicle.polish.gui.entries;

import net.darkkronicle.polish.api.AbstractPEntry;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.widgets.ToggleButton;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.DrawUtil;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ToggleEntry extends AbstractPEntry<Boolean, ToggleButton> {

    private final boolean right = true;

    protected ToggleEntry(int relativeX, int relativeY, int width, int height, ToggleButton widget, Text name, EntryButtonList list) {
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

    public static ToggleEntry createEntry(EntryButtonList list, ToggleButton button, Text name) {
        return createEntry(list, button, name, 0);
    }

    public static ToggleEntry createEntry(EntryButtonList list, ToggleButton button, Text name, int column) {
        ToggleEntry check;
        int col = column;
        if (list.getColumnCount() <= 1) {
            check = new ToggleEntry(0, list.lastY, list.getWidth(), button.getHeight(), new ToggleButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), button.isSelected()), name, list);
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
            check = new ToggleEntry(start, list.lastY, endWidth, button.getHeight(), new ToggleButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), button.isSelected()), name, list);
        }
//        list.addEntry(check);
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
    public Boolean getValue() {
        return widget.isSelected();
    }

    @Override
    public void setValue(Boolean value) {
        widget.setSelected(value);
    }


}

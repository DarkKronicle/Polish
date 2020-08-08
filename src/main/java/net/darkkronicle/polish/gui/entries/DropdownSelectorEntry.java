package net.darkkronicle.polish.gui.entries;

import net.darkkronicle.polish.api.AbstractPEntry;
import net.darkkronicle.polish.gui.complexwidgets.DropdownSelectorButton;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.DrawUtil;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class DropdownSelectorEntry<K> extends AbstractPEntry<K, DropdownSelectorButton<K>> {

    public DropdownSelectorEntry(int relativeX, int relativeY, int width, int height, DropdownSelectorButton<K> widget, Text name, EntryButtonList parentList) {
        super(relativeX, relativeY, width, height, widget, name, parentList);
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

        DrawUtil.drawRightText(matrices, client.textRenderer, name, parentList.getAbsoluteX() + relativeX + this.width - 6, widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
    }

    public static <T> DropdownSelectorEntry<T> createEntry(EntryButtonList list, DropdownSelectorButton<T> button, Text name) {
        return createEntry(list, button, name, 0);
    }

    public static <T> DropdownSelectorEntry<T> createEntry(EntryButtonList list, DropdownSelectorButton<T> button, Text name, int column) {
        DropdownSelectorEntry<T> check;
        int col = column;
        if (list.getColumnCount() <= 1) {
            DropdownSelectorButton<T> newButton = new DropdownSelectorButton<>(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), button.arrowWidth, button.getBackgroundColor());
            newButton.addSet(button.getEntrySet());
            check = new DropdownSelectorEntry<>(0, list.lastY, list.getWidth(), button.getHeight(), newButton, name, list);
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
            DropdownSelectorButton<T> newButton = new DropdownSelectorButton<>(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), button.arrowWidth, button.getBackgroundColor());
            newButton.addSet(button.getEntrySet());
            check = new DropdownSelectorEntry<>(start, list.lastY, endWidth, button.getHeight(), newButton, name, list);
        }
        //   list.addEntry(check);
        if (list.getColumnCount() == 1 || col == 1) {
            list.lastY = list.lastY + check.getHeight();
        }
        return check;
    }

    @Override
    public int getHeight() {
        return widget.getHeight() + 5;
    }

    @Override
    public K getValue() {
        return widget.getCurrent().getKey();
    }

    @Override
    public void setValue(K value) {
        widget.setCurrent(widget.entryFromObj(value));
    }
}

package io.github.darkkronicle.polish.gui.entries;

import io.github.darkkronicle.polish.api.AbstractPEntry;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.gui.widgets.CheckboxButton;
import io.github.darkkronicle.polish.gui.widgets.CleanButton;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.DrawUtil;
import io.github.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CleanButtonEntry extends AbstractPEntry<Void, CleanButton> {

    private final boolean right = false;


    protected CleanButtonEntry(int relativeX, int relativeY, int width, int height, CleanButton widget, Text name, EntryButtonList parentList) {
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
        if (right) {
            DrawUtil.drawRightText(matrices, client.textRenderer, name.asOrderedText(), parentList.getAbsoluteX() + relativeX + this.width - 6, widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
        } else {
            DrawUtil.drawText(matrices, client.textRenderer, name.asOrderedText(), parentList.getAbsoluteX() + relativeX + 20 + widget.getWidth(), widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
        }
    }

    public static CleanButtonEntry createEntry(EntryButtonList list, CleanButton button, Text name) {
        return createEntry(list, button, name, 0);
    }

    public static CleanButtonEntry createEntry(EntryButtonList list, CleanButton button, Text name, int column) {
        CleanButtonEntry check;
        int col = column;
        if (list.getColumnCount() <= 1) {
            check = new CleanButtonEntry(0, list.lastY, list.getWidth(), button.getHeight(), new CleanButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), button.getBaseColor(), button.getHighlightColor(), button.getBorderColor(), button.getMessage(), button.getOnPress()), name, list);
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
            check = new CleanButtonEntry(start, list.lastY, endWidth, button.getHeight(), new CleanButton(list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), button.getBaseColor(), button.getHighlightColor(), button.getBorderColor(), button.getMessage(), button.getOnPress()), name, list);
        }
        //  list.addEntry(check);
        if (list.getColumnCount() == 1 || col == 1) {
            list.lastY = list.lastY + check.getHeight();
        }
        return check;
    }

    @Override
    public int getHeight() {
        return height + 3;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public void setValue(Void value) {

    }
}

package io.github.darkkronicle.polish.gui.entries;

import io.github.darkkronicle.polish.gui.widgets.TextboxButton;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.SimpleRectangle;
import io.github.darkkronicle.polish.api.AbstractPEntry;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.util.DrawUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextboxEntry extends AbstractPEntry<String, TextboxButton> {

    private boolean right = true;

    public TextboxEntry(int relativeX, int relativeY, int width, int height, TextboxButton widget, Text name, EntryButtonList parentList) {
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
            DrawUtil.drawRightText(matrices, client.textRenderer, name, parentList.getAbsoluteX() + relativeX + this.width - 6, widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
        } else {
            DrawUtil.drawText(matrices, client.textRenderer, name, parentList.getAbsoluteX() + relativeX + 20 + widget.getWidth(), widget.getAbsoluteY() + (getHeight() / 2) - 4, Colors.WHITE.color().color());
        }
    }

    @Override
    public int getHeight() {
        return widget.getHeight() + 3;
    }

    @Override
    public String getValue() {
        return widget.getText();
    }

    @Override
    public void setValue(String value) {
        widget.setText(value);
    }

    public static TextboxEntry createEntry(EntryButtonList list, TextboxButton button, Text name) {
        return createEntry(list, button, name, 0);
    }

    public static TextboxEntry createEntry(EntryButtonList list, TextboxButton button, Text name, int column) {
        TextboxEntry check;
        int col = column;
        if (list.getColumnCount() <= 1) {
            check = new TextboxEntry(0, list.lastY, list.getWidth(), button.getHeight(), new TextboxButton(MinecraftClient.getInstance().textRenderer, list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), Colors.DARKGRAY.color().withAlpha(100), true), name, list);
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
            check = new TextboxEntry(start, list.lastY, endWidth, button.getHeight(), new TextboxButton(MinecraftClient.getInstance().textRenderer, list.getAbsoluteX(), list.getAbsoluteY(), button.getWidth(), button.getHeight(), Colors.DARKGRAY.color().withAlpha(100), true), name, list);
        }
        //   list.addEntry(check);
        if (list.getColumnCount() == 1 || col == 1) {
            list.lastY = list.lastY + check.getHeight();
        }
        return check;
    }
}

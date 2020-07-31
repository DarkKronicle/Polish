package net.darkkronicle.polish.gui.complexwidgets;

import net.darkkronicle.polish.gui.widgets.SimpleButton;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class SimpleButtonList extends AbstractPWidgetList<SimpleButtonList.Entry> {
    private int lastY = 0;

    public SimpleButtonList(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void addEntry(SimpleButton button) {
        Entry e = new ButtonEntry(0, lastY, width - 4, 19, button, this);
        lastY = lastY + e.getHeight();
        addEntry(e);
        scrollMax = Math.max(lastY - height, 1);
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        rect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, Colors.DARKGRAY.color().withAlpha(100).color());
        outlineRect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, Colors.DARKGRAY.color().color());
    }

    public class ButtonEntry extends Entry {

        public ButtonEntry(int offsetX, int offsetY, int width, int height, SimpleButton button, SimpleButtonList list) {
            super(offsetX, offsetY, width, height, button, list);
        }

        @Override
        public void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scrolled, boolean hovered) {
            widget.setRelativePos(originalRelativeX, originalRelativeY + scrolled);
            if (hovered) {
                widget.render(matrices, mouseX, mouseY, tickDelta);
            } else {
                widget.render(matrices, mouseX, mouseY, tickDelta, false);
            }
        }

        @Override
        public int getHeight() {
            return height;
        }
    }

    public abstract static class Entry extends AbstractPWidgetList.Entry<SimpleButtonList.Entry> {
        public Entry(int relativeX, int relativeY, int width, int height, SimpleButton button, SimpleButtonList list) {
            super(relativeX, relativeY, width, height, new SimpleButton(list.getAbsoluteX(), list.getAbsoluteY(), width, height, button.getBaseColor(), button.getHighlightColor(), button.getBorderColor(), button.getMessage(), button.getOnPress()) );
            widget.setRelativePos(relativeX, relativeY);
        }

    }
}

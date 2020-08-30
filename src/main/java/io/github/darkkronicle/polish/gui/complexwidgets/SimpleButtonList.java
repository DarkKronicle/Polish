package io.github.darkkronicle.polish.gui.complexwidgets;

import io.github.darkkronicle.polish.gui.widgets.SimpleButton;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.DrawUtil;
import io.github.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A list made out of {@link SimpleButton}
 */
@Environment(EnvType.CLIENT)
public class SimpleButtonList extends AbstractPWidgetList<SimpleButtonList.Entry> {
    /**
     * The last y used.
     */
    private int lastY = 0;

    /**
     * Instantiates a new Simple button list.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public SimpleButtonList(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Add an entry to the list.
     *
     * @param button the button
     */
    public void addEntry(SimpleButton button) {
        Entry e = new ButtonEntry(0, lastY, width - 4, 19, button, this);
        lastY = lastY + e.getHeight();
        addEntry(e);
        scrollMax = Math.max(lastY - height, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        DrawUtil.rect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, Colors.DARKGRAY.color().withAlpha(100).color());
        DrawUtil.outlineRect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, Colors.DARKGRAY.color().color());
    }

    /**
     * A button entry
     */
    public static class ButtonEntry extends Entry {

        /**
         * Instantiates a new Button entry.
         *
         * @param offsetX the offset x
         * @param offsetY the offset y
         * @param width   the width
         * @param height  the height
         * @param button  the button
         * @param list    the list
         */
        public ButtonEntry(int offsetX, int offsetY, int width, int height, SimpleButton button, SimpleButtonList list) {
            super(offsetX, offsetY, width, height, button, list);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scrolled, boolean hovered) {
            widget.setRelativePos(originalRelativeX, originalRelativeY + scrolled);
            if (hovered) {
                widget.render(matrices, mouseX, mouseY, tickDelta);
            } else {
                widget.render(matrices, mouseX, mouseY, tickDelta, false);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getHeight() {
            return height;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getWidth() {
            return width;
        }
    }

    /**
     * Abstract entry class
     */
    public abstract static class Entry extends AbstractPWidgetList.Entry<SimpleButtonList.Entry> {
        /**
         * Instantiates a new Entry.
         *
         * @param relativeX the relative x
         * @param relativeY the relative y
         * @param width     the width
         * @param height    the height
         * @param button    the button
         * @param list      the list
         */
        public Entry(int relativeX, int relativeY, int width, int height, SimpleButton button, SimpleButtonList list) {
            super(relativeX, relativeY, width, height, new SimpleButton(list.getAbsoluteX(), list.getAbsoluteY(), width, height, button.getBaseColor(), button.getHighlightColor(), button.getBorderColor(), button.getMessage(), button.getOnPress()) );
            widget.setRelativePos(relativeX, relativeY);
        }

    }
}

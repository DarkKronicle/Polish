package io.github.darkkronicle.polish.gui.complexwidgets;

import io.github.darkkronicle.polish.api.ScissorsHelper;
import io.github.darkkronicle.polish.gui.widgets.SimpleButton;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.DrawPosition;
import io.github.darkkronicle.polish.util.DrawUtil;
import io.github.darkkronicle.polish.util.ScrollUtil;
import io.github.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A list entry that is very similar to {@link SimpleButtonList} it is just horizontal.
 */
@Environment(EnvType.CLIENT)
public class SimpleTabList extends AbstractPWidgetList<SimpleTabList.Entry> {
    /**
     * The last x used.
     */
    private int lastX = 0;

    /**
     * Instantiates a new Simple tab list.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public SimpleTabList(int x, int y, int width, int height) {
        super(x, y, width, height);
        scrollUtil = new ScrollUtil(100, scroll, 10, new SimpleRectangle(getAbsoluteX(), getAbsoluteY() + height - 4, width, 4));
    }

    /**
     * Add an entry to the list.
     *
     * @param button the button
     */
    public void addEntry(SimpleButton button) {
        Entry e = new ButtonEntry(lastX, 0, button.getWidth() - 4, 19, button, this);
        lastX = lastX + e.getWidth();
        addEntry(e);
        scrollMax = Math.max(lastX - width, 1);
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
     * {@inheritDoc}
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        hovered = calcHover(mouseX, mouseY);
        renderWidget(matrices, mouseX, mouseY, delta);
        scrollUtil.setMaxScroll(Math.max(scrollMax, 1));
        scrollUtil.updateScroll();
        int scrolled = -1 * scrollUtil.getScroll();
        DrawPosition pos = scrollUtil.getSidePos();
        rect(matrices, pos.getX(), pos.getY(), 8, 4, Colors.WHITE.color().withAlpha(100).color());
        int currentx = scrolled;
        ScissorsHelper.INSTANCE.addScissor(new SimpleRectangle(getAbsoluteX() + 2, getAbsoluteY() + 2, width - 2, height - 2));
        for (int i = 0; i < entries.size(); i++) {
            AbstractPWidgetList.Entry sibling = entries.get(i);
            if (currentx > -1 * sibling.getWidth() && currentx < width + sibling.getWidth()) {
                sibling.renderEntry(matrices, i, mouseX, mouseY, delta, new SimpleRectangle(getAbsoluteX(), getAbsoluteY(), width, height), scrolled, hovered);
            }
            currentx = currentx + sibling.getWidth();
        }
        wasHovered = hovered;
        ScissorsHelper.INSTANCE.removeLastScissor();
        matrices.pop();
    }

    /**
     * A button entry
     */
    public class ButtonEntry extends Entry {

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
        public ButtonEntry(int offsetX, int offsetY, int width, int height, SimpleButton button, SimpleTabList list) {
            super(offsetX, offsetY, width, height, button, list);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scrolled, boolean hovered) {
            widget.setRelativePos(originalRelativeX + scrolled, originalRelativeY);
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
        public Entry(int relativeX, int relativeY, int width, int height, SimpleButton button, SimpleTabList list) {
            super(relativeX, relativeY, width, height, new SimpleButton(list.getAbsoluteX(), list.getAbsoluteY(), width, height, button.getBaseColor(), button.getHighlightColor(), button.getBorderColor(), button.getMessage(), button.getOnPress()) );
            widget.setRelativePos(relativeX, relativeY);
        }

    }
}


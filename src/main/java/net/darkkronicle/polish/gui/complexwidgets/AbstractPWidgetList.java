package net.darkkronicle.polish.gui.complexwidgets;

import lombok.Getter;
import net.darkkronicle.polish.api.ScissorsHelper;
import net.darkkronicle.polish.gui.widgets.AbstractPWidget;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.DrawPosition;
import net.darkkronicle.polish.util.ScrollUtil;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

/**
 * An abstract class to make scrollable widget lists.
 *
 * @param <E> the type parameter
 */
@Environment(EnvType.CLIENT)
public abstract class AbstractPWidgetList<E extends AbstractPWidgetList.Entry> extends AbstractPWidget {

    /**
     * Current scroll.
     */
    private double scroll = 0;
    /**
     * How far you can scroll.
     */
    public double scrollMax = 0;

    /**
     * The entries made up of the list.
     */
    @Getter
    private ArrayList<Entry> entries = new ArrayList<>();

    private ScrollUtil scrollUtil;

    /**
     * Instantiates a new AbstractPListWidget
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public AbstractPWidgetList(int x, int y, int width, int height) {
        super(x, y, width, height);
        scrollUtil = new ScrollUtil(100, scroll, 10, new SimpleRectangle(getAbsoluteX() + width - 4, getAbsoluteY(), 4, height));
    }

    /**
     * Adds an entry to the list.
     *
     * @param entry the entry
     */
    public void addEntry(E entry) {
        entries.add(entry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        hovered = calcHover(mouseX, mouseY);
        renderWidget(matrices, mouseX, mouseY, delta);
//        int scrolled = -1 * (int) Math.round(scroll);
//        double scrollAmount = MathHelper.clamp((scroll / (Math.max(scrollMax, 1))), 0, 1);
//        rect(matrices, getAbsoluteX() + width - 4, (getAbsoluteY() + (int) Math.round((height - 8) * scrollAmount)), 4, 8, Colors.WHITE.color().withAlpha(100).color());
        scrollUtil.setMaxScroll(Math.max(scrollMax, 1));
        scrollUtil.updateScroll();
        int scrolled = -1 * scrollUtil.getScroll();
        DrawPosition pos = scrollUtil.getScrollPos();
        rect(matrices, pos.getX(), pos.getY(), 4, 8, Colors.WHITE.color().withAlpha(100).color());
        int currenty = scrolled;
        ScissorsHelper.INSTANCE.addScissor(new SimpleRectangle(getAbsoluteX() + 2, getAbsoluteY() + 2, width - 2, height - 2));
        for (int i = 0; i < entries.size(); i++) {
            Entry sibling = entries.get(i);
            if (currenty > -1 * sibling.getHeight() && currenty < height + sibling.getHeight()) {
                sibling.renderEntry(matrices, i, mouseX, mouseY, delta, new SimpleRectangle(getAbsoluteX(), getAbsoluteY(), width, height), scrolled, hovered);
            }
            currenty = currenty + sibling.getHeight();
        }
        wasHovered = hovered;
        ScissorsHelper.INSTANCE.removeLastScissor();
        matrices.pop();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (hovered) {
            for (Entry entry : entries) {
                if (entry.keyPressed(keyCode, scanCode, modifiers)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (hovered) {
            for (Entry entry : entries) {
                if (entry.charTyped(chr, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered) {
            onClick(mouseX, mouseY, button);
            for (Entry entry : entries) {
                if (entry.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && hovered) {
            onDrag(mouseX, mouseY, button, deltaX, deltaY);
            for (Entry entry : entries) {
                entry.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onScroll(double mouseX, double mouseY, double amount) {
        scroll = scroll + (amount * -2);
        scroll = MathHelper.clamp(scroll, 0, scrollMax);
        scrollUtil.scrollTo(scroll * -1);
    }

    /**
     * An entry to the list
     *
     * @param <E> the type of entry
     */
    public abstract static class Entry<E extends AbstractPWidgetList.Entry> {
        /**
         * The widget that will render/interact with.
         */
        public final AbstractPWidget widget;
        /**
         * The width.
         */
        public int width;
        /**
         * The height.
         */
        public int height;
        /**
         * The relative x.
         */
        public int relativeX;
        /**
         * The relative y.
         */
        public int relativeY;
        /**
         * The original relative y.
         */
        public final int originalRelativeY;
        /**
         * The original relative x.
         */
        public final int originalRelativeX;

        /**
         * Instantiates a new Entry.
         *
         * @param relativeX the relative x
         * @param relativeY the relative y
         * @param width     the width
         * @param height    the height
         * @param widget    the widget
         */
        public Entry(int relativeX, int relativeY, int width, int height, AbstractPWidget widget) {
            this.width = width;
            this.height = height;
            this.originalRelativeX = relativeX;
            this.originalRelativeY = relativeY;
            this.relativeX = relativeX;
            this.relativeY = relativeY;
            this.widget = widget;
        }

        /**
         * Render entry.
         *
         * @param matrices  the matrices
         * @param index     the index
         * @param mouseX    the mouse x
         * @param mouseY    the mouse y
         * @param tickDelta the tick delta
         * @param bounds    the bounds
         * @param scroll    the scroll
         * @param hovered   the hovered
         */
        public abstract void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scroll, boolean hovered);

        /**
         * Gets height.
         *
         * @return the height
         */
        public abstract int getHeight();

        /**
         * Mouse clicked
         *
         * @param mouseX the mouse x
         * @param mouseY the mouse y
         * @param button the button
         * @return the boolean
         */
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0 && widget.isHovered()) {
                widget.onClick(mouseX, mouseY, button);
                return true;
            }
            return false;
        }

        /**
         * Mouse dragged
         *
         * @param mouseX the mouse x
         * @param mouseY the mouse y
         * @param button the button
         * @param deltaX the delta x
         * @param deltaY the delta y
         * @return the boolean
         */
        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (button == 0 && widget.isHovered()) {
                widget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
            }
            return false;
        }

        /**
         * Mouse scrolled
         *
         * @param mouseX the mouse x
         * @param mouseY the mouse y
         * @param amount the amount
         * @return the boolean
         */
        public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
            if (widget.isHovered()) {
                widget.onScroll(mouseX, mouseY, amount);
                return true;
            }
            return false;
        }

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (widget.isHovered()) {
                return widget.keyPressed(keyCode, scanCode, modifiers);
            }
            return false;
        }

        public boolean charTyped(char chr, int keyCode) {
            if (widget.isHovered()) {
                return widget.charTyped(chr, keyCode);
            }
            return false;
        }
    }
}

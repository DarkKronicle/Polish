package net.darkkronicle.polish.gui.complexwidgets;

import net.darkkronicle.polish.api.ScissorsHelper;
import net.darkkronicle.polish.gui.widgets.AbstractPWidget;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public abstract class AbstractPWidgetList<E extends AbstractPWidgetList.Entry> extends AbstractPWidget {

    private double scroll = 0;
    public double scrollMax = 0;

    private ArrayList<Entry> entries = new ArrayList<>();

    public AbstractPWidgetList(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void addEntry(E entry) {
        entries.add(entry);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        hovered = calcHover(mouseX, mouseY);
        renderWidget(matrices, mouseX, mouseY, delta);
        int scrolled = -1 * (int) Math.round(scroll);
        double scrollAmount = (scroll / (Math.max(scrollMax - 16, 16)));
        rect(matrices, getAbsoluteX() + width - 4, getAbsoluteY() + (int) Math.round(height * scrollAmount), 4, 8, Colors.WHITE.color().withAlpha(100).color());
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
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered) {
            onClick(mouseX, mouseY, button);
            for (Entry entry : entries) {
                entry.mouseClicked(mouseX, mouseY, button);
            }
            return true;
        }
        return false;
    }

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

    @Override
    public void onScroll(double mouseX, double mouseY, double amount) {
        scroll = scroll + (amount * -2);
        scroll = MathHelper.clamp(scroll, 0, scrollMax);
    }

    public abstract static class Entry<E extends AbstractPWidgetList.Entry> {
        public final AbstractPWidget widget;
        public int width;
        public int height;
        public final int originalRelativeY;
        public final int originalRelativeX;

        public Entry(int relativeX, int relativeY, int width, int height, AbstractPWidget widget) {
            this.width = width;
            this.height = height;
            this.originalRelativeX = relativeX;
            this.originalRelativeY = relativeY;
            this.widget = widget;
        }

        public abstract void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scroll, boolean hovered);

        public abstract int getHeight();

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0 && widget.isHovered()) {
                widget.onClick(mouseX, mouseY, button);
            }
            return false;
        }

        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (button == 0 && widget.isHovered()) {
                widget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
            }
            return false;
        }

        public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
            if (widget.isHovered()) {
                widget.onScroll(mouseX, mouseY, amount);
                return true;
            }
            return false;
        }
    }
}

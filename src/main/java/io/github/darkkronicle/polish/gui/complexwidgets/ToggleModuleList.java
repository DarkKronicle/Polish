package io.github.darkkronicle.polish.gui.complexwidgets;

import io.github.darkkronicle.polish.api.AbstractPEntry;
import io.github.darkkronicle.polish.util.SimpleRectangle;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ToggleModuleList extends AbstractPWidgetList<ToggleModuleList.ToggleModuleEntry> {

    private int lastY = 0;
    private int lastX = 0;

    public ToggleModuleList(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void addEntry(ToggleModuleWidget button, Consumer<Boolean> save) {
        ToggleModuleEntry e = new ToggleModuleEntry(lastX + 3,  lastY + 3, button, button.getText(), this, save);
        lastX = lastX + e.getWidth();
        if (lastX + e.getWidth() > width) {
            lastX = 0;
            lastY = lastY + e.getHeight();
        }
        addEntry(e);
        scrollMax = Math.max(lastY - height, 1);
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    public void save() {
        for (Entry<?> entry : entries) {
            ((ToggleModuleEntry) entry).save();
        }
    }

    public static class ToggleModuleEntry extends AbstractPEntry<Boolean, ToggleModuleWidget> {

        public ToggleModuleList parentList;
        public Consumer<Boolean> save;

        public ToggleModuleEntry(int relativeX, int relativeY, ToggleModuleWidget widget, Text name, ToggleModuleList parentList, Consumer<Boolean> save) {
            super(relativeX, relativeY, 80, 120, widget, name, null);
            widget.setOffsetPos(parentList.getAbsoluteX(), parentList.getAbsoluteY());
            this.parentList = parentList;
            this.save = save;
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

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public Boolean getValue() {
            return widget.getValue();
        }

        @Override
        public void setValue(Boolean value) {
            widget.setValue(value);
        }

        public void save() {
            save.accept(getValue());
        }
    }
}

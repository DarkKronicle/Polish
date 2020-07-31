package net.darkkronicle.polish.gui.complexwidgets;

import net.darkkronicle.polish.gui.widgets.SelectorButton;
import net.darkkronicle.polish.gui.widgets.SimpleButton;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.Map;


public class DropdownSelectorButton<K> extends SelectorButton<K> {

    private boolean dropdown = false;
    private SimpleButtonList dropdownMenu;

    public DropdownSelectorButton(int x, int y, int width, int height, int arrowWidth, SimpleColor backgroundColor) {
        super(x, y, width, height, arrowWidth, backgroundColor);
        setDropdownMenu();
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderWidget(matrices, mouseX, mouseY, delta);
        if (dropdown) {
            dropdownMenu.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void setDropdownMenu() {
        dropdownMenu = new SimpleButtonList(getAbsoluteX() + arrowWidth, getAbsoluteY() + height, width - arrowWidth - arrowWidth, 80);
        for (Map.Entry<K, String> entry : getEntries().entrySet()) {
            dropdownMenu.addEntry(new SimpleButton(0, 0, width, 19, Colors.DARKGRAY.color().withAlpha(100), ColorUtil.blend(Colors.DARKGRAY.color().withAlpha(100), Colors.WHITE.color(), 0.4F), Colors.BLACK.color(), new LiteralText(entry.getValue()), button -> setCurrent(entry)));
        }
    }

    @Override
    public SelectorButton<K> add(K entry, String name) {
        super.add(entry, name);
        setDropdownMenu();
        return this;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int relativeX = (int) Math.round(mouseX) - getAbsoluteX();
        int relativeY = (int) Math.round(mouseY) - getAbsoluteY();
        if (relativeX >= 0 && relativeX <= arrowWidth + 2 && relativeY >= 0 && relativeY <= height) {
            previous();
            return true;
        }
        if (relativeX >= (width - arrowWidth - 2) && relativeX <= width && relativeY >= 0 && relativeY <= height) {
            next();
            return true;
        }
        if (relativeX > arrowWidth + 2 && relativeX < width - arrowWidth - 2 && relativeY >= 0 && relativeY <= height) {
            dropdown = !dropdown;
        }
        if (dropdown) {
            dropdownMenu.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (dropdown) {
            return dropdownMenu.mouseScrolled(mouseX, mouseY, amount);
        }
        return false;
    }
}

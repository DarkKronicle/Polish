package net.darkkronicle.polish.gui.complexwidgets;

import net.darkkronicle.polish.gui.widgets.SelectorButton;
import net.darkkronicle.polish.gui.widgets.SimpleButton;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;


/**
 * A selector widget that has a dropdown if clicked in the middle.
 * //TODO clicks pass through the selector to other widgets.
 *
 * @param <K> the type parameter
 */
public class DropdownSelectorButton<K> extends SelectorButton<K> {

    /**
     * Is the dropdown active.
     */
    private boolean dropdown = false;
    /**
     * The Dropdown menu.
     */
    private SimpleButtonList dropdownMenu;

    /**
     * Instantiates a new Dropdown selector button.
     *
     * @param x               the x
     * @param y               the y
     * @param width           the width
     * @param height          the height
     * @param arrowWidth      the arrow width
     * @param backgroundColor the background color
     */
    public DropdownSelectorButton(int x, int y, int width, int height, int arrowWidth, SimpleColor backgroundColor) {
        super(x, y, width, height, arrowWidth, backgroundColor);
        setDropdownMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderWidget(matrices, mouseX, mouseY, delta);
        if (dropdown) {
            dropdownMenu.setRelativePos(arrowWidth, height);
            dropdownMenu.render(matrices, mouseX, mouseY, delta);
        }
    }

    /**
     * Creates the dropdownmenu
     */
    public void setDropdownMenu() {
        dropdownMenu = new SimpleButtonList(getAbsoluteX(), getAbsoluteY(), width - arrowWidth - arrowWidth, 80);
        dropdownMenu.setRelativePos(arrowWidth, height);
        for (Map.Entry<K, String> entry : getEntries().entrySet()) {
            dropdownMenu.addEntry(new SimpleButton(0, 0, width, 19, Colors.DARKGRAY.color().withAlpha(100), ColorUtil.blend(Colors.DARKGRAY.color().withAlpha(100), Colors.WHITE.color(), 0.4F), Colors.BLACK.color(), new LiteralText(entry.getValue()), button -> setCurrent(entry)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectorButton<K> add(K entry, String name) {
        super.add(entry, name);
        setDropdownMenu();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onClick(double mouseX, double mouseY, int button) {
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
            return true;
        }
        if (dropdown) {
            return dropdownMenu.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            return onClick(mouseX, mouseY, button);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (dropdown) {
            return dropdownMenu.mouseScrolled(mouseX, mouseY, amount);
        }
        return false;
    }

}

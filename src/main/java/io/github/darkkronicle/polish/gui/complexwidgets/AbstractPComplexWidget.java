package io.github.darkkronicle.polish.gui.complexwidgets;

import io.github.darkkronicle.polish.api.ScissorsHelper;
import io.github.darkkronicle.polish.util.SimpleRectangle;
import io.github.darkkronicle.polish.gui.widgets.AbstractPWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

/**
 * A widget that has multiple widgets make it up.
 */
public class AbstractPComplexWidget extends AbstractPWidget {
    /**
     * The widgets that it has.
     */
    protected ArrayList<AbstractPWidget> siblings;


    /**
     * Instantiates a new Abstract complex width
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public AbstractPComplexWidget(int x, int y, int width, int height) {
        super(x, y, width, height);
        siblings = new ArrayList<>();
    }

    /**
     * Add widget to the complex widget.
     *
     * @param widget the widget
     * @return the abstract p complex widget
     */
    public AbstractPComplexWidget add(AbstractPWidget widget) {
        siblings.add(widget);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        render(matrices, mouseX, mouseY, delta, calcHover(mouseX, mouseY));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, boolean hovered) {
        matrices.push();
        ScissorsHelper.INSTANCE.addScissor(new SimpleRectangle(getAbsoluteX(), getAbsoluteY(), width, height));
        this.hovered = hovered;
        renderWidget(matrices, mouseX, mouseY, delta);
        for (AbstractPWidget sibling : siblings) {
            sibling.render(matrices, mouseX, mouseY, delta);
        }
        wasHovered = hovered;
        ScissorsHelper.INSTANCE.removeLastScissor();
        matrices.pop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered) {
            System.out.println("CLICKED PLEASE");
            for (AbstractPWidget sibling : siblings) {
                if (sibling.isHovered()) {
                    sibling.onClick(mouseX, mouseY, button);
                    return true;
                }
            }
            onClick(mouseX, mouseY, button);
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
            for (AbstractPWidget sibling : siblings) {
                if (sibling.isHovered()) {
                    sibling.onDrag(mouseX, mouseY, button, deltaX, deltaY);
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
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }


}

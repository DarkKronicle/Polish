package net.darkkronicle.polish.gui.complexwidgets;

import net.darkkronicle.polish.api.ScissorsHelper;
import net.darkkronicle.polish.gui.widgets.AbstractPWidget;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class AbstractPComplexWidget extends AbstractPWidget {
    private ArrayList<AbstractPWidget> siblings;


    public AbstractPComplexWidget(int x, int y, int width, int height) {
        super(x, y, width, height);
        siblings = new ArrayList<>();
    }

    public AbstractPComplexWidget add(AbstractPWidget widget) {
        siblings.add(widget);
        return this;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        ScissorsHelper.INSTANCE.addScissor(new SimpleRectangle(getAbsoluteX(), getAbsoluteY(), width, height));
        hovered = calcHover(mouseX, mouseY);
        renderWidget(matrices, mouseX, mouseY, delta);
        for (AbstractPWidget sibling : siblings) {
            sibling.render(matrices, mouseX, mouseY, delta);
        }
        wasHovered = hovered;
        ScissorsHelper.INSTANCE.removeLastScissor();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered) {
            onClick(mouseX, mouseY, button);
            for (AbstractPWidget sibling : siblings) {
                if (sibling.isHovered()) {
                    sibling.onClick(mouseX, mouseY, button);
                }
            }
            return true;
        }
        return false;
    }

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


    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }


}

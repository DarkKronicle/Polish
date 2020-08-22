package io.github.darkkronicle.polish.util;

import io.github.darkkronicle.polish.gui.widgets.AbstractPWidget;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A manager for {@link AbstractPWidget}'s to add them to this and not have to deal with rendering, scrolling, or clicking them.
 * You create a new one add use the add() method to add new Widget's to it.
 * Once you have one created you tap into {@link Screen} and on render call there method.
 */
@Environment(EnvType.CLIENT)
public class WidgetManager implements Element, Drawable {

    /**
     * The stored widgets.
     */
    @Getter
    private final ArrayList<Drawable> widgets;
    /**
     * The parent screen
     */
    private final Screen parent;
    /**
     * The children in the parent screen.
     */
    @Setter
    // Minecrafts dumb V
    private List<Element> children;

    /**
     * Instantiates a new Widget manager.
     *
     * @param parent   the parent screen
     * @param children the children from the screen
     */
    public WidgetManager(Screen parent, List<Element> children) {
        widgets = new ArrayList<>();
        this.parent = parent;
        this.children = children;
    }

    /**
     * Clears all the widgets from the manager.
     */
    public void clear() {
        widgets.clear();
    }

    /**
     * Adds an {@link AbstractPWidget} to the manager.
     *
     * @param w the widget to add
     */
    public void add(AbstractPWidget w) {
        widgets.add(w);
        children.add(w);
    }

    /**
     * Render method to render all the widgets
     *
     * @param matrices the matrices
     * @param mouseX   the mouse x
     * @param mouseY   the mouse y
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (Drawable d : widgets) {
            d.render(matrices, mouseX, mouseY, delta);
        }
    }

    /**
     * Method to tick widgets if the method isTickable() returns true.
     */
    public void tick() {
        for (Drawable d : widgets) {
            if (d instanceof AbstractPWidget) {
                if (((AbstractPWidget) d).isTickable()) {
                    ((AbstractPWidget) d).tick();
                }
            }
        }
    }

    /**
     * Method to forward mouseScrolled to all the widgets.
     *
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     * @param amount the amount
     * @return the boolean
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        boolean isTrue = false;
        for (Drawable widget : widgets) {
            if (widget instanceof AbstractPWidget) {
                if (!isTrue) {
                    isTrue = ((AbstractPWidget) widget).mouseScrolled(mouseX, mouseY, amount);
                } else {
                    ((AbstractPWidget) widget).mouseScrolled(mouseX, mouseY, amount);
                }
            }
        }
        return isTrue;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean isTrue = false;
        for (Drawable widget : widgets) {
            if (widget instanceof AbstractPWidget) {
                if (!isTrue) {
                    isTrue = ((AbstractPWidget) widget).mouseClicked(mouseX, mouseY, button);
                } else {
                    ((AbstractPWidget) widget).mouseClicked(mouseX, mouseY, button);
                }
            }
        }
        return isTrue;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        boolean isTrue = false;
        for (Drawable widget : widgets) {
            if (widget instanceof AbstractPWidget) {
                if (!isTrue) {
                    isTrue = ((AbstractPWidget) widget).mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
                } else {
                    ((AbstractPWidget) widget).mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
                }
            }
        }
        return isTrue;
    }
}

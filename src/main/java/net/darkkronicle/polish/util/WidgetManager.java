package net.darkkronicle.polish.util;

import lombok.Setter;
import net.darkkronicle.polish.gui.widgets.AbstractPWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class WidgetManager {

    private ArrayList<Drawable> widgets;
    private final Screen parent;
    @Setter
    // Minecrafts dumb V
    private List<Element> children;

    public WidgetManager(Screen parent, List<Element> children) {
        widgets = new ArrayList<>();
        this.parent = parent;
        this.children = children;
    }

    public void clear() {
        widgets.clear();
    }

    public void add(AbstractPWidget w) {
        widgets.add(w);
        children.add(w);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (Drawable d : widgets) {
            d.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void tick() {
        for (Drawable d : widgets) {
            if (d instanceof AbstractPWidget) {
                if (((AbstractPWidget) d).isTickable()) {
                    ((AbstractPWidget) d).tick();
                }
            }
        }
    }

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
}

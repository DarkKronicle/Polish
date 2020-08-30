package io.github.darkkronicle.polish.gui.screens;

import io.github.darkkronicle.polish.gui.widgets.AbstractPWidget;
import io.github.darkkronicle.polish.util.WidgetManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class AbstractConfigScreen extends Screen {

    public WidgetManager widgetManager;
    public int x, y, width, height;
    public Runnable save;

    public AbstractConfigScreen(Text title, int x, int y, int width, int height, Runnable save) {
        super(title);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.save = save;
        widgetManager = new WidgetManager(this, children);
    }

    public abstract void save();

    public abstract void reset();

    public void add(AbstractPWidget widget) {
        widgetManager.add(widget);
    }

    @Override
    public void tick() {
        widgetManager.tick();
        super.tick();
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        boolean yes = super.charTyped(chr, keyCode);
        widgetManager.charTyped(chr, keyCode);
        return yes;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        widgetManager.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        widgetManager.mouseScrolled(mouseX, mouseY, amount);
        return false;
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean yes = super.keyPressed(keyCode, scanCode, modifiers);
        widgetManager.keyPressed(keyCode, scanCode, modifiers);
        return yes;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean yes = super.mouseClicked(mouseX, mouseY, button);
        widgetManager.mouseClicked(mouseX, mouseY, button);
        return yes;
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        boolean yes = super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        widgetManager.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return yes;
    }
}

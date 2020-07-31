package net.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import net.darkkronicle.polish.util.DrawUtil;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;

public abstract class AbstractPWidget extends DrawUtil implements Drawable, Element {

    /**
     * int that is where the x anchor is
     */
    @Getter
    protected int offsetX;
    /**
     * int that is where the y anchor is
     */
    @Getter
    protected int offsetY;
    /**
     * int how far x off is from the anchor.
     */
    @Getter
    protected int relativeX;
    /**
     * int how for y off is from the anchor
     */
    @Getter
    protected int relativeY;
    @Getter
    protected int width;
    @Getter
    protected int height;
    @Getter
    protected boolean hovered;
    @Getter
    protected boolean wasHovered;

    public AbstractPWidget(int offsetX, int offsetY, int width, int height) {
        super();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    public int getAbsoluteX() {
        return offsetX + relativeX;
    }

    public int getAbsoluteY() {
        return offsetY + relativeY;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        render(matrices, mouseX, mouseY, delta, calcHover(mouseX, mouseY));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, boolean hovered) {
        this.hovered = hovered;
        renderWidget(matrices, mouseX, mouseY, delta);
        wasHovered = hovered;
    }

    public boolean calcHover(int mouseX, int mouseY) {
        int relativeX = mouseX - getAbsoluteX();
        int relativeY = mouseY - getAbsoluteY();
        return relativeX >= 0 && relativeX <= width && relativeY >= 0 && relativeY <= height;
    }

    public abstract void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta);

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered) {
            onClick(mouseX, mouseY, button);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (hovered) {
            onScroll(mouseX, mouseY, amount);
            return true;
        }
        return false;
    }

    public void onScroll(double mouseX, double mouseY, double amount) {

    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && hovered) {
            onDrag(mouseX, mouseY, button, deltaX, deltaY);
            return true;
        }
        return false;
    }

    public void onDrag(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

    }

    public void onClick(double mouseX, double mouseY, int button) {

    }

    public boolean isTickable() {
        return false;
    }

    public void tick() {

    }


    public void setPos(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    public void setRelativePos(int x, int y) {
        this.relativeX = x;
        this.relativeY = y;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

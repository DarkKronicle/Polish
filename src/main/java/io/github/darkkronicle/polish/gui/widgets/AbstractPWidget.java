package io.github.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.DrawUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Abstract class to create widgets.
 */
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
    /**
     * Widget width.
     */
    @Getter
    protected int width;
    /**
     * Widget height.
     */
    @Getter
    protected int height;
    /**
     * Is the widget rendered.
     */
    @Getter
    protected boolean hovered;
    /**
     * If the widget was hovered before finishing the render.
     */
    @Getter
    protected boolean wasHovered;
    /**
     * Tooltip that is applied when hovered.
     * //TODO NOT IMPLEMENTED FULLY
     */
    protected Text tooltip;


    /**
     * Constructor to assign without a tooltip.
     *
     * @param offsetX where x anchor is
     * @param offsetY where y anchor is
     * @param width   width of widget
     * @param height  height of widget
     */
    public AbstractPWidget(int offsetX, int offsetY, int width, int height) {
        this(offsetX, offsetY, width, height, null);
    }

    /**
     * Constructor to assign with a tooltip.
     *
     * @param offsetX where x anchor is
     * @param offsetY where y anchor is
     * @param width   width of widget
     * @param height  height of widget
     * @param tooltip {@link Text} that displays when hovering.
     */
    public AbstractPWidget(int offsetX, int offsetY, int width, int height, Text tooltip) {
        super();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.tooltip = tooltip;
    }

    /**
     * Gets X value combining relative and offset.
     *
     * @return absolute X value.
     */
    public int getAbsoluteX() {
        return offsetX + relativeX;
    }

    /**
     * Gets Y value combining relative and offset.
     *
     * @return absolute Y value.
     */
    public int getAbsoluteY() {
        return offsetY + relativeY;
    }

    /**
     * Renders the widget's tooltip.
     */
    public void renderTooltip(MatrixStack matrices) {
        if (tooltip != null && !tooltip.getString().isEmpty()) {
            rect(matrices, getAbsoluteX() - 20, getAbsoluteY() - 40, width + 40, 40, Colors.DARKGRAY.color().color());
            List<StringRenderable> lines = ChatMessages.breakRenderedChatMessageLines(tooltip, width + 38, MinecraftClient.getInstance().textRenderer);
            int lineY = 0;
            for (StringRenderable line : lines) {
                drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, line, getAbsoluteX() - 18, getAbsoluteY() - 40 + lineY, Colors.WHITE.color().color());
                lineY += 10;
            }
        }
    }

    /**
     * Renders the widget.
     * @param matrices {@link MatrixStack}
     * @param mouseX current mouse X
     * @param mouseY current mouse Y
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        render(matrices, mouseX, mouseY, delta, calcHover(mouseX, mouseY));
    }

    /**
     * Renders the widget, but it allows for specification if the widget is hovered.
     *
     * @param matrices {@link MatrixStack}
     * @param mouseX   current mouse X
     * @param mouseY   current mouse Y
     * @param hovered  Is the widget currently hovered
     */
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, boolean hovered) {
        this.hovered = hovered;
        renderWidget(matrices, mouseX, mouseY, delta);
        wasHovered = hovered;
    }

    /**
     * Calculates if the widget is currently hovered by taking the width, height, x, and y values and checking if the
     * mouse is in there.
     *
     * @param mouseX current mouse X
     * @param mouseY current mouse Y
     * @return boolean boolean
     */
    public boolean calcHover(int mouseX, int mouseY) {
        int relativeX = mouseX - getAbsoluteX();
        int relativeY = mouseY - getAbsoluteY();
        return relativeX >= 0 && relativeX <= width && relativeY >= 0 && relativeY <= height;
    }

    /**
     * Renders the widget, but only deals with the rendering, not hovering or any calculations.
     *
     * @param matrices {@link MatrixStack}
     * @param mouseX   current mouse X
     * @param mouseY   current mouse Y
     * @param delta    the delta
     */
    public abstract void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta);

    /**
     * Overrides {@link Drawable} mouseClicked method and triggers onClick method if it is currently hovered.
     * @param mouseX current mouse X
     * @param mouseY current mouse Y
     * @param button what type of button it is
     * @return if it is successful.
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered) {
            return onClick(mouseX, mouseY, button);
        }
        return false;
    }

    /**
     * Overrides {@link Drawable} mouseScrolled method and triggers onScroll method if it is currently hovered.
     * @param mouseX Current mouse X
     * @param mouseY Current mouse Y
     * @param amount How much is scrolled.
     * @return If it is successful.
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (hovered) {
            onScroll(mouseX, mouseY, amount);
            return true;
        }
        return false;
    }

    /**
     * What gets called when the mouse is over the widget and scrolls.
     *
     * @param mouseX Current mouse X
     * @param mouseY Current mouse Y
     * @param amount the amount
     */
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

    /**
     * Method that gets called when the mouse is over the widget and dragged.
     *
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     * @param button the button
     * @param deltaX how much it was dragged horizontally
     * @param deltaY how much it was dragged vertically
     */
    public void onDrag(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

    }

    /**
     * Method that gets called when the mouse is over the widget and clicked.
     *
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     * @param button the button
     */
    public boolean onClick(double mouseX, double mouseY, int button) {
        return false;
    }

    /**
     * Widget only ticks if this returns true. Override to change the value.
     *
     * @return is it tickable
     */
    public boolean isTickable() {
        return false;
    }

    /**
     * Called when the widget ticks.
     */
    public void tick() {

    }


    /**
     * Sets offset position.
     *
     * @param x the x
     * @param y the y
     */
    public void setOffsetPos(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    /**
     * Sets relative position.
     *
     * @param x the x
     * @param y the y
     */
    public void setRelativePos(int x, int y) {
        this.relativeX = x;
        this.relativeY = y;
    }

    /**
     * Sets dimensions.
     *
     * @param width the width
     * @param height the height
     */
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

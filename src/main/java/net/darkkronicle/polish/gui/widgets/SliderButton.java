package net.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.EasingFunctions;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;

/**
 * An abstract class for creating sliders
 *
 * @param <K> what type of data the slider takes
 */
public abstract class SliderButton<K> extends AbstractPWidget {

    /**
     * The scale used for finer details
     */
    private float scale = 0.25F;

    /**
     * The slider color.
     */
    private SimpleColor sliderColor = Colors.BLACK.color();
    /**
     * The circle color.
     */
    private SimpleColor circleColor = Colors.SELECTOR_BLUE.color();
    /**
     * When the hover animation starts. If -1 it's done.
     */
    private float hoverStart = -1;
    /**
     * How long the hover animation is in milliseconds.
     */
    private int hoverAnim = 300;
    /**
     * The min value K can be.
     */
    @Getter
    protected K min;
    /**
     * The max value K can be.
     */
    @Getter
    protected K max;
    /**
     * The current value of the slider.
     */
    protected K value;

    /**
     * Instantiates a new Slider button.
     *
     * @param x     the x
     * @param y     the y
     * @param width the width
     */
    public SliderButton(int x, int y, int width) {
        super(x, y, width, 9);
    }

    /**
     * Gets current percent the slider is at.
     *
     * @return the percent
     */
    public abstract double getPercent();

    /**
     * Gets value used for rendering.
     *
     * @return the value
     */
    public abstract String getValue();

    /**
     * Gets the raw value of K
     *
     * @return the raw value
     */
    public K getRawValue() {
        return value;
    }

    /**
     * Sets raw value.
     *
     * @param value the value
     */
    public void setRawValue(K value) {
        this.value = value;
    }

    /**
     * Sets the percent of the slider.
     *
     * @param percent the percent
     */
    public abstract void setPercent(double percent);

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        matrices.scale(scale, scale, 1);

        if (isHovered() != isWasHovered()) {
            hoverStart = Util.getMeasuringTimeMs();
        }

        double hoverTrans = hoverStart == -1 ? 1 : ((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim);
        SimpleColor background;
        SimpleColor hightlight = ColorUtil.blend(sliderColor, Colors.WHITE.color(), 0.2F);
        if (isHovered()) {
            background = ColorUtil.blend(sliderColor, hightlight, (float) EasingFunctions.Types.CUBIC_IN_OUT.apply(hoverTrans));
        } else {
            background = ColorUtil.blend(hightlight, sliderColor, (float) EasingFunctions.Types.CUBIC_IN_OUT.apply(hoverTrans));
        }

        int scaledWidth = Math.round((width - 2) / scale);
        int scaledHeight = Math.round(height / scale);
        int scaledX = Math.round(getAbsoluteX() / scale);
        int scaledY = Math.round(getAbsoluteY() / scale);
        double actualWidth = scaledWidth - scaledHeight;

        rect(matrices, scaledX + (scaledHeight / 2), scaledY + (scaledHeight / 2) - 2, scaledWidth - scaledHeight, 5, background.color());
        circle(matrices, (int) Math.round(scaledX + (actualWidth * getPercent()) + ((float) scaledHeight / 2)), scaledY + (scaledHeight / 2), scaledHeight / 2 - 2, circleColor.color());

        matrices.scale(4, 4, 1);
        drawCenteredString(matrices, MinecraftClient.getInstance().textRenderer, getValue(), getAbsoluteX() + (width / 2), getAbsoluteY() + (int) ((float) height / 2 - 3), Colors.WHITE.color().withAlpha(100).color());
        if ((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim >= 1) {
            hoverStart = -1;
        }
        matrices.pop();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        setValueFromMouse(mouseX);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrag(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        setValueFromMouse(mouseX);
    }

    /**
     * Sets value from mouse.
     *
     * @param mouseX the mouse x
     */
    public void setValueFromMouse(double mouseX) {
        double pos = mouseX - getAbsoluteX() - (double) height / 2;
        int actualWidth = width - height;
        double per = (float) pos / actualWidth;
        setPercent(per);
    }
}

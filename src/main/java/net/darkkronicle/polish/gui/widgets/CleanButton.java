package net.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import lombok.Setter;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.EasingFunctions;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

/**
 * A clean button. Doesn't change any values, just has nice animations and an onClick event.
 */
public class CleanButton extends AbstractPWidget {

    /**
     * The background color.
     */
    @Setter
    @Getter
    private SimpleColor baseColor;
    /**
     * The highlight color.
     */
    @Getter
    private SimpleColor highlightColor;
    /**
     * The border color.
     */
    @Getter
    private SimpleColor borderColor;


    private MinecraftClient client;
    /**
     * Show border
     */
    private boolean border;
    /**
     * Message on button
     */
    @Getter
    private Text message;
    /**
     * The scale used for fine details.
     */
    private float scale = 0.5F;
    /**
     * When the hover animation starts. If -1 it's done.
     */
    private double hoverStart = -1;
    /**
     * How long the hover animation takes in milliseconds.
     */
    private int hoverAnim = 300;


    /**
     * What happens on press
     */
    @Setter
    @Getter
    private OnPress onPress;

    /**
     * Instantiates a new Clean button with a default highlight color and border color.
     *
     * @param x                the x
     * @param y               the y
     * @param width           the width
     * @param height          the height
     * @param baseColor       the background color
     * @param text            the text for the button
     * @param onPress         the {@link OnPress}
     */
    public CleanButton(int x, int y, int width, int height, SimpleColor baseColor, Text text, OnPress onPress) {
        this(x, y, width, height, baseColor, ColorUtil.blend(baseColor, Colors.WHITE.color(), 0.2F), text, onPress);
    }

    /**
     * Instantiates a new Clean button with the default border color.
     *
     * @param x              the x
     * @param y               the y
     * @param width           the width
     * @param height          the height
     * @param baseColor       the background color
     * @param text            the text for the button
     * @param onPress         the {@link OnPress}
     */
    public CleanButton(int x, int y, int width, int height, SimpleColor baseColor, SimpleColor highlightColor, Text text, OnPress onPress) {
        this(x, y, width, height, baseColor, highlightColor, null, text, onPress);
    }


    /**
     * Instantiates a new Clean button.
     *
     * @param x               the x
     * @param y               the y
     * @param width           the width
     * @param height          the height
     * @param baseColor       the background color
     * @param hightlightColor the hightlight color
     * @param borderColor     the border color
     * @param text            the text for the button
     * @param onPress         the {@link OnPress}
     */
    public CleanButton(int x, int y, int width, int height, SimpleColor baseColor, SimpleColor hightlightColor, SimpleColor borderColor, Text text, OnPress onPress) {
        super(x, y, width, height);
        this.onPress = onPress;
        this.baseColor = baseColor;
        this.highlightColor = hightlightColor;
        this.borderColor = borderColor;
        this.message = text;
        border = borderColor != null;
        this.client = MinecraftClient.getInstance();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        onPress.onPress(this);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        matrices.scale(scale, scale, 1);
        int scaledHeight = Math.round(height / scale);
        int scaledWidth = Math.round(width / scale);
        int scaledX = Math.round(getAbsoluteX() / scale);
        int scaledY = Math.round(getAbsoluteY() / scale);
        SimpleColor color;
        if (isHovered() != isWasHovered()) {
            hoverStart = Util.getMeasuringTimeMs();
        }
        double hoverTrans = hoverStart == -1 ? 1 : MathHelper.clamp((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim, 0, 1);
        if (isHovered()) {
            color = ColorUtil.blend(baseColor, highlightColor, (float) EasingFunctions.Types.SINE_IN.apply(hoverTrans));
        } else {
            color = ColorUtil.blend(highlightColor, baseColor, (float) EasingFunctions.Types.SINE_OUT.apply(hoverTrans));
        }
        if (((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim) >= 1) {
            hoverStart = -1;
        }
        fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 9, color.color());
        if (border) {
            outlineRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 9, borderColor.color());
        }
        matrices.scale(1 / scale, 1 / scale, 1);
        drawCenteredText(matrices, client.textRenderer, message, (getAbsoluteX() + (width / 2)), (getAbsoluteY() + (height / 2) - 3), Colors.WHITE.color().color());
        matrices.pop();


    }

    /**
     * The interface OnPress that has a method of what to do when a button is pressed.
     */
    public interface OnPress {
        /**
         * Method that triggers when button is pressed.
         *
         * @param button the parent button
         */
        void onPress(CleanButton button);
    }

}

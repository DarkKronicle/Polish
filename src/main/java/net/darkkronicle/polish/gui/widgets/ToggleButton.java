package net.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import lombok.Setter;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.EasingFunctions;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class ToggleButton extends AbstractPWidget {

    private int smallWidth;
    private Text on;
    private Text off;
    @Getter
    @Setter
    private boolean selected;
    private boolean wasSelected;
    private float scale = 0.5F;
    private float transStart = -1;
    private float hoverStart = -1;
    private int moveAnim = 200;
    private int hoverAnim = 300;
    private MinecraftClient client;

    public ToggleButton(int x, int y, int width, int height, boolean selected) {
        this(x, y, width, height, selected, new LiteralText("ON"), new LiteralText("OFF"));
    }

    public ToggleButton(int x, int y, int width, int height, boolean selected, Text on, Text off) {
        super(x, y, width, height);
        this.selected = selected;
        this.on = on;
        this.off = off;
        this.smallWidth = (int) Math.round((double)(width * 3) / 5);
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        int scaledWidth = Math.round(width / scale);
        int scaledHeight = Math.round(height / scale);
        int scaledX = Math.round(getAbsoluteX() / scale);
        int scaledY = Math.round(getAbsoluteY() / scale);
        matrices.scale(scale, scale, 1);

        if (isWasHovered() != isHovered()) {
            hoverStart = Util.getMeasuringTimeMs();
        }

        SimpleColor background = Colors.DARKGRAY.color();
        SimpleColor border = Colors.BLACK.color();
        SimpleColor backHover = ColorUtil.blend(background, Colors.WHITE.color(), 0.2F);
        SimpleColor borderHover = ColorUtil.blend(border, Colors.WHITE.color(), 0.2F);

        double hoverTrans = hoverStart == -1 ? 1.0 : MathHelper.clamp((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim, 0, 1);
        if (isHovered()) {
            background = ColorUtil.blend(background, backHover, (float) EasingFunctions.Types.SINE_IN.apply(hoverTrans));
            border = ColorUtil.blend(border, borderHover, (float) EasingFunctions.Types.SINE_IN.apply(hoverTrans));
        } else {
            background = ColorUtil.blend(backHover, background, (float) EasingFunctions.Types.SINE_IN.apply(hoverTrans));
            border = ColorUtil.blend(borderHover, border, (float) EasingFunctions.Types.SINE_IN.apply(hoverTrans));
        }

        fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 3, background.color());
        outlineRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 3, border.color());
        int scaledSmallWidth = Math.round(smallWidth / scale);
        if (wasSelected != selected) {
            transStart = Util.getMeasuringTimeMs();
        }
        SimpleColor green = Colors.SELECTOR_GREEN.color();
        SimpleColor red = Colors.SELECTOR_RED.color();
        double trans = transStart == -1 ? 1.0 : MathHelper.clamp((Util.getMeasuringTimeMs() - transStart) / moveAnim, 0, 1);
        int newX;
        if (selected) {
            SimpleColor color = ColorUtil.blend(red, green, (float) EasingFunctions.Types.LINEAR.apply(trans));
            newX = ColorUtil.blendInt(scaledX + 1 + (scaledWidth - scaledSmallWidth), scaledX + 1, (float) EasingFunctions.Types.LINEAR.apply(trans));
            fillRoundedRect(matrices, newX, scaledY + 1, scaledSmallWidth - 2, scaledHeight - 2, 5, color.color());
            drawCenteredText(matrices, client.textRenderer, on,newX + (scaledSmallWidth / 2), scaledY + (scaledHeight / 2) - 4, Colors.WHITE.color().color());
        } else {
            SimpleColor color = ColorUtil.blend(green, red, (float) EasingFunctions.Types.LINEAR.apply(trans));
            newX = ColorUtil.blendInt(scaledX + 1, scaledX + 1 + (scaledWidth - scaledSmallWidth),  (float) EasingFunctions.Types.LINEAR.apply(trans));
            fillRoundedRect(matrices, newX, scaledY + 1, scaledSmallWidth - 2, scaledHeight - 2, 5, color.color());
            drawCenteredText(matrices, client.textRenderer, off,newX + (scaledSmallWidth / 2), scaledY + (scaledHeight / 2) - 4, Colors.WHITE.color().color());
        }
        if ((Util.getMeasuringTimeMs() - transStart) / moveAnim >= 1) {
            transStart = -1;
        }
        if ((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim >= 1) {
            hoverStart = -1;
        }
        wasSelected = selected;
        matrices.pop();
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        selected = !selected;
    }

}

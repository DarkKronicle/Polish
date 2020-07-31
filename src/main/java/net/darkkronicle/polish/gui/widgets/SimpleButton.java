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

public class SimpleButton extends AbstractPWidget {

    @Setter
    @Getter
    private SimpleColor baseColor;
    @Getter
    private SimpleColor highlightColor;
    @Getter
    private SimpleColor borderColor;
    private MinecraftClient client;
    private boolean border;
    @Getter
    private Text message;
    private double hoverStart = -1;
    private int hoverAnim = 300;


    @Setter
    @Getter
    private SimpleButton.OnPress onPress;

    public SimpleButton(int x, int y, int width, int height, SimpleColor baseColor, Text text, SimpleButton.OnPress onPress) {
        this(x, y, width, height, baseColor, ColorUtil.blend(baseColor, Colors.WHITE.color(), 0.2F), text, onPress);
    }

    public SimpleButton(int x, int y, int width, int height, SimpleColor baseColor, SimpleColor highlightColor, Text text, SimpleButton.OnPress onPress) {
        this(x, y, width, height, baseColor, highlightColor, null, text, onPress);
    }


    public SimpleButton(int x, int y, int width, int height, SimpleColor baseColor, SimpleColor hightlightColor, SimpleColor borderColor, Text text, SimpleButton.OnPress onPress) {
        super(x, y, width, height);
        this.onPress = onPress;
        this.baseColor = baseColor;
        this.highlightColor = hightlightColor;
        this.borderColor = borderColor;
        this.message = text;
        border = borderColor != null;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        onPress.onPress(this);
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
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
        rect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, color.color());
        if (border) {
            outlineRect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, borderColor.color());
        }
        drawCenteredText(matrices, client.textRenderer, message, (getAbsoluteX() + (width / 2)), (getAbsoluteY() + (height / 2) - 3), Colors.WHITE.color().color());
        matrices.pop();
    }

    public interface OnPress {
        void onPress(SimpleButton button);
    }
}

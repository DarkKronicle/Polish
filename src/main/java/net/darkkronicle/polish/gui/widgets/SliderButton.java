package net.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.EasingFunctions;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;

public abstract class SliderButton<K> extends AbstractPWidget {

    private float scale = 0.25F;

    private SimpleColor sliderColor = Colors.BLACK.color();
    private SimpleColor circleColor = Colors.SELECTOR_BLUE.color();
    private float hoverStart = -1;
    private int hoverAnim = 300;
    @Getter
    protected K min;
    @Getter
    protected K max;
    protected K value;

    public SliderButton(int x, int y, int width) {
        super(x, y, width, 9);
    }

    public abstract double getPercent();

    public abstract String getValue();

    public K getRawValue() {
        return value;
    }

    public void setRawValue(K value) {
        this.value = value;
    }

    public abstract void setPercent(double percent);

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

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        setValueFromMouse(mouseX);
    }

    @Override
    public void onDrag(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        setValueFromMouse(mouseX);
    }

    public void setValueFromMouse(double mouseX) {
        double pos = mouseX - getAbsoluteX() - (double) height / 2;
        int actualWidth = width - height;
        double per = (float) pos / actualWidth;
        setPercent(per);
    }
}

package io.github.darkkronicle.polish.gui.complexwidgets;

import io.github.darkkronicle.polish.gui.widgets.AbstractPWidget;
import io.github.darkkronicle.polish.gui.widgets.ToggleButton;
import io.github.darkkronicle.polish.util.ColorUtil;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.EasingFunctions;
import io.github.darkkronicle.polish.util.SimpleColor;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class ToggleModuleWidget extends AbstractPComplexWidget {

    private ToggleButton button;
    @Getter
    private Text text;
    private OnPress onClick;
    private float hoverStart = -1;
    private SimpleColor color = Colors.WHITE.color().withAlpha(50);
    private int hoverAnim = 300;

    /**
     * Instantiates a new Abstract complex width
     *
     * @param x      the x
     * @param y      the y
     */
    public ToggleModuleWidget(int x, int y, Text text, boolean on, OnPress click) {
        super(x, y, 80, 120);
        this.text = text;
        button = new ToggleButton(getAbsoluteX(), getAbsoluteY(), 25, 10, on);
        button.setRelativePos((width / 2 - 12), (height / 2 + 30));
        onClick = click;
        add(button);
    }

    @Override
    public void setRelativePos(int x, int y) {
        super.setRelativePos(x, y);
        button.setRelativePos((width / 2 - 12) + x, (height / 2 + 30) + y);
    }

    @Override
    public void setOffsetPos(int x, int y) {
        super.setOffsetPos(x, y);
        button.setOffsetPos(getAbsoluteX(), getAbsoluteY());
    }

    public boolean getValue() {
        return button.isSelected();
    }

    public void setValue(boolean value) {
        button.setSelected(value);
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        SimpleColor bcolor;
        if (isHovered() != isWasHovered()) {
            hoverStart = Util.getMeasuringTimeMs();
        }
        double hoverTrans = hoverStart == -1 ? 1 : MathHelper.clamp((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim, 0, 1);
        if (isHovered()) {
            bcolor = ColorUtil.blend(Colors.WHITE.color().withAlpha(0), color, (float) EasingFunctions.Types.SINE_IN.apply(hoverTrans));
        } else {
            bcolor = ColorUtil.blend(color, Colors.WHITE.color().withAlpha(0), (float) EasingFunctions.Types.SINE_OUT.apply(hoverTrans));
        }
        if (((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim) >= 1) {
            hoverStart = -1;
        }
        rect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, bcolor.color());
        outlineRect(matrices, getAbsoluteX(), getAbsoluteY(), width, height, Colors.GRAY.color().color());
        drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, text.asOrderedText(), (width / 2) + getAbsoluteX(), (height / 2 - 20) + getAbsoluteY(), Colors.WHITE.color().color());

        matrices.pop();
    }

    @Override
    public boolean onClick(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered) {
            for (AbstractPWidget sibling : siblings) {
                if (sibling.isHovered()) {
                    sibling.onClick(mouseX, mouseY, button);
                    return true;
                }
            }
            onSecondClick(mouseX, mouseY, button);
            return true;
        }
        return false;
    }

    public void onSecondClick(double mouseX, double mouseY, int button) {
        onClick.onPress(this);
    }

    public interface OnPress {
        void onPress(ToggleModuleWidget widget);
    }
}

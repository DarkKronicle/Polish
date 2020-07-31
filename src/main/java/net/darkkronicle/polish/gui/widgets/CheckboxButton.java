package net.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import lombok.Setter;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.EasingFunctions;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class CheckboxButton extends AbstractPWidget {

    private int pressAnim = 100;
    private float pressStart = -1;
    private int hoverAnim = 300;
    private float hoverStart = -1;
    private float scale = 0.5F;
    @Getter
    @Setter
    private boolean selected;
    private boolean wasSelected;
    @Getter
    private int size;

    public CheckboxButton(int x, int y, int size, boolean selected) {
        super(x, y, size, size);
        this.selected = selected;
        this.size = size;
        this.wasSelected = selected;
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        int scaledWidth = Math.round(width / scale);
        int scaledHeight = Math.round(height / scale);
        int scaledX = Math.round(getAbsoluteX() / scale);
        int scaledY = Math.round(getAbsoluteY() / scale);
        matrices.scale(scale, scale, 1);
        SimpleColor background = Colors.SELECTOR_BLUE.color();
        SimpleColor border = ColorUtil.blend(background, Colors.BLACK.color(), 0.2F);
        SimpleColor hoverBackground = ColorUtil.blend(background, Colors.WHITE.color(), 0.3F);

        if (isHovered() != isWasHovered()) {
            hoverStart = Util.getMeasuringTimeMs();
        }
        double hoverTrans = hoverStart == -1 ? 1 : MathHelper.clamp((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim, 0, 1);
        if (isHovered()) {
            background = ColorUtil.blend(background, hoverBackground, (float) EasingFunctions.Types.SINE_IN.apply(hoverTrans));
        } else {
            background = ColorUtil.blend(hoverBackground, background, (float) EasingFunctions.Types.SINE_OUT.apply(hoverTrans));
        }
        fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 3, background.color());
        outlineRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 3, border.color());

        if (selected != wasSelected) {
            pressStart = Util.getMeasuringTimeMs();
        }
        double pressTrans = pressStart == -1 ? 1 : MathHelper.clamp((Util.getMeasuringTimeMs() - pressStart) / pressAnim, 0, 1);
        if (selected) {
            int relativeSize = Math.round(size / scale) - 4;
            SimpleColor checkColor = ColorUtil.blend(background, Colors.WHITE.color(), (float) EasingFunctions.Types.SINE_IN.apply(pressTrans));
            drawCheckmark(matrices, scaledX + 1, scaledY + 3, relativeSize, checkColor.color());
        } else {
            if (pressTrans < 1) {
                int relativeSize = Math.round(size / scale) - 4;
                SimpleColor checkColor = ColorUtil.blend(Colors.WHITE.color(), background, (float) EasingFunctions.Types.SINE_IN.apply(pressTrans));
                drawCheckmark(matrices, scaledX + 1, scaledY + 3, relativeSize, checkColor.color());
            }
        }
        if ((Util.getMeasuringTimeMs() - hoverStart) / hoverAnim >= 1) {
            hoverStart = -1;
        }
        if ((Util.getMeasuringTimeMs() - pressStart) / pressAnim >= 1) {
            pressStart = -1;
        }
        wasSelected = selected;
        matrices.pop();
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        selected = !selected;
    }
}

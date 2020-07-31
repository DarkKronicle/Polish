package net.darkkronicle.polish.gui.widgets;

import lombok.Getter;
import lombok.Setter;
import net.darkkronicle.polish.util.ColorUtil;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.EasingFunctions;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SelectorButton<K> extends AbstractPWidget {
    @Getter
    private LinkedHashMap<K, String> entries;
    @Setter
    private Map.Entry<K, String> current;
    public int arrowWidth;
    private SimpleColor backgroundColor;
    private float scale = 0.5F;
    private int hoverAnim = 300;
    private float leftAStart = -1;
    private float rightAStart = -1;
    private float allStart = -1;

    private boolean leftHover = false;
    private boolean rightHover = false;
    private boolean wasLeftHover = false;
    private boolean wasRightHover = false;

    public SelectorButton(int x, int y, int width, int height, int arrowWidth, SimpleColor backgroundColor) {
        super(x, y, width, height);
        entries = new LinkedHashMap<>();
        this.arrowWidth = arrowWidth + 2;
        this.backgroundColor = backgroundColor;
    }

    public SelectorButton<K> add(K entry, String name) {
        entries.put(entry, name);
        return this;
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        if (entries.size() < 1) {
            throw new NullPointerException("No entries in SelectorButton");
        }
        if (current == null) {
            current = entries.entrySet().iterator().next();
        }
        int relativeX = mouseX - getAbsoluteX();
        int relativeY = mouseY - getAbsoluteY();
        leftHover = relativeX >= 0 && relativeX <= arrowWidth + 2 && relativeY >= 0 && relativeY <= height;
        rightHover = relativeX >= (width - arrowWidth - 2) && relativeX <= width && relativeY >= 0 && relativeY <= height;

        matrices.scale(scale, scale, 1);
        int scaledWidth = Math.round(width / scale);
        int scaledHeight = Math.round(height / scale);
        int scaledArrowWidth = Math.round(arrowWidth / scale);
        int scaledX = Math.round(getAbsoluteX() / scale);
        int scaledY = Math.round(getAbsoluteY() / scale);
        float scaleBack = 1 / scale;

        if (isHovered() != isWasHovered()) {
            allStart = Util.getMeasuringTimeMs();
        }
        if (leftHover != wasLeftHover) {
            leftAStart = Util.getMeasuringTimeMs();
        }
        if (rightHover != wasRightHover) {
            rightAStart = Util.getMeasuringTimeMs();
        }

        double allHoverTrans = allStart == -1 ? 1 : ((Util.getMeasuringTimeMs() - allStart) / hoverAnim);
        double leftHoverTrans = leftAStart == -1 ? 1 : ((Util.getMeasuringTimeMs() - leftAStart) / hoverAnim);
        double rightHoverTrans = rightAStart == -1 ? 1 : ((Util.getMeasuringTimeMs() - rightAStart) / hoverAnim);
        SimpleColor border;
        SimpleColor leftBack;
        SimpleColor rightBack;
        SimpleColor highLight = ColorUtil.blend(backgroundColor, Colors.WHITE.color().withAlpha(backgroundColor.alpha()), 0.2F);
        if (isHovered()) {
            border = ColorUtil.blend(backgroundColor, Colors.WHITE.color(), (float) EasingFunctions.Types.SINE_IN_OUT.apply(allHoverTrans));
        } else {
            border = ColorUtil.blend(Colors.WHITE.color(),backgroundColor,  (float) EasingFunctions.Types.SINE_IN_OUT.apply(allHoverTrans));
        }
        if (leftHover) {
            leftBack = ColorUtil.blend(backgroundColor, highLight, (float) EasingFunctions.Types.CUBIC_IN_OUT.apply(leftHoverTrans));
        } else {
            leftBack = ColorUtil.blend(highLight, backgroundColor, (float) EasingFunctions.Types.CUBIC_IN_OUT.apply(leftHoverTrans));
        }
        if (rightHover) {
            rightBack = ColorUtil.blend(backgroundColor, highLight, (float) EasingFunctions.Types.CUBIC_IN_OUT.apply(rightHoverTrans));
        } else {
            rightBack = ColorUtil.blend(highLight, backgroundColor, (float) EasingFunctions.Types.CUBIC_IN_OUT.apply(rightHoverTrans));
        }

        // Left
        fillLeftRoundedRect(matrices, scaledX, scaledY, scaledArrowWidth + 6, scaledHeight, 11, leftBack.color());
        // Middle
        rect(matrices, scaledX + scaledArrowWidth + 5, scaledY, scaledWidth - scaledArrowWidth - scaledArrowWidth - 8, scaledHeight, backgroundColor.color());
        // Right
        fillRightRoundedRect(matrices, scaledX + scaledWidth - scaledArrowWidth - 4, scaledY, scaledArrowWidth + 4, scaledHeight, 11, rightBack.color());

        outlineRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 11, border.color());


        drawLeftArrow(matrices, scaledX + 2, scaledY + ((scaledHeight - (scaledArrowWidth * 2)) / 2), scaledArrowWidth * 2, Colors.WHITE.color().color());

        drawRightArrow(matrices, (scaledX + scaledWidth - scaledArrowWidth - 3), scaledY + ((scaledHeight - (scaledArrowWidth * 2)) / 2), scaledArrowWidth * 2, Colors.WHITE.color().color());


        matrices.scale(scaleBack, scaleBack, 1);
        drawCenteredString(matrices, MinecraftClient.getInstance().textRenderer, current.getValue(), getAbsoluteX() + (width / 2), (getAbsoluteY() + (height / 2) - 4), Colors.WHITE.color().color());

        matrices.pop();

        if ((Util.getMeasuringTimeMs() - allStart) / hoverAnim >= 1) {
            allStart = -1;
        }
        if ((Util.getMeasuringTimeMs() - rightAStart) / hoverAnim >= 1) {
            rightAStart = -1;
        }
        if ((Util.getMeasuringTimeMs() - leftAStart) / hoverAnim >= 1) {
            leftAStart = -1;
        }


        wasLeftHover = leftHover;
        wasRightHover = rightHover;
    }

    public Map.Entry<K, String> next() {
        List<K> keys = new ArrayList<>(entries.keySet());
        int index = keys.indexOf(current.getKey());
        int newindex = index + 1 >= keys.size() ? 0 : index + 1;
        current = entryFromObj(keys.get(newindex));
        return current;
    }

    public Map.Entry<K, String> entryFromObj(K key) {
        for (Map.Entry<K, String> next : entries.entrySet()) {
            if (next.getKey().equals(key)) {
                return next;
            }
        }
        return current;
    }

    public Map.Entry<K, String> previous() {
        List<K> keys = new ArrayList<>(entries.keySet());
        int index = keys.indexOf(current.getKey());
        int newindex = index - 1 < 0 ? keys.size() - 1 : index - 1;
        current = entryFromObj(keys.get(newindex));
        return current;
    }

    public Map.Entry<K, String> getCurrent() {
        return current;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int relativeX = (int) Math.round(mouseX) - getAbsoluteX();
        int relativeY = (int) Math.round(mouseY) - getAbsoluteY();
        if (relativeX >= 0 && relativeX <= arrowWidth + 2 && relativeY >= 0 && relativeY <= height) {
            previous();
            return true;
        }
        if (relativeX >= (width - arrowWidth - 2) && relativeX <= width && relativeY >= 0 && relativeY <= height) {
            next();
            return true;
        }
        return false;
    }
}

package io.github.darkkronicle.polish.gui.screens;

import io.github.darkkronicle.polish.api.AbstractPEntry;
import io.github.darkkronicle.polish.gui.complexwidgets.AbstractPWidgetList;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.DrawUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class BasicConfigScreen extends AbstractConfigScreen {

    public BasicConfigScreen(Text title, EntryButtonList widget, Runnable save) {
        super(title, (MinecraftClient.getInstance().getWindow().getScaledWidth() / 2) - (getWidth() / 2), (MinecraftClient.getInstance().getWindow().getScaledHeight() / 2) - (getHeight() / 2), getWidth(), getHeight(), save);
        widget.setOffsetPos(x + 10, y + 30);
        widget.setDimensions(580, 150);
        add(widget);
//        CleanButton reset = new CleanButton(x, y, 40, 15, Colors.BLACK.color().withAlpha(180), new LiteralText("Reset All"), button -> reset());
//        reset.setRelativePos(width - 45, 5);
//        add(reset);
    }

    public static EntryButtonList createButtonList(int columns) {
        MinecraftClient client = MinecraftClient.getInstance();
        int y = getHeight();
        int x = getWidth();
        return new EntryButtonList((client.getWindow().getScaledWidth() / 2) - 290, (client.getWindow().getScaledHeight() / 2) - 70, x - 20, y - 50, columns, false);
    }

    public static int getHeight() {
        MinecraftClient client = MinecraftClient.getInstance();
        int y = 200;
        if (client.getWindow().getScaledHeight() < y - 30) {
            y = client.getWindow().getScaledHeight() - 30;
        }
        return y;
    }

    public static int getWidth() {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = 600;
        if (client.getWindow().getScaledWidth() < x - 30) {
            x = client.getWindow().getScaledWidth() - 30;
        }
        return x;
    }

    @Override
    public void onClose() {
        save();
        super.onClose();
    }

    @Override
    public void save() {
        for (Drawable widget : widgetManager.getWidgets()) {
            if (widget instanceof EntryButtonList) {
                for (AbstractPWidgetList.Entry<?> entry : ((EntryButtonList) widget).getEntries()) {
                    if (entry instanceof AbstractPEntry) {
                        ((AbstractPEntry<?, ?>) entry).save();
                    }
                }
            }
        }
        save.run();
    }

    @Override
    public void reset() {
        for (Drawable widget : widgetManager.getWidgets()) {
            if (widget instanceof EntryButtonList) {
                for (AbstractPWidgetList.Entry<?> entry : ((EntryButtonList) widget).getEntries()) {
                    if (entry instanceof AbstractPEntry) {
                        ((AbstractPEntry<?, ?>) entry).reset();
                    }
                }
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        float scale = 0.5F;
        drawCenteredText(matrices, client.textRenderer, title, client.getWindow().getScaledWidth() / 2, y + 15, Colors.WHITE.color().color());
        matrices.scale(0.5F, 0.5F, 1);
        int scaledX = Math.round(x / scale);
        int scaledY = Math.round(y / scale);
        int scaledWidth = Math.round(width / scale);
        int scaledHeight = Math.round(height / scale);
        DrawUtil.fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 15, Colors.DARKGRAY.color().withAlpha(150).color());
        matrices.scale(2, 2, 1);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

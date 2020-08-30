package io.github.darkkronicle.polish.gui.screens;

import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.gui.complexwidgets.ToggleModuleList;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.DrawUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ToggleModuleScreen extends AbstractConfigScreen {

    public ToggleModuleList list;

    public ToggleModuleScreen(Text title, ToggleModuleList list, Runnable save) {
        super(title, (MinecraftClient.getInstance().getWindow().getScaledWidth() / 2) - (getWidth() / 2), (MinecraftClient.getInstance().getWindow().getScaledHeight() / 2) - (getHeight() / 2), getWidth(), getHeight(), save);
        this.list = list;
//        this.list.setOffsetPos(x + 10, y + 30);
//        this.list.setDimensions(580, 150);
        add(this.list);
    }

    public static ToggleModuleList createButtonList() {
        MinecraftClient client = MinecraftClient.getInstance();
        int y = getHeight();
        int x = getWidth();
        return new ToggleModuleList((client.getWindow().getScaledWidth() / 2) - ((x - 20) / 2), (client.getWindow().getScaledHeight() / 2) - ((y - 50) / 2), x - 20, y - 50);
    }

    @Override
    public void save() {
        list.save();
        save.run();
    }

    @Override
    public void reset() {

    }

    @Override
    public void onClose() {
        save();
        super.onClose();
    }

    public static int getHeight() {
        MinecraftClient client = MinecraftClient.getInstance();
        int y = 400;
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        float scale = 0.5F;
        matrices.scale(0.5F, 0.5F, 1);
        int scaledX = Math.round(x / scale);
        int scaledY = Math.round(y / scale);
        int scaledWidth = Math.round(width / scale);
        int scaledHeight = Math.round(height / scale);
        DrawUtil.fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 15, Colors.DARKGRAY.color().withAlpha(150).color());
        matrices.scale(2, 2, 1);
        drawCenteredText(matrices, client.textRenderer, title, client.getWindow().getScaledWidth() / 2, y + 15, Colors.WHITE.color().color());
        super.render(matrices, mouseX, mouseY, delta);
    }

}

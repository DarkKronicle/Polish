package net.darkkronicle.polish.gui.complexwidgets;

import net.darkkronicle.polish.api.AbstractPEntry;
import net.darkkronicle.polish.util.Colors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class EntryButtonList extends AbstractPWidgetList<AbstractPEntry> {

    public int lastY = 2;
    private float scale = 0.5F;

    public EntryButtonList(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void addEntry(AbstractPEntry entry) {
        scrollMax = Math.max(lastY - height, 1);
        super.addEntry(entry);
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        matrices.scale(scale, scale, 1);
        int scaledWidth = (int) Math.ceil(width / scale);
        int scaledHeight = (int) Math.ceil(height / scale);
        int scaledX = (int) Math.ceil(getAbsoluteX() / scale);
        int scaledY = (int) Math.ceil(getAbsoluteY() / scale);
        fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 9, Colors.DARKGRAY.color().withAlpha(100).color());
        outlineRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 9, Colors.BLACK.color().withAlpha(200).color());
        matrices.pop();
    }
}

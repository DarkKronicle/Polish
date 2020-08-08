package net.darkkronicle.polish.gui.complexwidgets;

import lombok.Getter;
import net.darkkronicle.polish.api.AbstractPEntry;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.ScrollUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A WidgetList that uses savable entries.
 */
@Environment(EnvType.CLIENT)
public class EntryButtonList extends AbstractPWidgetList<AbstractPEntry> {

    /**
     * The last y that was used.
     */
    public int lastY = 2;
    /**
     * The scale used for finer details.
     */
    private float scale = 0.5F;
    /**
     * Show backgorund.
     */
    private boolean background;
    /**
     * The amount of columns.
     */
    @Getter
    protected final int columnCount;
    /**
     * Current column.
     */
    protected int currentColumn = 0;

    /**
     * Instantiates a new Entry button list.
     *
     * @param x           the x
     * @param y           the y
     * @param width       the width
     * @param height      the height
     * @param columnCount the column count
     */
    public EntryButtonList(int x, int y, int width, int height, int columnCount) {
        this(x, y, width, height, columnCount, true);
    }

    /**
     * Instantiates a new Entry button list.
     *
     * @param x           the x
     * @param y           the y
     * @param width       the width
     * @param height      the height
     * @param columnCount the column count
     * @param background  the background
     */
    public EntryButtonList(int x, int y, int width, int height, int columnCount, boolean background) {
        super(x, y, width, height);
        this.background = background;
        this.columnCount = columnCount;
    }

    /**
     * Increment column int.
     *
     * @return the int
     */
    public int incrementColumn() {
        currentColumn++;
        if (currentColumn > columnCount) {
            currentColumn = 1;
        }
        return currentColumn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntry(AbstractPEntry entry) {
        scrollMax = Math.max(lastY - height, 1);
        super.addEntry(entry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        if (background) {
            matrices.scale(scale, scale, 1);
            int scaledWidth = (int) Math.ceil(width / scale);
            int scaledHeight = (int) Math.ceil(height / scale);
            int scaledX = (int) Math.ceil(getAbsoluteX() / scale);
            int scaledY = (int) Math.ceil(getAbsoluteY() / scale);
            fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 9, Colors.DARKGRAY.color().withAlpha(100).color());
            outlineRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 9, Colors.BLACK.color().withAlpha(200).color());
        }
        matrices.pop();
    }

    public void reset() {
        for (Entry entry : getEntries()) {
            if (entry instanceof AbstractPEntry) {
                ((AbstractPEntry) entry).reset();
            }
        }
    }

    public void save() {
        for (Entry entry : getEntries()) {
            if (entry instanceof AbstractPEntry) {
                ((AbstractPEntry) entry).save();
            }
        }
    }
}

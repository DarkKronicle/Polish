package io.github.darkkronicle.polish.impl.builders;

import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.gui.entries.CleanButtonEntry;
import io.github.darkkronicle.polish.gui.widgets.CleanButton;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.SimpleColor;
import net.minecraft.text.Text;

public class CleanButtonEntryBuilder extends EntryFieldBuilder<Void, CleanButtonEntry>{
    private SimpleColor baseColor = Colors.BLACK.color().withAlpha(100);
    private SimpleColor borderColor = Colors.BLACK.color();
    private SimpleColor highlightColor = Colors.WHITE.color();
    private CleanButton.OnPress onPress;
    private int width = 30;
    private int height = 10;
    private Text buttonName;

    public CleanButtonEntryBuilder(Text name, Text buttonName, CleanButton.OnPress onPress) {
        super(name, null);
        this.buttonName = buttonName;
        this.onPress = onPress;
    }

    public CleanButtonEntryBuilder setBaseColor(SimpleColor color) {
        baseColor = color;
        return this;
    }

    public CleanButtonEntryBuilder setBorderColor(SimpleColor color) {
        borderColor = color;
        return this;
    }

    public CleanButtonEntryBuilder setHighlightColor(SimpleColor color) {
        highlightColor = color;
        return this;
    }

    public CleanButtonEntryBuilder setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public CleanButtonEntry build(EntryButtonList list) {
        CleanButton button = new CleanButton(0, 0, width, height, baseColor, highlightColor, borderColor, buttonName, onPress);
        CleanButtonEntry entry = CleanButtonEntry.createEntry(list, button, name, columnnum);
        return entry;
    }
}

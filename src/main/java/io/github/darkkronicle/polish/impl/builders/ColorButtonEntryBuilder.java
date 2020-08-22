package io.github.darkkronicle.polish.impl.builders;

import io.github.darkkronicle.polish.gui.complexwidgets.ColorButton;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.gui.entries.ColorButtonEntry;
import io.github.darkkronicle.polish.util.SimpleColor;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ColorButtonEntryBuilder extends EntryFieldBuilder<SimpleColor, ColorButtonEntry> {
    public ColorButtonEntryBuilder(Text name, SimpleColor value) {
        super(name, value);
    }

    @Override
    public ColorButtonEntry build(EntryButtonList list) {
        ColorButton button = new ColorButton(0, 0, value);
        ColorButtonEntry entry = ColorButtonEntry.createEntry(list, button, name);
        if (saveable != null) {
            entry.setSaveable(saveable);
        }
        return entry;
    }

    public ColorButtonEntryBuilder setSavable(Consumer<SimpleColor> save) {
        this.saveable = save;
        return this;
    }
}

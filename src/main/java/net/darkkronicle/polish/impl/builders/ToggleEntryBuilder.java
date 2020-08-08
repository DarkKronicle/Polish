package net.darkkronicle.polish.impl.builders;

import lombok.Getter;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.entries.ToggleEntry;
import net.darkkronicle.polish.gui.widgets.ToggleButton;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ToggleEntryBuilder extends EntryFieldBuilder<Boolean, ToggleEntry> {

    @Getter
    private int width = 20;
    @Getter
    private int height = 10;
    @Getter
    private Text on = new LiteralText("ON");
    @Getter
    private Text off = new LiteralText("OFF");

    public ToggleEntryBuilder(Text name, Boolean value) {
        super(name, value);
    }

    public ToggleEntryBuilder setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ToggleEntryBuilder setOnOff(Text on, Text off) {
        this.on = on;
        this.off = off;
        return this;
    }

    public ToggleEntryBuilder setColumn(int column) {
        this.columnnum = column;
        return this;
    }

    public ToggleEntryBuilder setDefaultSupplier(Supplier<Boolean> sup) {
        this.defaultValue = sup;
        return this;
    }

    public ToggleEntryBuilder setSavable(Consumer<Boolean> save) {
        this.saveable = save;
        return this;
    }

    @Override
    public ToggleEntry build(EntryButtonList list) {
        ToggleButton button = new ToggleButton(0, 0, width, height, value, on, off);
        ToggleEntry entry = ToggleEntry.createEntry(list, button, name, columnnum);
        if (defaultValue != null) {
            entry.setDefaultValue(defaultValue);
        }
        if (saveable != null) {
            entry.setSaveable(saveable);
        }
        return entry;
    }
}

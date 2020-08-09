package io.github.darkkronicle.polish.impl.builders;

import io.github.darkkronicle.polish.gui.widgets.CheckboxButton;
import lombok.Getter;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.gui.entries.CheckboxEntry;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CheckboxEntryBuilder extends EntryFieldBuilder<Boolean, CheckboxEntry> {

    @Getter
    private int size = 9;

    public CheckboxEntryBuilder(Text name, Boolean value) {
        super(name, value);
    }

    public CheckboxEntryBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public CheckboxEntryBuilder setColumn(int column) {
        this.columnnum = column;
        return this;
    }

    public CheckboxEntryBuilder setDefaultSupplier(Supplier<Boolean> sup) {
        this.defaultValue = sup;
        return this;
    }

    public CheckboxEntryBuilder setSavable(Consumer<Boolean> save) {
        this.saveable = save;
        return this;
    }

    @Override
    public CheckboxEntry build(EntryButtonList list) {
        CheckboxButton button = new CheckboxButton(0, 0, size, value);
        CheckboxEntry entry = CheckboxEntry.createEntry(list, button, name, columnnum);
        if (defaultValue != null) {
            entry.setDefaultValue(defaultValue);
        }
        if (saveable != null) {
            entry.setSaveable(saveable);
        }
        return entry;
    }
}

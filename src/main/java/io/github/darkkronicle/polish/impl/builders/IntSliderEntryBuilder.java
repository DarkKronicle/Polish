package io.github.darkkronicle.polish.impl.builders;

import io.github.darkkronicle.polish.gui.entries.IntSliderEntry;
import io.github.darkkronicle.polish.gui.widgets.IntSliderButton;
import lombok.Getter;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntSliderEntryBuilder extends EntryFieldBuilder<Integer, IntSliderEntry> {

    @Getter
    private int width = 20;
    @Getter
    private int min;
    @Getter
    private int max;

    public IntSliderEntryBuilder(Text name, Integer value) {
        super(name, value);
        min = value - 20;
        max = value + 20;
    }

    public IntSliderEntryBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public IntSliderEntryBuilder setMin(int min) {
        this.min = min;
        return this;
    }

    public IntSliderEntryBuilder setMax(int max) {
        this.max = max;
        return this;
    }

    public IntSliderEntryBuilder setColumn(int column) {
        this.columnnum = column;
        return this;
    }

    public IntSliderEntryBuilder setDefaultSupplier(Supplier<Integer> sup) {
        this.defaultValue = sup;
        return this;
    }

    public IntSliderEntryBuilder setSavable(Consumer<Integer> save) {
        this.saveable = save;
        return this;
    }

    @Override
    public IntSliderEntry build(EntryButtonList list) {
        IntSliderButton button = new IntSliderButton(0, 0, width, value, min, max);
        IntSliderEntry entry = IntSliderEntry.createEntry(list, button, name, columnnum);
        if (defaultValue != null) {
            entry.setDefaultValue(defaultValue);
        }
        if (saveable != null) {
            entry.setSaveable(saveable);
        }
        return entry;
    }
}
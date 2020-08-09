package io.github.darkkronicle.polish.impl.builders;

import lombok.Getter;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.gui.entries.FloatSliderEntry;
import io.github.darkkronicle.polish.gui.widgets.FloatSliderButton;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FloatSliderEntryBuilder extends EntryFieldBuilder<Float, FloatSliderEntry> {

    @Getter
    private int width = 20;
    @Getter
    private Float min;
    @Getter
    private Float max;

    public FloatSliderEntryBuilder(Text name, Float value) {
        super(name, value);
        min = value - 20;
        max = value + 20;
    }

    public FloatSliderEntryBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public FloatSliderEntryBuilder setMin(float min) {
        this.min = min;
        return this;
    }

    public FloatSliderEntryBuilder setMax(float max) {
        this.max = max;
        return this;
    }

    public FloatSliderEntryBuilder setColumn(int column) {
        this.columnnum = column;
        return this;
    }

    public FloatSliderEntryBuilder setDefaultSupplier(Supplier<Float> sup) {
        this.defaultValue = sup;
        return this;
    }

    public FloatSliderEntryBuilder setSavable(Consumer<Float> save) {
        this.saveable = save;
        return this;
    }

    @Override
    public FloatSliderEntry build(EntryButtonList list) {
        FloatSliderButton button = new FloatSliderButton(0, 0, width, value, min, max);
        FloatSliderEntry entry = FloatSliderEntry.createEntry(list, button, name, columnnum);
        if (defaultValue != null) {
            entry.setDefaultValue(defaultValue);
        }
        if (saveable != null) {
            entry.setSaveable(saveable);
        }
        return entry;
    }

}

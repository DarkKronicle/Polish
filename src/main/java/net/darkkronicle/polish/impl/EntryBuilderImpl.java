package net.darkkronicle.polish.impl;

import net.darkkronicle.polish.api.EntryBuilder;
import net.darkkronicle.polish.impl.builders.CheckboxEntryBuilder;
import net.darkkronicle.polish.impl.builders.DropdownSelectorEntryBuilder;
import net.darkkronicle.polish.impl.builders.FloatSliderEntryBuilder;
import net.darkkronicle.polish.impl.builders.IntSliderEntryBuilder;
import net.darkkronicle.polish.impl.builders.TextboxEntryBuilder;
import net.darkkronicle.polish.impl.builders.ToggleEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class EntryBuilderImpl implements EntryBuilder {

    @Override
    public ToggleEntryBuilder startToggleEntry(Text name, Boolean value) {
        return new ToggleEntryBuilder(name, value);
    }

    @Override
    public CheckboxEntryBuilder startCheckboxEntry(Text name, Boolean value) {
        return new CheckboxEntryBuilder(name, value);
    }

    @Override
    public <K> DropdownSelectorEntryBuilder<K> startDropdownEntry(Text name, K value) {
        return new DropdownSelectorEntryBuilder<>(name, value);
    }

    @Override
    public FloatSliderEntryBuilder startFloatSliderEntry(Text name, Float value, Float min, Float max) {
        return new FloatSliderEntryBuilder(name, value).setMin(min).setMax(max);
    }

    @Override
    public IntSliderEntryBuilder startIntSliderEntry(Text name, Integer value, Integer min, Integer max) {
        return new IntSliderEntryBuilder(name, value).setMin(min).setMax(max);
    }

    @Override
    public TextboxEntryBuilder startTextboxEntry(Text name, String value) {
        return new TextboxEntryBuilder(name, value);
    }
}

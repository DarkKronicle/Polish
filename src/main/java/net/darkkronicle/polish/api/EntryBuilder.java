package net.darkkronicle.polish.api;

import net.darkkronicle.polish.impl.EntryBuilderImpl;
import net.darkkronicle.polish.impl.builders.CheckboxEntryBuilder;
import net.darkkronicle.polish.impl.builders.DropdownSelectorEntryBuilder;
import net.darkkronicle.polish.impl.builders.FloatSliderEntryBuilder;
import net.darkkronicle.polish.impl.builders.IntSliderEntryBuilder;
import net.darkkronicle.polish.impl.builders.TextboxEntryBuilder;
import net.darkkronicle.polish.impl.builders.ToggleEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

// Brand new to builders so I did take inspiration on how to organize classes and use interfaces to my advantage
// from shedaniel.
@Environment(EnvType.CLIENT)
public interface EntryBuilder {
    static EntryBuilder create() {
        return new EntryBuilderImpl();
    }

    ToggleEntryBuilder startToggleEntry(Text name, Boolean value);

    CheckboxEntryBuilder startCheckboxEntry(Text name, Boolean value);

    <K> DropdownSelectorEntryBuilder<K> startDropdownEntry(Text name, K value);

    FloatSliderEntryBuilder startFloatSliderEntry(Text name, Float value, Float min, Float max);

    IntSliderEntryBuilder startIntSliderEntry(Text name, Integer value, Integer min, Integer max);

    TextboxEntryBuilder startTextboxEntry(Text name, String value);

}

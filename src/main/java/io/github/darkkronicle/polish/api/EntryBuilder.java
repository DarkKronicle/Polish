package io.github.darkkronicle.polish.api;

import io.github.darkkronicle.polish.gui.widgets.CleanButton;
import io.github.darkkronicle.polish.impl.builders.CheckboxEntryBuilder;
import io.github.darkkronicle.polish.impl.builders.CleanButtonEntryBuilder;
import io.github.darkkronicle.polish.impl.builders.ColorButtonEntryBuilder;
import io.github.darkkronicle.polish.impl.builders.DropdownSelectorEntryBuilder;
import io.github.darkkronicle.polish.impl.builders.FloatSliderEntryBuilder;
import io.github.darkkronicle.polish.impl.builders.IntSliderEntryBuilder;
import io.github.darkkronicle.polish.impl.builders.TextboxEntryBuilder;
import io.github.darkkronicle.polish.impl.EntryBuilderImpl;
import io.github.darkkronicle.polish.impl.builders.ToggleEntryBuilder;
import io.github.darkkronicle.polish.util.SimpleColor;
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

    CleanButtonEntryBuilder startCleanButtonEntry(Text name, Text buttonName, CleanButton.OnPress onPress);

    ColorButtonEntryBuilder startColorButtonEntry(Text name, SimpleColor color);
}

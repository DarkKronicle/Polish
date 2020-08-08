package net.darkkronicle.polish.impl.builders;

import net.darkkronicle.polish.gui.complexwidgets.DropdownSelectorButton;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.entries.DropdownSelectorEntry;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.SimpleColor;
import net.minecraft.text.Text;

import java.util.LinkedHashMap;
import java.util.Map;

public class DropdownSelectorEntryBuilder<K> extends EntryFieldBuilder<K, DropdownSelectorEntry<K>> {

    private int width = 100;
    private int height = 19;
    private SimpleColor background = Colors.DARKGRAY.color().withAlpha(100);
    private LinkedHashMap<K, String> entries;

    public DropdownSelectorEntryBuilder(Text name, K value) {
        super(name, value);
        entries = new LinkedHashMap<>();
    }

    public DropdownSelectorEntryBuilder<K> add(K value, String name) {
        entries.put(value, name);
        return this;
    }

    @Override
    public DropdownSelectorEntry<K> build(EntryButtonList list) {
        DropdownSelectorButton<K> button = new DropdownSelectorButton<>(0, 0, width, height, (int) (height/2) - 5, background);
        button.addSet(entries.entrySet());
        DropdownSelectorEntry<K> entry = DropdownSelectorEntry.createEntry(list, button, name, columnnum);
        if (defaultValue != null) {
            entry.setDefaultValue(defaultValue);
        }
        if (saveable != null) {
            entry.setSaveable(saveable);
        }
        return entry;
    }
}

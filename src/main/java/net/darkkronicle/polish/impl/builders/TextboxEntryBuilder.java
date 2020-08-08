package net.darkkronicle.polish.impl.builders;

import lombok.Getter;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.entries.TextboxEntry;
import net.darkkronicle.polish.gui.widgets.TextboxButton;
import net.darkkronicle.polish.util.Colors;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TextboxEntryBuilder extends EntryFieldBuilder<String, TextboxEntry> {

    @Getter
    private int width = 60;
    @Getter
    private int height = 15;

    public TextboxEntryBuilder(Text name, String value) {
        super(name, value);
    }

    public TextboxEntryBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public TextboxEntryBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public TextboxEntryBuilder setDefaultSupplier(Supplier<String> sup) {
        this.defaultValue = sup;
        return this;
    }

    public TextboxEntryBuilder setSavable(Consumer<String> save) {
        this.saveable = save;
        return this;
    }

    @Override
    public TextboxEntry build(EntryButtonList list) {
        TextboxButton button = new TextboxButton(MinecraftClient.getInstance().textRenderer, 0, 0, width, height, Colors.DARKGRAY.color().withAlpha(100), true);
        button.setText(value);
        TextboxEntry entry = TextboxEntry.createEntry(list, button, name, columnnum);
        if (defaultValue != null) {
            entry.setDefaultValue(defaultValue);
        }
        if (saveable != null) {
            entry.setSaveable(saveable);
        }
        return entry;
    }
}

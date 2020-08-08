package net.darkkronicle.polish.impl.builders;

import lombok.Getter;
import net.darkkronicle.polish.api.AbstractPEntry;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public abstract class EntryFieldBuilder<K, W extends AbstractPEntry> {
    @Getter
    protected final Text name;
    @Getter
    protected Supplier<K> defaultValue;
    @Getter
    protected Consumer<K> saveable;
    @Getter
    protected int columnnum = 0;
    @Getter
    protected final K value;

    public EntryFieldBuilder(Text name, K value) {
        this.name = name;
        this.value = value;
    }

    public abstract W build(EntryButtonList list);

}

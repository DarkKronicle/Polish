package io.github.darkkronicle.polish.impl.builders;

import io.github.darkkronicle.polish.api.AbstractPEntry;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import lombok.Getter;
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

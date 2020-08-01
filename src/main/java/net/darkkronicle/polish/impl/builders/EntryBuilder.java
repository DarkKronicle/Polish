package net.darkkronicle.polish.impl.builders;

import net.darkkronicle.polish.api.AbstractPEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public abstract class EntryBuilder<K, W extends AbstractPEntry> {
    private Text name;
    private Supplier<K> defaultValue;
    private Consumer<K> saveable;
    private int columnnum = 1;
}

package net.darkkronicle.polish.api;


import net.darkkronicle.polish.gui.complexwidgets.AbstractPWidgetList;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.widgets.AbstractPWidget;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Entries that can be added to {@link EntryButtonList}
 *
 * This class contains the bare bones of what is needed for the entries.
 * @param <V> The data type that it stores.
 * @param <W> The type of {@link AbstractPWidget} that it takes.
 */
@Environment(EnvType.CLIENT)
public abstract class AbstractPEntry<V, W extends AbstractPWidget> extends AbstractPWidgetList.Entry<AbstractPEntry<V, W>> implements ConfigurableEntry<V>  {
   private Supplier<V> defaultValue;
   private Consumer<V> saveable;
   protected Text name;
   protected final W widget;
   protected final MinecraftClient client;
   protected final EntryButtonList parentList;

   /**
    * Default constructor for AbstractPEntry
    * @param relativeX How far away from the left it is from the {@link EntryButtonList}
    * @param relativeY How far away from the top it is from the {@link EntryButtonList}
    * @param width How wide the entry is.
    * @param height How tall the entry is.
    * @param widget The {@link AbstractPWidget} that renders to interpret the data.
    * @param name What text displays to it's side.
    * @param parentList {@link EntryButtonList} parent list.
    */
   public AbstractPEntry(int relativeX, int relativeY, int width, int height, W widget, Text name, EntryButtonList parentList) {
      super(relativeX, relativeY, width, height, widget);
      this.name = name;
      this.widget = widget;
      this.client = MinecraftClient.getInstance();
      this.parentList = parentList;
   }

   /**
    * Gets the supplier that gets the default value.
    * @return Supplier that gets the default value.
    */
   public Supplier<V> getDefaultValue() {
      return defaultValue;
   }

   /**
    * Gets the consumer that saves the value.
    * @return The consumer that saves the value.
    */
   public Consumer<V> getSaveable() {
      return saveable;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void save() {
      if (saveable != null) {
         saveable.accept(getValue());
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public V getDefault() {
      if (defaultValue != null) {
         return defaultValue.get();
      }
      return getValue();
   }

   /**
    * Renders the entry.
    * @param bounds How much the parent list bounds are.
    * @param scrolled How far the list is scrolled. Always negative.
    * @param hovered If the parent list is hovered.
    */
   public abstract void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scrolled, boolean hovered);
}

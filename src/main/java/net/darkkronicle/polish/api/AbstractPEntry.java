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

@Environment(EnvType.CLIENT)
public abstract class AbstractPEntry<V, W extends AbstractPWidget> extends AbstractPWidgetList.Entry<AbstractPEntry<V, W>> implements ConfigurableEntry<V>  {
   private Supplier<V> defaultValue;
   private Consumer<V> saveable;
   protected Text name;
   protected final W widget;
   protected final MinecraftClient client;
   protected final EntryButtonList list;

   public AbstractPEntry(int relativeX, int relativeY, int width, int height, W widget, Text name, EntryButtonList list) {
      super(relativeX, relativeY, width, height, widget);
      this.name = name;
      this.widget = widget;
      this.client = MinecraftClient.getInstance();
      this.list = list;
   }


   public Supplier<V> getDefaultValue() {
      return defaultValue;
   }

   public Consumer<V> getSaveable() {
      return saveable;
   }

   @Override
   public void save() {
      if (saveable != null) {
         saveable.accept(getValue());
      }
   }

   @Override
   public V getDefault() {
      if (defaultValue != null) {
         return defaultValue.get();
      }
      return getValue();
   }

   public abstract void renderEntry(MatrixStack matrices, int index, int mouseX, int mouseY, float tickDelta, SimpleRectangle bounds, int scrolled, boolean hovered);
}

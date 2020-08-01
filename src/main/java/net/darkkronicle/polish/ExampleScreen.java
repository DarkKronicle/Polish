package net.darkkronicle.polish;

import net.darkkronicle.polish.gui.complexwidgets.ColorButton;
import net.darkkronicle.polish.gui.complexwidgets.DropdownSelectorButton;
import net.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import net.darkkronicle.polish.gui.entries.CheckboxEntry;
import net.darkkronicle.polish.gui.entries.FloatSliderEntry;
import net.darkkronicle.polish.gui.entries.IntSliderEntry;
import net.darkkronicle.polish.gui.entries.ToggleEntry;
import net.darkkronicle.polish.gui.widgets.CheckboxButton;
import net.darkkronicle.polish.gui.widgets.CleanButton;
import net.darkkronicle.polish.gui.widgets.FloatSliderButton;
import net.darkkronicle.polish.gui.widgets.IntSliderButton;
import net.darkkronicle.polish.gui.widgets.SelectorButton;
import net.darkkronicle.polish.gui.widgets.SimpleButton;
import net.darkkronicle.polish.gui.widgets.TextboxButton;
import net.darkkronicle.polish.gui.widgets.ToggleButton;
import net.darkkronicle.polish.util.Colors;
import net.darkkronicle.polish.util.WidgetManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ExampleScreen extends Screen {

    protected ExampleScreen() {
        super(new LiteralText("AAAAAAAAA"));
    }

    private WidgetManager widgetManager;

    // Class to mess around with the widgets. Will be gone in the release.
    @Override
    public void init() {
        widgetManager = new WidgetManager(this, children);
        MinecraftClient client = MinecraftClient.getInstance();
        CleanButton clean = new CleanButton(10, 10, 50, 20, Colors.SELECTOR_BLUE.color().withAlpha(100), Colors.WHITE.color().withAlpha(100), Colors.BLACK.color(), new LiteralText("Button"), button -> {
            client.inGameHud.getChatHud().addMessage(new LiteralText("CLICK"));
        });
        SimpleButton hello = new SimpleButton(0, 0, width, 19, Colors.DARKGRAY.color().withAlpha(100), new LiteralText("Sup nerds"), button -> {
            System.out.println("CLicked");
        });

     //   ToggleEntry toggleButton = new ToggleEntry(10, 40, 30, 15, false);
        CheckboxButton checkbox = new CheckboxButton(10, 70, 10, false);
        TextboxButton textbox = new TextboxButton(client.textRenderer, 10, 90, 100, 20, Colors.DARKGRAY.color().withAlpha(100), true);
  //      SelectorButton<String> selector = new SelectorButton<>(10, 115, 120, 20, 6, Colors.DARKGRAY.color().withAlpha(100));
//        selector.add("Hello", "Hello1");
//        selector.add("Goodbye", "Goodbye2");
//        selector.add("StillHere", "StillHere3");

        FloatSliderButton floatSlider = new FloatSliderButton(60, 40, 120, 0.5F, 0.0F, 1.0F);
        IntSliderButton intSlider = new IntSliderButton(60, 60, 200, 10, 0, 255);

        ColorButton color = new ColorButton(20, 150, Colors.DARKGRAY.color().withAlpha(100));
        widgetManager.add(color);
        widgetManager.add(clean);
        widgetManager.add(checkbox);
      //  widgetManager.add(toggleButton);
        widgetManager.add(textbox);
       // widgetManager.add(selector);
        widgetManager.add(floatSlider);
        widgetManager.add(intSlider);
        DropdownSelectorButton<String> selector = new DropdownSelectorButton<>(10, 115, 120, 20, 6, Colors.DARKGRAY.color().withAlpha(100));
        selector.add("Hello", "Hello1");
        selector.add("Goodbye", "Goodbye2");
        selector.add("StillHere", "StillHere3");


        widgetManager.add(hello);
        widgetManager.add(selector);

//        EntryButtonList list = new EntryButtonList((client.getWindow().getScaledWidth() / 2) - 300, (client.getWindow().getScaledHeight() / 2) - 100, 600, 200, 2);
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Baby"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Mom"));
//        ToggleEntry.addToList(list, new ToggleButton(10, 40, 19, 9, false), new LiteralText("Do I belong here?"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Dad"));
//        ToggleEntry.addToList(list, new ToggleButton(10, 40, 19, 9, false), new LiteralText("Is DarkKronicle epic?"));
//        IntSliderEntry.addToList(list, new IntSliderButton(60, 60, 50, 10, 0, 255), new LiteralText("How cool am I?"));
//        FloatSliderEntry.addToList(list, new FloatSliderButton(60, 60, 50, 10, 0.2F, 8.9F), new LiteralText("Wow, FlOaTs"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Baby"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Mom"));
//        ToggleEntry.addToList(list, new ToggleButton(10, 40, 19, 9, false), new LiteralText("Do I belong here?"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Dad"));
//        ToggleEntry.addToList(list, new ToggleButton(10, 40, 19, 9, false), new LiteralText("Is DarkKronicle epic?"));
//        IntSliderEntry.addToList(list, new IntSliderButton(60, 60, 50, 10, 0, 255), new LiteralText("How cool am I?"));
//        FloatSliderEntry.addToList(list, new FloatSliderButton(60, 60, 50, 10, 0.2F, 8.9F), new LiteralText("Wow, FlOaTs"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Baby"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Mom"));
//        ToggleEntry.addToList(list, new ToggleButton(10, 40, 19, 9, false), new LiteralText("Do I belong here?"));
//        CheckboxEntry.addToList(list, new CheckboxButton(0, 0, 9, false), new LiteralText("Checkbox Dad"));
//        ToggleEntry.addToList(list, new ToggleButton(10, 40, 19, 9, false), new LiteralText("Is DarkKronicle epic?"));
//        IntSliderEntry.addToList(list, new IntSliderButton(60, 60, 50, 10, 0, 255), new LiteralText("How cool am I?"));
//        FloatSliderEntry.addToList(list, new FloatSliderButton(60, 60, 50, 10, 0.2F, 8.9F), new LiteralText("Wow, FlOaTs"));
//
//        widgetManager.add(list);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
       // DrawUtil.rect(50, 50, 50, 50, Colors.BLACK.color().color());

        widgetManager.render(matrices, mouseX, mouseY, delta);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        widgetManager.mouseScrolled(mouseX, mouseY, amount);
        return false;
    }

    @Override
    public void tick() {
        widgetManager.tick();
        super.tick();
    }
}

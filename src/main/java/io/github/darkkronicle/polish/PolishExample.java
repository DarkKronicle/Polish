package io.github.darkkronicle.polish;

import io.github.darkkronicle.polish.api.EntryBuilder;
import io.github.darkkronicle.polish.gui.complexwidgets.EntryButtonList;
import io.github.darkkronicle.polish.gui.screens.BasicConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class PolishExample implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Messing around with the widgets.
        KeyBinding keyBinding = new KeyBinding(
                "config.advancedchat.key.openlog",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "category.advancedchat.keys"
        );
        KeyBindingHelper.registerKeyBinding(keyBinding);
        ClientTickEvents.START_CLIENT_TICK.register(s -> {
            if (keyBinding.wasPressed()) {
           //     s.openScreen(new ExampleScreen());
                open();
            }
        });
    }

    public void open() {
        MinecraftClient client = MinecraftClient.getInstance();
        EntryBuilder builder = EntryBuilder.create();
        EntryButtonList list = new EntryButtonList((client.getWindow().getScaledWidth() / 2) - 290, (client.getWindow().getScaledHeight() / 2) - 70, 580, 150, 1, false);
        list.addEntry(builder.startToggleEntry(new LiteralText("Building?"), false).setDimensions(20, 10).build(list));
        list.addEntry(builder.startDropdownEntry(new LiteralText("I hate selectors"), 2).add(1, "nerd").add(2, "bruh").add(3, "yo").build(list));
        list.addEntry(builder.startCheckboxEntry(new LiteralText("Dope 10/10"), true).build(list));
        list.addEntry(builder.startFloatSliderEntry(new LiteralText("FloAts"), 1F, 0F, 2F).build(list));
        list.addEntry(builder.startIntSliderEntry(new LiteralText("ints"), 5, 1, 255).build(list));
        list.addEntry(builder.startTextboxEntry(new LiteralText("Names?"), "").build(list));
        Screen screen = new BasicConfigScreen(new LiteralText("Configurations!"), list, () -> System.out.println("Saved"));
        client.openScreen(screen);
    }
}

package net.darkkronicle.polish;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
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
                s.openScreen(new ExampleScreen());
            }
        });
    }

}

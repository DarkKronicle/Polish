package net.darkkronicle.polish.api;

import net.darkkronicle.polish.impl.ScissorsHelperImpl;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ScissorsHelper {
    // Loosely based off of shedaniel's code (wow, this is awesome) https://github.com/shedaniel/cloth-config/blob/78a6e4668508863ef7e7f50866d5a0ac3097d452/src/main/java/me/shedaniel/clothconfig2/api/ScissorsHandler.java#L11

    ScissorsHelper INSTANCE = new ScissorsHelperImpl();

    void addScissor(SimpleRectangle rect);

    void removeLastScissor();

    void apply();
}

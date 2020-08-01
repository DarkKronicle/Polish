package net.darkkronicle.polish.api;

import net.darkkronicle.polish.impl.ScissorsHelperImpl;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Easy way to implement GL's scissoring effect.
 */
@Environment(EnvType.CLIENT)
public interface ScissorsHelper {
    // Loosely based off of shedaniel's code (wow, this is awesome) https://github.com/shedaniel/cloth-config/blob/78a6e4668508863ef7e7f50866d5a0ac3097d452/src/main/java/me/shedaniel/clothconfig2/api/ScissorsHandler.java#L11

    /**
     * The implementation of this class.
     */
    ScissorsHelper INSTANCE = new ScissorsHelperImpl();

    /**
     * Adds a scissor to GL.
     * @param rect The bounds of the scissor represented in a {@link SimpleRectangle}
     */
    void addScissor(SimpleRectangle rect);

    /**
     * Removes the last scissor added.
     */
    void removeLastScissor();

    /**
     * Applies all scissors.
     */
    void apply();
}

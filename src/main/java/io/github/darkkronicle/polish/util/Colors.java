package io.github.darkkronicle.polish.util;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Class to store basic colors for the mod.
 */
@Accessors(fluent = true)
public enum Colors {
    WHITE(255, 255, 255, 255),
    BLACK(0, 0, 0, 255),
    GRAY(128, 128, 128, 255),
    DARKGRAY(49, 51, 53, 255),
    SELECTOR_GREEN(53, 219, 103, 255),
    SELECTOR_RED(191, 34, 34, 255),
    SELECTOR_BLUE(51, 153, 255, 255)
    ;

    /**
     * The color generated by the enum.
     */
    @Getter
    private final SimpleColor color;

    /**
     * Instantiates a new Color.
     *
     * @param red   the red
     * @param green the green
     * @param blue  the blue
     * @param alpha the alpha
     */
    Colors(int red, int green, int blue, int alpha) {
        this.color = new SimpleColor(red, green, blue, alpha);
    }

}

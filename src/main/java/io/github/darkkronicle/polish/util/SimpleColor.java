package io.github.darkkronicle.polish.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

/**
 * An Object to store a color and switch back and forth between a bit-shifted int and RGBA.
 */
@Value
@Accessors(fluent = true)
public class SimpleColor {
    /**
     * The Red.
     */
    @Getter
    int red;
    /**
     * The Green.
     */
    @Getter
    int green;
    /**
     * The Blue.
     */
    @Getter
    int blue;
    /**
     * The Alpha.
     */
    @Getter
    @With(AccessLevel.PUBLIC)
    int alpha;
    /**
     * The Color.
     */
    @Getter
    int color;

    /**
     * Instantiates a new Simple Color with a bit-shifted int.
     *
     * @param color the bit shifted int.
     */
    public SimpleColor(int color) {
        this.color = color;
        SimpleColor completeColor = ColorUtil.intToColor(color);
        this.red = completeColor.red();
        this.green = completeColor.green();
        this.blue = completeColor.blue();
        this.alpha = completeColor.alpha();
    }

    /**
     * Instantiates a new Simple Color with RGBA
     *
     * @param red   the red
     * @param green the green
     * @param blue  the blue
     * @param alpha the alpha
     */
    public SimpleColor(int red, int green, int blue, int alpha) {
        red = Math.min(red, 255);
        green = Math.min(green, 255);
        blue = Math.min(blue, 255);
        alpha = Math.min(alpha, 255);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.color = ColorUtil.colorToInt(this);
    }


    /**
     * Generated for use of Lombok's @With
     * Should not be used.
     */
    protected SimpleColor(int red, int green, int blue, int alpha, int color) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.color = ColorUtil.colorToInt(this);
    }

}

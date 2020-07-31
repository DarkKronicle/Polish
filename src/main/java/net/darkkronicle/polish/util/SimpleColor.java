package net.darkkronicle.polish.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class SimpleColor {
    @Getter
    int red;
    @Getter
    int green;
    @Getter
    int blue;
    @Getter
    @With(AccessLevel.PUBLIC)
    int alpha;
    @Getter
    int color;

    public SimpleColor(int color) {
        this.color = color;
        SimpleColor completeColor = ColorUtil.intToColor(color);
        this.red = completeColor.red();
        this.green = completeColor.green();
        this.blue = completeColor.blue();
        this.alpha = completeColor.alpha();
    }

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
     */
    public SimpleColor(int red, int green, int blue, int alpha, int color) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.color = ColorUtil.colorToInt(this);
    }

}

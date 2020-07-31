package net.darkkronicle.polish.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

/**
 * A static utility class that helps when dealing with colors that use bit shifting, like Minecraft.
 * In here there is a color storage class that makes getting int and creating new colors simple.
 */
@UtilityClass
public class ColorUtil {

    // Probably my best hope for color... https://github.com/parzivail/StarWarsMod/blob/master/src/main/java/com/parzivail/util/ui/GLPalette.java
    // I don't like color ints :(
    // intToColor and colorToInt from parzivail https://github.com/parzivail (slightly modified to account for Alpha)

    /**
     * Turns a packed RGB color into a Color
     *
     * @param rgb The color to unpack
     * @return The new Color
     */
    public SimpleColor intToColor(int rgb) {
        int alpha = rgb >> 24 & 0xFF;
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        return new SimpleColor(red, green, blue, alpha);
    }

    /**
     * Turns a Color into a packed RGB int
     *
     * @param c The color to pack
     * @return The packed int
     */
    public int colorToInt(SimpleColor c) {
        int rgb = c.alpha();
        rgb = (rgb << 8) + c.red();
        rgb = (rgb << 8) + c.green();
        rgb = (rgb << 8) + c.blue();
        return rgb;
    }

    public SimpleColor fade(SimpleColor color, int timeAlive, int fadestart, int fadestop) {
        if (timeAlive <= fadestop) {
            if (timeAlive > fadestart) {
                int fadetime = fadestop - fadestart;
                int currenttime = timeAlive - fadestart;
                float alpha = (float) color.alpha();
                float fadePerTick = alpha / fadetime;
                int fadealpha = (int) alpha - (int) (fadePerTick * currenttime);
                return color.withAlpha(fadealpha);
            }
        }
        return color;
    }

    public SimpleColor blend(SimpleColor original, SimpleColor blend, float percentage) {
        if (percentage >= 1) {
            return blend;
        }
        if (percentage <= 0) {
            return original;
        }
        int red = blendInt(original.red(), blend.red(), percentage);
        int green = blendInt(original.green(), blend.green(), percentage);
        int blue = blendInt(original.blue(), blend.blue(), percentage);
        int alpha = blendInt(original.alpha(), blend.alpha(), percentage);
        return new SimpleColor(red, green, blue, alpha);
    }

    public int blendInt(int start, int end, float percent) {
        if (percent <= 0) {
            return start;
        }
        if (start == end || percent >= 1) {
            return end;
        }
        int dif = end - start;
        int add = (int) Math.round((float) dif * percent);
        return start + (add);
    }

}

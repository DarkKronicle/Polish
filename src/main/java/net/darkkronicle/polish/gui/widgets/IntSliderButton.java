package net.darkkronicle.polish.gui.widgets;

import net.minecraft.util.math.MathHelper;

public class IntSliderButton extends SliderButton<Integer> {

    public IntSliderButton(int x, int y, int width, int value, int min, int max) {
        super(x, y, width);
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @Override
    public double getPercent() {
        return ((double) value - min) / ((double) max - min);
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public void setPercent(double percent) {
        int dif = max - min;
        int per = Math.round(dif * (float) percent);
        int newval = per + min;
        value = MathHelper.clamp(newval, min, max);
    }
}

package net.darkkronicle.polish.gui.widgets;

import net.minecraft.util.math.MathHelper;

/**
 * A slider button that takes in integers.
 */
public class IntSliderButton extends SliderButton<Integer> {

    /**
     * Instantiates a new Int slider button.
     *
     * @param x     the x
     * @param y     the y
     * @param width the width
     * @param value the value
     * @param min   the min
     * @param max   the max
     */
    public IntSliderButton(int x, int y, int width, int value, int min, int max) {
        super(x, y, width);
        this.value = value;
        this.min = min;
        this.max = max;
        this.value = MathHelper.clamp(this.value, this.min, this.max);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPercent() {
        return ((double) value - min) / ((double) max - min);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPercent(double percent) {
        int dif = max - min;
        int per = Math.round(dif * (float) percent);
        int newval = per + min;
        value = MathHelper.clamp(newval, min, max);
    }
}

package net.darkkronicle.polish.gui.widgets;

import net.minecraft.util.math.MathHelper;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * A slider button that takes in Float values.
 */
public class FloatSliderButton extends SliderButton<Float> {

    /**
     * Instantiates a new Float slider button.
     *
     * @param x     the x
     * @param y     the y
     * @param width the width
     * @param value the value
     * @param min   the min
     * @param max   the max
     */
    public FloatSliderButton(int x, int y, int width, float value, float min, float max) {
        super(x, y, width);
        this.value = value;
        this.min = min;
        this.max = max;
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
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPercent(double percent) {
        float dif = max - min;
        float per = dif * (float) percent;
        float newval = per + min;
        value = MathHelper.clamp(newval, min, max);
    }
}

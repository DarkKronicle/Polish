package net.darkkronicle.polish.gui.widgets;

import net.minecraft.util.math.MathHelper;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FloatSliderButton extends SliderButton<Float> {

    public FloatSliderButton(int x, int y, int width, float value, float min, float max) {
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
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }

    @Override
    public void setPercent(double percent) {
        float dif = max - min;
        float per = dif * (float) percent;
        float newval = per + min;
        value = MathHelper.clamp(newval, min, max);
    }
}

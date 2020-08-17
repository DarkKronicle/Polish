package io.github.darkkronicle.polish.util;

import net.minecraft.util.Util;

/*
Based off of shedaniel's smooth scrolling code (did my own stuff too) which was based off of the osu-framework.
https://github.com/shedaniel/cloth-config/blob/v4/src/main/java/me/shedaniel/clothconfig2/api/ScrollingContainer.java
 */
/*
 * <p>
 * Copyright (c) 2020 ppy Pty Ltd <contact@ppy.sh>.
 * Copyright (c) 2018, 2019, 2020 shedaniel.
 * Copyright (c) 2020 DarkKronicle.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

public class ScrollUtil {
    private double start = -1;
    private double duration;
    private double target = 0;
    private double targetScroll;
    private double scroll;
    private double maxScroll;
    private SimpleRectangle dimensions;

    public ScrollUtil(double duration, double scroll, double maxScroll, SimpleRectangle dimensions) {
        this.duration = duration;
        this.scroll = scroll;
        this.targetScroll = scroll;
        this.dimensions = dimensions;
        this.maxScroll = maxScroll;
    }

    public DrawPosition getScrollPos() {
        return new DrawPosition(dimensions.x(),  dimensions.y() + (int) Math.round((dimensions.height() - 8) * (scroll / maxScroll) * -1));
    }

    public DrawPosition getSidePos() {
        return new DrawPosition(dimensions.x() + (int) Math.round((dimensions.width() - 8) * (scroll / maxScroll) * -1),  dimensions.y());
    }

    public void setMaxScroll(double maxScroll) {
        this.maxScroll = maxScroll;
    }

    public int getScroll() {
        //TODO Why am I using a negative value for scrolling again?
        return (int) Math.round(scroll * -1);
    }

    public void scrollTo(double pos) {
        start = Util.getMeasuringTimeMs();
        target = pos;
        targetScroll = scroll;
        updateScroll();
    }

    public void updateScroll() {
        if (getTime() > duration) {
            start = -1;
            scroll = target;
            targetScroll = scroll;
        }
        if (start == -1) {
            return;
        }
        double percent = start == -1 ? 1 : getTime() / duration;
        scroll = easeScroll(EasingFunctions.Types.SINE_OUT, targetScroll, target, percent);
        if (scroll > dimensions.height()) {
            scroll = dimensions.height();
            target = dimensions.height();
            targetScroll = scroll;
            start = -1;
        }
    }

    public static double easeScroll(EasingFunctions type, double scroll, double scrollEnd, double percent) {
        double scrollAmount = scrollEnd - scroll;
        double scrollEased = type.apply(percent) * scrollAmount;
        return scroll + scrollEased;
    }

    public double getTime() {
        return start == -1 ? -1 : Util.getMeasuringTimeMs() - start;
    }
}

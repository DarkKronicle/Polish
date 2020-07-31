package net.darkkronicle.polish.util;

import java.util.function.Function;

public interface EasingFunctions {
    // https://easings.net/# <- Equations used
    // shedaniel made an awesome class: https://github.com/shedaniel/cloth-config/blob/v4/src/main/java/me/shedaniel/clothconfig2/impl/EasingMethod.java
    double apply(double percent);

    enum Types implements EasingFunctions {
        NONE(d -> 1.0),
        LINEAR(d -> d),
        SINE_IN(d -> 1 - Math.cos((d * Math.PI) / 2)),
        SINE_OUT(d -> Math.sin((d * Math.PI) / 2)),
        SINE_IN_OUT(d -> -(Math.cos(Math.PI * d) - 1) / 2),
        CUBIC_IN_OUT(d -> d < 0.5 ? 4 * d * d * d : 1 - Math.pow(-2 * d + 2, 3) / 2),
        QUINT_IN_OUT(d -> d < 0.5 ? 16 * d * d * d * d * d : 1 - Math.pow(-2 * d + 2, 5) / 2)
        ;
        Types(Function<Double, Double> toapply) {
            this.toapply = toapply;
        }

        private final Function<Double, Double> toapply;

        @Override
        public double apply(double percent) {
            return toapply.apply(percent);
        }
    }

}

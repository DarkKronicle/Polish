package net.darkkronicle.polish.util;

import java.util.function.Function;

/**
 * An interface to use EasingFunctions.
 */
public interface EasingFunctions {
    // https://easings.net/# <- Equations used
    // shedaniel made an awesome class: https://github.com/shedaniel/cloth-config/blob/v4/src/main/java/me/shedaniel/clothconfig2/impl/EasingMethod.java

    /**
     * Apply a double to the easing function.
     *
     * @param percent input percent
     * @return double that went through the easing function
     */
    double apply(double percent);

    /**
     * Different types of EasingFunctions.
     */
    enum Types implements EasingFunctions {
        NONE(d -> 1.0),
        LINEAR(d -> d),
        SINE_IN(d -> 1 - Math.cos((d * Math.PI) / 2)),
        SINE_OUT(d -> Math.sin((d * Math.PI) / 2)),
        SINE_IN_OUT(d -> -(Math.cos(Math.PI * d) - 1) / 2),
        CUBIC_IN_OUT(d -> d < 0.5 ? 4 * d * d * d : 1 - Math.pow(-2 * d + 2, 3) / 2),
        QUINT_IN_OUT(d -> d < 0.5 ? 16 * d * d * d * d * d : 1 - Math.pow(-2 * d + 2, 5) / 2)
        ;

        /**
         * Instantiates a new Easing Function.
         *
         * @param toapply function that applies the easing function
         */
        Types(Function<Double, Double> toapply) {
            this.toapply = toapply;
        }

        /**
         * Function that applies the easing functions.
         */
        private final Function<Double, Double> toapply;

        /**
         * {@inheritDoc}
         */
        @Override
        public double apply(double percent) {
            return toapply.apply(percent);
        }
    }

}

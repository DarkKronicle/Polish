package io.github.darkkronicle.polish.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Simple class that stores an X and Y value that will eventually be drawed on.
 */
@EqualsAndHashCode
@AllArgsConstructor
@Data
public class DrawPosition {
    /**
     * The X.
     */
    private int x;


    /**
     * The Y.
     */
    private int y;
}

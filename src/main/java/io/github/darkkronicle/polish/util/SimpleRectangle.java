package io.github.darkkronicle.polish.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Immutable class that stores a rectangle.
 */
@Value
@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class SimpleRectangle implements Serializable {
    private static final long serialVersionUID = 692245160052938609L;

    /**
     * The X.
     */
    int x;
    /**
     * The Y.
     */
    int y;
    /**
     * The Width.
     */
    int width;
    /**
     * The Height.
     */
    int height;
}

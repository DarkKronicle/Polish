package net.darkkronicle.polish.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class SimpleRectangle {
    int x;
    int y;
    int width;
    int height;
}

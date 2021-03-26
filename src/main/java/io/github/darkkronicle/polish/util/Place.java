package io.github.darkkronicle.polish.util;

public interface Place {

    SimpleRectangle calculate(SimpleRectangle bounds, SimpleRectangle inside);

    enum Type {
        TOP_LEFT((bounds, inside) -> new SimpleRectangle(bounds.x(), bounds.y(), inside.width(), inside.height())),
        TOP_MIDDLE((bounds, inside) -> new SimpleRectangle(bounds.x() + (bounds.width() / 2 - inside.width() / 2), bounds.y(), inside.width(), inside.height())),
        TOP_RIGHT((bounds, inside) -> new SimpleRectangle(bounds.x() + bounds.width() - inside.width(), bounds.y(), inside.width(), inside.height())),
        MIDDLE_RIGHT((bounds, inside) -> new SimpleRectangle(bounds.x() + bounds.width() - inside.width(), bounds.y() + (bounds.height() / 2 - inside.height() / 2), inside.width(), inside.height())),
        BOTTOM_RIGHT((bounds, inside) -> new SimpleRectangle(bounds.x() + bounds.width() - inside.width(), bounds.y() + bounds.height() - inside.height(), inside.width(), inside.height())),
        BOTTOM_MIDDLE((bounds, inside) -> new SimpleRectangle(bounds.x() + (bounds.width() / 2 - inside.width() / 2), bounds.y() + bounds.height() - inside.height(), inside.width(), inside.height())),
        BOTTOM_LEFT((bounds, inside) -> new SimpleRectangle(bounds.x(), bounds.y() + bounds.height() - inside.height(), inside.width(), inside.height())),
        MIDDLE_LEFT((bounds, inside) -> new SimpleRectangle(bounds.x(), bounds.y() + (bounds.height() / 2 - inside.height() / 2), inside.width(), inside.height())),
        MIDDLE_MIDDLE((bounds, inside) -> new SimpleRectangle(bounds.x() + (bounds.width() / 2 - inside.width() / 2), bounds.y() + (bounds.height() / 2 - inside.height() / 2), inside.width(), inside.height()))
        ;

        private final Place place;

        Type(Place place) {
            this.place = place;
        }

        public SimpleRectangle calculate(SimpleRectangle bounds, SimpleRectangle inside) {
            return place.calculate(bounds, inside);
        }
    }
}

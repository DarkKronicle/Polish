package net.darkkronicle.polish.api;

/**
 * An interface to deal with saving entries and configuring them.
 * @param <V> The data type that will be saved and manipulated.
 */
public interface ConfigurableEntry<V> {
    /**
     * Gets the current value.
     * @return Returns V that is the value.
     */
    V getValue();

    /**
     * Sets the current value.
     * @param value The value that gets set.
     */
    void setValue(V value);

    /**
     * Saves the Entry. Won't do anything if there is no Save Consumer
     */
    void save();

    /**
     * Returns the default value of the entry. Returns currentvalue if no supplier set.
     * @return Default value.
     */
    V getDefault();
}

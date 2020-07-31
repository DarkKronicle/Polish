package net.darkkronicle.polish.api;

public interface ConfigurableEntry<V> {
    V getValue();
    void setValue(V value);
    void save();
    V getDefault();
}

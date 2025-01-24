package jp.fishmans.moire.decorators

public interface Decorator<T : DecoratorContext> {
    public fun decorate(context: T)
}

public interface DecoratorContext